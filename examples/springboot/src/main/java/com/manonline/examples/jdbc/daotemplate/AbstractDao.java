package com.manonline.examples.jdbc.daotemplate;

import com.manonline.examples.jdbc.base.dao.DaoException;
import com.manonline.examples.jdbc.base.utils.ConnectionUtils;

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
            conn = ConnectionUtils.getConnection();
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
            ConnectionUtils.free(null, st, conn);
        }
    }

    /**
     * 查找用户 ： 模版模式；转换工作交给子类去做，但是子类必须实现rowMapper方法。这种方法的问题是，rowMapper是订死的，所以不能适应
     * 不同的结果集，也就是说每次要为不同结果集重写RowMapper
     */
    protected Object find(String sql, Object[] args) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtils.getConnection();
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
            ConnectionUtils.free(null, st, conn);
        }
    }

    // 子类需要实现的结果集处理方法
    protected abstract Object rowMapper(ResultSet rs) throws SQLException;

    /**
     * 查找用户 : 策略模式，传入一个RowMapper接口实现告诉父类如何完成转换工作
     */
    protected Object find(String sql, Object[] args, RowMapper rowMapper) {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtils.getConnection();
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
            ConnectionUtils.free(null, st, conn);
        }
    }

}