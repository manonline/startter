package com.manonline.examples.generic.containertest;

/**
 * Created by davidqi on 1/31/17.
 */
public class Container<T> {
    private T data;

    public Container() {
    }

    public Container(T data) {
        setData(data);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}