package com.manonline.examples.generic.inherit;

/**
 * Created by davidqi on 1/31/17.
 */
public class Father<T> {
    T data;

    public Father(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Father [data=" + data + "]";
    }
}
