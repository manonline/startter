package com.manonline.examples.jdbc.base.utils.misc;

import com.manonline.examples.jdbc.base.utils.ConnectionUtils;

import java.sql.*;

/**
 * Created by davidqi on 2/11/17.
 */
public class Batch {

    public static void main(String[] args) throws Exception {
        // 执行一百遍，每遍插入一条
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 100; i++) {
            create();
        }
        long endTime = System.currentTimeMillis();

        // 执行一遍，插入一百条
        System.out.println("For Waste Time:" + (endTime - startTime));
        createBatch();
        System.out.println("Batch Waste Time:" + (System.currentTimeMillis() - endTime));
    }

    // 单独执行一次示例
    static void create() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtils.getConnection();
            String sql = "insert user(name,birthday,money) values(?,?,?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "jiangwei");
            ps.setDate(2, new Date(System.currentTimeMillis()));
            ps.setFloat(3, 400);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtils.free(rs, ps, conn);
        }
    }

    static void createBatch() throws Exception {
        /**
         * 建立一个连接的是很耗时间的，执行一个sql语句也是很耗时间的，优化的措施：批处理
         */
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtils.getConnection();
            String sql = "insert user(name,birthday,money) values(?,?,?)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            /**
             * 打包的话容量也不是越大越好，因为可能会内存溢出的，同时网络传输的过程中也是会进行拆包传输的，这个包的大小是不一定的
             * 有时候打包的效率不一定就会高，这个和数据库的类型，版本都有关系的，所以我们在实践的过程中需要检验的
             */
            for (int i = 0; i < 100; i++) {
                ps.setString(1, "jiangwei");
                ps.setDate(2, new Date(System.currentTimeMillis()));
                ps.setFloat(3, 400);
                //ps.addBatch(sql);
                /**
                 * 加入到batch（大包）
                 */
                ps.addBatch();
            }
            /**
             * 执行批处理
             */
            ps.executeBatch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtils.free(rs, ps, conn);
        }
    }
}
