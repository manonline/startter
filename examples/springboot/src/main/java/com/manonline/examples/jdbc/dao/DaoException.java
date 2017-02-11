package com.manonline.examples.jdbc.dao;

/**
 * Created by davidqi on 2/11/17.
 */
public class DaoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DaoException() {

    }

    public DaoException(Exception e) {
        super(e);
    }

    public DaoException(String msg) {
        super(msg);
    }

    public DaoException(String msg, Exception e) {
        super(msg, e);
    }

}