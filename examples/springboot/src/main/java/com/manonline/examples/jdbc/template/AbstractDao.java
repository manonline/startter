package com.manonline.examples.jdbc.template;

import com.manonline.examples.jdbc.firstdemo.DaoException;
import com.manonline.examples.jdbc.strategy.RowMapper;
import com.manonline.examples.jdbc.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by davidqi on 2/11/17.
 */
public abstract class AbstractDao {

    /**
     * 更新
     */
    protected int update(String sql, Object[] args) {
        // 这里需要做判断的,可能args为null
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = JdbcUtils.getConnection();
            st = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                st.setObject(i + 1, args[i]);
            }
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

    /**
     * 查询
     *
     * @param sql
     * @param args
     * @return
     */
    protected Object find(String sql, Object[] args) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            st = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                st.setObject(i + 1, args[i]);
            }
            rs = st.executeQuery();
            Object obj = null;
            while (rs.next()) {
                //不同的部分放到子类去做
                obj = rowMapper(rs);
            }
            return obj;
        } catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            JdbcUtils.free(null, st, conn);
        }
    }

    /**
     * 查找用户
     *
     * @param sql
     * @param args
     * @param rowMapper
     * @return
     */
    protected Object find(String sql, Object[] args, RowMapper rowMapper) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            st = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                st.setObject(i + 1, args[i]);
            }
            rs = st.executeQuery();
            Object obj = null;
            while (rs.next()) {
                obj = rowMapper.mapRow(rs);
            }
            return obj;
        } catch (Exception e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            JdbcUtils.free(null, st, conn);
        }
    }

    //子类需要实现的结果集处理方法
    protected abstract Object rowMapper(ResultSet rs) throws SQLException;
}