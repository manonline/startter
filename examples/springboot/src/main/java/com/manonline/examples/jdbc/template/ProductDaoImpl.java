package com.manonline.examples.jdbc.template;

import java.sql.ResultSet;

/**
 * Created by davidqi on 2/11/17.
 */
public class ProductDaoImpl extends AbstractDao{

    public int update(){
        String sql = "update product set pname=?,price=? where pid=?";
        Object[] args = new Object[]{"drug",11,1};
        return super.update(sql, args);
    }

    @Override
    protected Object rowMapper(ResultSet rs) {
        return null;
    }

}