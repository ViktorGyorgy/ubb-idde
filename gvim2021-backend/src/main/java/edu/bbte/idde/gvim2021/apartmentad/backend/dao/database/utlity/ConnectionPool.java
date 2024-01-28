package edu.bbte.idde.gvim2021.apartmentad.backend.dao.database.utlity;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.bbte.idde.gvim2021.apartmentad.backend.utility.ConfigurationFactory;
import edu.bbte.idde.gvim2021.apartmentad.backend.utility.ConnectionPoolConfiguration;
import edu.bbte.idde.gvim2021.apartmentad.backend.utility.JdbcConfiguration;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public final class ConnectionPool {
    private static final HikariDataSource DATA_SOURCE;

    static {
        HikariConfig conf = new HikariConfig();
        JdbcConfiguration jdbcConfiguration = ConfigurationFactory.getJdbcConfiguration();
        ConnectionPoolConfiguration connectionPoolConfiguration = ConfigurationFactory.getConnectionPoolConfiguration();
        conf.setJdbcUrl(jdbcConfiguration.getUrl());
        conf.setDriverClassName(jdbcConfiguration.getDriverClass());
        conf.setUsername(jdbcConfiguration.getUsername());
        conf.setMaximumPoolSize(connectionPoolConfiguration.getPoolSize());
        DATA_SOURCE = new HikariDataSource(conf);
        log.info("Connection pool to database created.");
    }

    private ConnectionPool() {
    }

    public static Connection getConnection() throws SQLException {
        return DATA_SOURCE.getConnection();
    }
}
