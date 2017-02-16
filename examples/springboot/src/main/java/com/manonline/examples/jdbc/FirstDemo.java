package com.manonline.examples.jdbc;

import com.manonline.examples.jdbc.base.utils.ConnectionUtils;

import java.sql.*;

/**
 * Created by davidqi on 2/11/17.
 * http://blog.csdn.net/jiangwei0910410003/article/details/26164629
 */
public class FirstDemo {

    public static void main(String[] args) throws Exception {
        // 完全jdbc api调用
        jdbcRaw();
        // 使用模版JdbcUtils模版代码来管理连接
        template();
    }

    // jdbc api调用
    private static void jdbcRaw() throws Exception {
        /**
         * 加载jdbc驱动程序；
         * 为了兼容性，jdbc驱动程序通过DriverManager来管理；所有和驱动的交互均通过DriverManager来进行。所以加载完驱动之后，将驱动
         * 注册到DriverManager。通常情况下，驱动程序会在自己的静态初始化快中进行注册，不需要手工调用。
         */
        // 1. 静态加载驱动 - 推荐使用的方式; 此处的驱动程序可能已经实现Driver接口，并在静态初始化方法的时候自动向DriverManager注册。
        Class.forName("com.mysql.jdbc.Driver");
        // 2. 同台注册驱动 - 会造成DriverManager中产生两个一样的驱动，并会对具体的驱动类产生依赖。
        // DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        // 3. 通过系统属性来注册驱动
        // System.setProperty("jdbc.drivers", "");

        /**
         * 定义连接参数
         */
        String url = "jdbc:mysql://localhost:3306";
        String userName = "root";
        String password = "";

        /**
         * 通过驱动程序获取连接
         */
        Connection conn = DriverManager.getConnection(url, userName, password);

        /**
         * 通过连接来发送命令，并接受结果。就像，人为在命令行下执行程序一样；
         */
        // 创建语句
        Statement st = conn.createStatement();
        // 执行语句
        ResultSet rs = st.executeQuery("select * from user");

        /**
         * 处理结果
         */
        // 处理结果
        while (rs.next()) {
            System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t" + rs.getObject(3) + "\t");
        }

        /**
         * 释放资源
         */
        rs.close();
        st.close();
        conn.close();
    }

    // 模板代码
    private static void template() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtils.getConnection();
            // 创建语句
            st = conn.createStatement();
            // 执行语句
            rs = st.executeQuery("select * from user");
            // 处理结果
            while (rs.next()) {
                System.out.println(rs.getObject(1) + "\t" + rs.getObject(2) + "\t" + rs.getObject(3) + "\t");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtils.free(rs, st, conn);
        }
    }
}