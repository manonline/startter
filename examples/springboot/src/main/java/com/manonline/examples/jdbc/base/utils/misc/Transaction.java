package com.manonline.examples.jdbc.base.utils.misc;

import com.manonline.examples.jdbc.base.utils.ConnectionUtils;

import java.sql.*;

/**
 * Created by davidqi on 2/11/17.
 * http://www.cnblogs.com/davenkin/archive/2013/02/16/java-tranaction-1.html
 * https://www.ibm.com/developerworks/cn/java/j-lo-spring-ts1/index.html
 * =================
 * http://www.mamicode.com/info-detail-1248286.html
 * http://lxneliu.iteye.com/blog/1664573
 * http://developer.51cto.com/art/200906/127430.htm
 * http://blog.csdn.net/spjhandsomeman/article/details/8556176
 * ==============
 */
public class Transaction {
    static void test() throws Exception {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        Savepoint sp = null;
        try {
            conn = ConnectionUtils.getConnection();
            /**
             * 事务起始点 ：关闭自动提交，手动控制事务
             */
            conn.setAutoCommit(false);

            // 执行第一次操作
            st = conn.createStatement();
            String sql = "update user set money=money-10 where id=1";
            st.executeUpdate(sql);

            /**
             * 设置回滚点
             */
            sp = conn.setSavepoint();

            // 执行第二次操作
            sql = "update user set money=money-10 where id=3";
            st.executeUpdate(sql);

            // 执行第三次操作
            sql = "select money from user where id=2";
            rs = st.executeQuery(sql);
            float money = 0.0f;
            if (rs.next()) {
                money = rs.getFloat("money");
            }
            System.out.println("money:" + money);
            if (money > 300) {
                throw new RuntimeException("已经超过最大值");
            }
            sql = "update user set money=money+10 where id=2";
            st.executeUpdate(sql);
            conn.commit();
            /**
             * 事务终点 ： 提交事务；
             */
        } catch (RuntimeException e) {
            // 捕获异常
            if (conn != null && sp != null) {
                // 会滚到savePoint, 再次提交；
                conn.rollback(sp);
                conn.commit();
            }
//            // 如果没有回滚点，直接回滚事务，无需再次提交
//            if (conn != null) {
//                conn.rollback();
//            }
        } finally {
            ConnectionUtils.free(rs, st, conn);
        }
    }
}
