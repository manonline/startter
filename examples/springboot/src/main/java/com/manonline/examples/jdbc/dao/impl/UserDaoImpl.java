package com.manonline.examples.jdbc.dao.impl;

import com.manonline.examples.jdbc.utils.JdbcUtils;
import com.manonline.examples.jdbc.dao.DaoException;
import com.manonline.examples.jdbc.dao.UserDao;
import com.manonline.examples.jdbc.domain.User;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by davidqi on 2/11/17.
 */
public class UserDaoImpl implements UserDao {

    /**
     * 添加用户
     */
    public void addUser(User user) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "insert into user(id,name,birthday,money) values(?,?,?,?)";
            st = conn.prepareStatement(sql);
            st.setInt(1, user.getId());
            st.setString(2, user.getName());
            /**
             * 日期格式的转换(utils.date转化成sql.date)
             * Java 对象 日期 utils.Date
             * Java SQL 日期 sql.Date
             * 存入数据库  : utils.Date to sqlDate
             * 取出生成对象: sqlDate to Utils.Date
             */
            st.setDate(3, new Date(user.getBirthday().getTime()));
            st.setFloat(4, user.getMoney());
            int count = st.executeUpdate();
            System.out.println("添加记录条数:" + count);
        } catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            JdbcUtils.free(null, st, conn);
        }
    }

    /**
     * 删除用户
     */
    public int delete(User user) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "delete from user where id=?";
            st = conn.prepareStatement(sql);
            st.setInt(1, user.getId());
            int count = -1;
            count = st.executeUpdate();
            System.out.println("删除记录条数:" + count);
            return count;
        } catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            JdbcUtils.free(null, st, conn);
        }
    }

    /**
     * 通过userId获取用户信息
     */
    public User getUserById(int userId) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "select * from user where id=?";
            st = conn.prepareStatement(sql);
            st.setInt(1, userId);
            rs = st.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(userId);
                user.setName(rs.getString("name"));
                user.setBirthday(rs.getDate("birthday"));
                user.setMoney(rs.getFloat("money"));
                return user;
            }
        } catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            JdbcUtils.free(rs, st, conn);
        }
        return null;
    }

    /**
     * 更新用户信息
     */
    public int update(User user) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = JdbcUtils.getConnection();
            String sql = "update user set name=?,birthday=?,money=? where id=?";
            st = conn.prepareStatement(sql);
            st.setString(1, user.getName());
            st.setDate(2, new Date(user.getBirthday().getTime()));
            st.setFloat(3, user.getMoney());
            st.setInt(3, user.getId());
            int count = 0;
            count = st.executeUpdate();
            System.out.println("更新的记录数:" + count);
            return count;
        } catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            JdbcUtils.free(null, st, conn);
        }
    }

}
