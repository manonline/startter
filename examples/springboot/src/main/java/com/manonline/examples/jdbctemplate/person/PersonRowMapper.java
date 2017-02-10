package com.manonline.examples.jdbctemplate.person;

/**
 * Created by davidqi on 2/10/17.
 */

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PersonRowMapper implements RowMapper {
    // 默认已经执行rs.next(),可以直接取数据
    public Object mapRow(ResultSet rs, int index) throws SQLException {
        /**
         * 获得结果集
         */
        PersonBean pb = new PersonBean(rs.getInt("id"), rs.getString("name"));
        return pb;
    }
}
