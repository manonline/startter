package com.manonline.myapp.common;

import java.io.Serializable;
import java.util.List;

/**
 * Created by davidqi on 1/16/17.
 */
public interface IRepository<T, PK extends Serializable> {

    T load(PK id);

    T get(PK id);

    List<T> findAll();

    void persist(T entity);

    PK save(T entity);

    void saveOrUpdate(T entity);

    void delete(PK id);

    void flush();

}