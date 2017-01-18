package com.manonline.myapp.repository.impl;

import com.manonline.myapp.repository.IUserRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by davidqi on 1/17/17.
 */
@Repository
public class UserRepository implements IUserRepository{
    @Override
    public Object load(Serializable id) {
        return null;
    }

    @Override
    public Object get(Serializable id) {
        return null;
    }

    @Override
    public List findAll() {
        return null;
    }

    @Override
    public void persist(Object entity) {

    }

    @Override
    public Serializable save(Object entity) {
        return null;
    }

    @Override
    public void saveOrUpdate(Object entity) {

    }

    @Override
    public void delete(Serializable id) {

    }

    @Override
    public void flush() {

    }
}
