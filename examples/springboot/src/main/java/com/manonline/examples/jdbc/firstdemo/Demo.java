package com.manonline.examples.jdbc.firstdemo;

/**
 * Created by davidqi on 2/11/17.
 */

import com.manonline.examples.jdbc.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Demo {

    public static void main(String[] args) throws Exception {
        //测试代码：
        jdbcRaw();
        //标准规范代码：
        template();
    }

    //模板代码
    public static void template() {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
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
            JdbcUtils.free(rs, st, conn);
        }
    }

    // 测试
    static void jdbcRaw() throws Exception {
        /**
         * 获取jdbc驱动程序；为了兼容性，jdbc驱动程序通过DriverManager来管理；所有和驱动的交互均通过DriverManager来进行。所以加载完
         * 驱动之后，将驱动注册到DriverManager。
         */
        // 静态加载驱动 - 推荐使用的方式; 此处的驱动程序可能已经实现Driver接口，并在静态初始化方法的时候自动向DriverManager注册。
        Class.forName("com.mysql.jdbc.Driver");
//        // 注册驱动 - 会造成DriverManager中产生两个一样的驱动，并会对具体的驱动类产生依赖。
//        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
//        // 通过系统属性来注册驱动
//        System.setProperty("jdbc.drivers", "");

        /**
         * 通过驱动程序创建连击
         */
        // 建立连接
        String url = "jdbc:mysql://localhost:3306";
        String userName = "root";
        String password = "";
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

}
