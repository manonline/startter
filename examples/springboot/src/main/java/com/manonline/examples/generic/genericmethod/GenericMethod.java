package com.manonline.examples.generic.genericmethod;

/**
 * Created by davidqi on 1/31/17.
 */
public class GenericMethod {
    /**
     * Generic Method
     * @param <T> declare a generic T type
     * @param c 用来创建泛型对象
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public <T> T getObject(Class<T> c) throws IllegalAccessException, InstantiationException {
        T t = c.newInstance();
        return t;
    }
}