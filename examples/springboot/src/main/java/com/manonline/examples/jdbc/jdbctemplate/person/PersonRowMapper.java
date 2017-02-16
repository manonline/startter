package com.manonline.examples.jdbc.jdbctemplate.person;

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
         * 1. 获得结果集
         * 2. 遍历结果集 -> 也即获取每一行
         * 3. 字段映射 : 按照column名称取出字符串，按照字符串创建相应的数据类型，
         * 4. 调用构造函数，构造对象
         */
        PersonBean pb = new PersonBean();
        pb.setName(rs.getString("name"));
        pb.setId(rs.getInt("id"));
        return pb;
    }
}
