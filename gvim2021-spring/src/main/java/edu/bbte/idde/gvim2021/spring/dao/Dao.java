package edu.bbte.idde.gvim2021.spring.dao;

import edu.bbte.idde.gvim2021.spring.model.BaseEntity;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.Optional;

public interface Dao<T extends BaseEntity> {
    Collection<T> findAll();

    Optional<T> findById(Long id);

    T saveAndFlush(T entity);

    void update(Long id, T entity);

    @Transactional
    void deleteById(Long id);
}
