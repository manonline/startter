package com.manonline.examples.jdbc.daotemplate;

import com.manonline.examples.jdbc.base.entity.User;
import com.manonline.examples.jdbc.datasource.simple.ConnectionUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * Created by davidqi on 2/15/17.
 */
public class SpringTemplateTest {

    public static void main(String[] args) {
        User user = new User();
        user.setMoney(20);
        user.setId(1);
        update(user);
    }

    /**
     * 更新操作
     *
     * @param user
     */
    static void update(User user) {
        JdbcTemplate jdbc = null;
        try {
            jdbc = new JdbcTemplate(ConnectionUtils.getDataSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "update user set money=? where id=?";
        Object[] args = new Object[]{user.getMoney(), user.getId()};
        jdbc.update(sql, args);
    }

    /**
     * 通过用户名查询用户
     *
     * @param name
     * @return
     */
    static User findUser(String name) {
        //需要传递一个数据源
        JdbcTemplate jdbc = null;
        try {
            jdbc = new JdbcTemplate(ConnectionUtils.getDataSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select id,name,money,birthday from user where name=?";
        Object[] args = new Object[]{name};
        //queryForObject方法和我们之前采用策略模式设置的模板很类似呀,这个方法只会返回一个记录，如果有多个记录返回或者没有记录返回的话，这个方法就会报告异常的
        Object user = jdbc.queryForObject(sql, args, new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setMoney(rs.getFloat("money"));
                user.setBirthday(rs.getDate("birthday"));
                return user;
            }
        });
        return (User) user;
    }

    /**
     * 通过用户名查询实体类
     *
     * @param name
     * @return
     */
    static User findUsers(String name) {
        JdbcTemplate jdbc = null;
        try {
            jdbc = new JdbcTemplate(ConnectionUtils.getDataSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select id,name,money,birthday from user where name=?";
        Object[] args = new Object[]{name};
        //如果没有记录或者返回多个记录的话，这个方法是会报异常的
        //使用这个方法直接将返回的结果集映射到实体类，这里返回的结果集中的字段和实体类中的属性名必须相等
        //如果不相等的话，就是用默认值对其属性进行赋值
        Object user = jdbc.queryForObject(sql, args, new BeanPropertyRowMapper(User.class) {
        });
        return (User) user;
    }

    /**
     * 查询多个用户
     *
     * @param id
     * @return
     */
    static List findUser1(int id) {
        JdbcTemplate jdbc = null;
        try {
            jdbc = new JdbcTemplate(ConnectionUtils.getDataSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select id,name,money,birthday from user where id<?";
        Object[] args = new Object[]{id};
        List users = jdbc.query(sql, args, new BeanPropertyRowMapper(User.class) {
        });
        return users;
    }

    //求最大值，记录总数等情况，查询结果只有一个值
    //返回8种基本类型
    static int getUserCount() {
        String sql = "select count(*) from user";
        //JdbcTemplate是线程安全的
        JdbcTemplate jdbc = null;
        try {
            jdbc = new JdbcTemplate(ConnectionUtils.getDataSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 2;
        // queryForInt is a deprecated method
        // jdbc.queryForInt(sql);
    }

    //返回String
    static String getUserName(int id) {
        JdbcTemplate jdbc = null;
        try {
            jdbc = new JdbcTemplate(ConnectionUtils.getDataSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select name from user where id=" + id;
        Object name = jdbc.queryForObject(sql, String.class);
        return (String) name;
    }

    //返回map
    static Map getUser(int id) {
        JdbcTemplate jdbc = null;
        try {
            jdbc = new JdbcTemplate(ConnectionUtils.getDataSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String sql = "select id,name,birthday from user where id=?";
        return jdbc.queryForMap(sql, new Object[]{id});
    }

    //添加完用户之后返回主键
    static User addUser(final User user) {
        JdbcTemplate jdbc = null;
        try {
            jdbc = new JdbcTemplate(ConnectionUtils.getDataSource());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //这个和RowMapper接口差不多，RowMapper是传回来一个ResultSet
        //而这个接口返回的是一个Connection，给我们更多的权限了
        jdbc.execute(new ConnectionCallback() {
            public Object doInConnection(Connection conn) throws SQLException, DataAccessException {
                String sql = "insert into user(name,birtdhday,birthday) values('jiangwei','1987-01-01',400)";
                PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.executeUpdate();
                //可能是组合主键,可能会返回一个ResultSet
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getInt(1));
                }
                return null;
            }
        });
        return user;
    }

}
