package edu.bbte.idde.gvim2021.spring.dao.database;

import edu.bbte.idde.gvim2021.spring.controller.exception.CreationException;
import edu.bbte.idde.gvim2021.spring.dao.Dao;
import edu.bbte.idde.gvim2021.spring.dao.database.utlity.ConnectionPool;
import edu.bbte.idde.gvim2021.spring.dao.exception.RepositoryException;
import edu.bbte.idde.gvim2021.spring.model.BaseEntity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
public abstract class DatabaseDao<T extends BaseEntity> implements Dao<T> {
    private static final Map<Class<?>, String> CLASS_TO_SQL_TYPE = new ConcurrentHashMap<>();

    static {
        CLASS_TO_SQL_TYPE.put(Integer.class, "int");
        CLASS_TO_SQL_TYPE.put(Long.class, "bigint");
        CLASS_TO_SQL_TYPE.put(String.class, "varchar(255)");
    }

    private final AtomicLong randomGenerator = new AtomicLong();
    private final String tableName;
    private final Class<T> datatype;
    private final List<Field> sortedFields;
    @Autowired
    private ConnectionPool connectionPool;

    public DatabaseDao(String tableName, Class<T> datatype) {
        log.info("Creating Databasedao - " + getClass());
        this.tableName = tableName;
        this.datatype = datatype;

        //compared by String and not the string's hashcode. for future compatibility (can never be sure?)
        //also sorting, because getDeclaredFields doesn't guarantees the order of fields
        sortedFields = Arrays.stream(datatype.getDeclaredFields())
                .filter(field -> field.getAnnotation(OneToMany.class) == null
                        && field.getAnnotation(ManyToOne.class) == null)
                .sorted(Comparator.comparing(Field::getName))
                .collect(Collectors.toList());

        //filter out field which have annotations @OneToMany or ManyToOne
    }

    private List<T> collectResultSet(ResultSet resultSet) throws SQLException {
        List<T> list = new ArrayList<>();

        while (resultSet.next()) {
            list.add(resultSetToObject(resultSet));
        }
        return list;
    }

    private T resultSetToObject(ResultSet resultSet) throws SQLException {
        try {
            T entity = datatype.getConstructor().newInstance();

            for (int i = 0; i < sortedFields.size(); i++) {
                Field field = sortedFields.get(i);
                String capitalizedFieldName = field.getName().substring(0, 1).toUpperCase(Locale.ROOT)
                        + field.getName().substring(1);

                Method setter = datatype.getMethod("set" + capitalizedFieldName, field.getType());

                setter.invoke(entity, resultSet.getObject(i + 1, field.getType()));
            }
            entity.setId(resultSet.getLong("ID"));

            return entity;
        } catch (NoSuchMethodException
                | InvocationTargetException
                | IllegalAccessException
                | InstantiationException e) {
            throw new RepositoryException("Can't decompose entity of type " + datatype);
        }
    }

    protected Collection<T> executeQuery() {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                StringBuilder select = new StringBuilder("SELECT ");
                for (Field field : sortedFields) {
                    select.append(field.getName())
                            .append(", ");
                }
                select.append("id FROM ")
                        .append(tableName);

                ResultSet resultSet = statement.executeQuery(select.toString());

                return collectResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException("couldn't execute SELECT SCRIPT");
        }
    }

    protected List<T> executeQuery(String filter) {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement statement = connection.createStatement()) {
                StringBuilder select = new StringBuilder("SELECT ");
                for (Field field : sortedFields) {
                    select.append(field.getName())
                            .append(", ");
                }
                select.append("id FROM ")
                        .append(tableName)
                        .append(" WHERE ")
                        .append(filter);
                ResultSet resultSet = statement.executeQuery(select.toString());

                return collectResultSet(resultSet);
            }
        } catch (SQLException e) {
            throw new RepositoryException(
                    "couldn't execute SELECT SCRIPT with filter: "
                            + filter);
        }
    }

    @Override
    public Collection<T> findAll() {
        return executeQuery();
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.ofNullable(executeQuery("ID = " + id).get(0));
    }

    @Override
    public T saveAndFlush(T entity) {
        Long id = randomGenerator.getAndIncrement();
        entity.setId(id);

        try (Connection connection = connectionPool.getConnection()) {

            StringBuilder insert = new StringBuilder("INSERT INTO ")
                    .append(tableName)
                    .append(" (");

            for (Field field : sortedFields) {
                insert.append(field.getName())
                        .append(", ");
            }
            insert.append("id )");

            insert.append(" VALUES(")
                    .append("?, ".repeat(sortedFields.size()))
                    .append('0')
                    .append(')');

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(insert.toString(), Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < sortedFields.size(); i++) {
                    Field field = sortedFields.get(i);
                    String capitalizedFieldName = field.getName().substring(0, 1).toUpperCase(Locale.ROOT)
                            + field.getName().substring(1);

                    Method getter = datatype.getMethod("get" + capitalizedFieldName);

                    preparedStatement.setObject(i + 1, getter.invoke(entity));
                }

                preparedStatement.executeUpdate();

                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    entity.setId(resultSet.getLong(1));
                    log.info("Created entity with id = " + entity.getId());
                    return entity;
                }

                throw new CreationException("Couldn't create entity.");
            }
        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            log.info(e.toString());
            throw new RepositoryException("Couldn't create with entity = " + entity);

        }
    }

    @Override
    public void update(Long id, T entity) {
        try (Connection connection = connectionPool.getConnection()) {
            StringBuilder update = new StringBuilder("UPDATE ")
                    .append(tableName)
                    .append(" SET ");

            for (int i = 0; i < sortedFields.size(); i++) {
                Field field = sortedFields.get(i);
                update.append(field.getName())
                        .append(" = ?");

                if (i != sortedFields.size() - 1) {
                    update.append(", ");
                }
            }

            update.append(" WHERE ID = ")
                    .append(id);

            log.info(update.toString());
            PreparedStatement preparedStatement = connection.prepareStatement(update.toString());

            for (int i = 0; i < sortedFields.size(); i++) {
                Field field = sortedFields.get(i);
                String capitalizedFieldName = field.getName().substring(0, 1).toUpperCase(Locale.ROOT)
                        + field.getName().substring(1);
                Method getter = datatype.getMethod("get" + capitalizedFieldName);

                preparedStatement.setObject(i + 1, getter.invoke(entity));
            }

            preparedStatement.executeUpdate();

        } catch (SQLException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            throw new RepositoryException("Couldn't update with id = " + id);
        }
    }

    @Override
    public void deleteById(Long id) {
        //execute delete
        try (Connection connection = connectionPool.getConnection()) {
            String delete = "DELETE FROM "
                    + tableName
                    + " WHERE ID = " + id;

            Statement statement = connection.createStatement();
            statement.executeUpdate(delete);
        } catch (SQLException e) {
            throw new RepositoryException("Couldn't delete with id = " + id);
        }
    }
}
