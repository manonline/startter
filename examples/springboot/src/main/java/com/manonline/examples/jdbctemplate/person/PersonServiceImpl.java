package com.manonline.examples.jdbctemplate.person;

/**
 * Created by davidqi on 2/10/17.
 */

import java.util.List;

import javax.sql.DataSource;
import java.sql.Types;

import org.springframework.jdbc.core.JdbcTemplate;

public class PersonServiceImpl implements PersonService {

    private JdbcTemplate jdbcTemplate;

    /**
     * 通过Spring容器注入datasource
     * 实例化JdbcTemplate,该类为主要操作数据库的类
     *
     * @param ds
     */
    public void setDataSource(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    public void addPerson(PersonBean person) {
        /**
         * 第一个参数为执行sql
         * 第二个参数为参数数据
         * 第三个参数为参数类型
         */
        jdbcTemplate.update("insert into person values(null,?)", new Object[]{person.getName()}, new int[]{Types.VARCHAR});
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("delete from person where id = ?", new Object[]{id}, new int[]{Types.INTEGER});
    }

    public PersonBean queryPerson(int id) {
        /**
         * new PersonRowMapper()是一个实现RowMapper接口的类,
         * 执行回调,实现mapRow()方法将rs对象转换成PersonBean对象返回
         */
        PersonBean pb = (PersonBean) jdbcTemplate.queryForObject("select id,name from person where id = ?", new Object[]{id}, new PersonRowMapper());
        return pb;
    }

    @SuppressWarnings("unchecked")
    public List<PersonBean> queryPersons() {
        List<PersonBean> pbs = (List<PersonBean>) jdbcTemplate.query("select id,name from person", new PersonRowMapper());
        return pbs;
    }

    public void updatePerson(PersonBean person) {
        jdbcTemplate.update("update person set name = ? where id = ?", new Object[]{person.getName(), person.getId()}, new int[]{Types.VARCHAR, Types.INTEGER});
    }
}