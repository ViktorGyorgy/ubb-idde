package edu.bbte.idde.gvim2021.apartmentad.backend.dao;

import edu.bbte.idde.gvim2021.apartmentad.backend.model.BaseEntity;

import java.util.Collection;

public interface Dao<T extends BaseEntity> {
    Collection<T> findAll();

    T findById(Long id);

    T create(T entity);

    void update(Long id, T entity);

    void delete(Long id);
}
