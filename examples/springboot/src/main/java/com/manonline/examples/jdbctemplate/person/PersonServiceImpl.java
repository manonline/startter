package com.manonline.examples.jdbctemplate.person;

/**
 * Created by davidqi on 2/10/17.
 * http://blog.csdn.net/skmbw/article/details/9105261
 *
 */

import java.util.List;

import javax.sql.DataSource;
import java.sql.Types;

import org.springframework.jdbc.core.JdbcTemplate;

public class PersonServiceImpl implements PersonService {

    private JdbcTemplate jdbcTemplate;

    /**
     * 通过Spring容器注入datasource，实例化JdbcTemplate,该类为主要操作数据库的类
     */
    public void setDataSource(DataSource ds) {
        this.jdbcTemplate = new JdbcTemplate(ds);
    }

    /**
     * DDL : execute用来执行ddl命令；
     */
    public void create(String tableName) { //tb_test1
        jdbcTemplate.execute("create table " + tableName + " (id integer, " +
                "                                                  name varchar2(40)");
    }

    /**
     * DML : update适合于insert 、update和delete操作；
     */
    public void addPerson(PersonBean person) {
        /**
         * 第一个参数为执行sql
         * 第二个参数为参数数据
         * 第三个参数为参数类型
         */
        jdbcTemplate.update("insert into person values(null,?)",
                new Object[]{person.getName()},
                new int[]{Types.VARCHAR});
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("delete from person where id = ?",
                new Object[]{id},
                new int[]{Types.INTEGER});
    }

    public PersonBean queryPerson(int id) {
        /**
         * 查询一行任何类型的数据
         *
         * new PersonRowMapper()是一个实现RowMapper接口的类,
         * 执行回调,实现mapRow()方法将rs对象转换成PersonBean对象返回
         */
        PersonBean pb = (PersonBean) jdbcTemplate.queryForObject("select id, name from person where id = ?",
                new Object[]{id},
                new PersonRowMapper());
        return pb;
    }

    @SuppressWarnings("unchecked")
    public List<PersonBean> queryPersons() {
        /**
         * 查询一批数据，默认将每行数据转换为Map，此处已指定行数据转换的类型为Person；
         */
        List<PersonBean> pbs = (List<PersonBean>) jdbcTemplate.query("select id, name from person",
                new PersonRowMapper());
        return pbs;
    }

    public void updatePerson(PersonBean person) {
        jdbcTemplate.update("update person set name = ? where id = ?",
                new Object[]{person.getName(), person.getId()},
                new int[]{Types.VARCHAR, Types.INTEGER});
    }

    /**
     * Call : 调用存储过程
     */
    public void callProcedure(int id) {
        this.jdbcTemplate.update("call SUPPORT.REFRESH_PERSONS_SUMMARY(?)", new Object[]{Long.valueOf(id)});
    }
}

// 1.查询一行数据并返回int型结果
// jdbcTemplate.queryForInt("select count(*) from test");
// 2. 查询一行数据并将该行数据转换为Map返回
// jdbcTemplate.queryForMap("select * from test where name='name5'");
// 3.查询一行任何类型的数据，最后一个参数指定返回结果类型
// jdbcTemplate.queryForObject("select count(*) from test", Integer.class);
// 4.查询一批数据，默认将每行数据转换为Map
// jdbcTemplate.queryForList("select * from test");
// 5.只查询一列数据列表，列类型是String类型，列名字是name
// jdbcTemplate.queryForList("
// select name from test where name=?", new Object[]{"name5"}, String.class);
// 6.查询一批数据，返回为SqlRowSet，类似于ResultSet，但不再绑定到连接上
// SqlRowSet rs = jdbcTemplate.queryForRowSet("select * from test");