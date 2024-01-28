package edu.bbte.idde.gvim2021.spring.dao.memory;

import edu.bbte.idde.gvim2021.spring.dao.Dao;
import edu.bbte.idde.gvim2021.spring.model.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
public abstract class MemoryDao<T extends BaseEntity> implements Dao<T> {
    protected final Map<Long, T> entities = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    protected MemoryDao() {
        log.info("Creating MemDao - " + getClass());
    }

    @Override
    public Collection<T> findAll() {
        return entities.values();
    }

    @Override
    public Optional<T> findById(Long id) {
        return Optional.of(entities.get(id));
    }

    @Override
    public T saveAndFlush(T entity) {
        Long id = idGenerator.getAndIncrement();
        entity.setId(id);
        entities.put(id, entity);
        return entity;
    }

    @Override
    public void update(Long id, T entity) {
        entities.put(id, entity);
    }

    @Override
    public void deleteById(Long id) {
        entities.remove(id);
    }
}
