package com.manonline.examples.jdbc.base.utils.misc;

import com.manonline.examples.jdbc.base.utils.ConnectionUtils;

import java.sql.*;

/**
 * Created by davidqi on 2/11/17.
 */
public class PrepareStatementTest {
    /**
     * 使用Statement读取数据
     */
    static void read(String name) throws SQLException {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtils.getConnection();
            /**
             * 此处有两个问题，导致PrepareStatement会更好
             * 1. name可能是非法字符串，比方说 "'1' or 1 = 1"；这样的注入可以拖出所有的数据。
             * 2. sql语句不会被缓存，数据库上的编译结果不会被重用。
             * 但是，单独执行PrepareStatement反倒会慢，多次执行的语句才能获得效率上的提升。
             */
            String sql = "select id,name from user where name='" + name + "'";
            // 创建语句
            st = conn.createStatement();
            // 执行语句并获取结果
            rs = st.executeQuery(sql);

            while (rs.next()) {
                // 根据列名提取取数据
                System.out.println(rs.getObject("id") + "\t" + rs.getObject("name") + "\t");
                // 根据列的位置提取数据
                // System.out.println(rs.getInt(1) + "\t" + rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtils.free(rs, st, conn);
        }
    }

    /**
     * 使用PreparedStatement
     */
    static void readPrepared(String name) throws SQLException {
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtils.getConnection();
            // 执行语句(不建议使用*)
            String sql = "select id,name from user where name=?";
            // 创建语句
            st = conn.prepareStatement(sql);
            st.setString(1, name);
            // 执行语句并获取结果
            rs = st.executeQuery();
            // 根据列名取数据
            while (rs.next()) {
                System.out.println(rs.getObject("id") + "\t" + rs.getObject("name") + "\t");
            }
        } catch (Exception e) {

        }
    }
}
