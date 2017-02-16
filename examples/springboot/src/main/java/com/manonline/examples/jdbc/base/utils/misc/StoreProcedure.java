package com.manonline.examples.jdbc.base.utils.misc;

import com.manonline.examples.jdbc.base.utils.ConnectionUtils;

import java.sql.*;

/**
 * Created by davidqi on 2/11/17.
 */
public class StoreProcedure {
    static void test() throws Exception {
        Connection conn = null;
        //需要使用CallableStatement
        CallableStatement cs = null;
        try {
            conn = ConnectionUtils.getConnection();
            //name,birthday,money,id
            //存储过程名称是:addUser
            String sql = "{ call addUser(?,?,?,?)}";
            cs = conn.prepareCall(sql);
            cs.registerOutParameter(4, Types.INTEGER);
            cs.setString(1, "jiangwei");
            cs.setDate(2, new Date(System.currentTimeMillis()));
            cs.setFloat(3, 300);
            cs.executeUpdate();

            int id = cs.getInt(4);
            System.out.println("id:" + id);

            /**
             * 通过这个存储过程来获取主键id是有一个问题,不同的数据库,存储过程的编写语法是不一样的，所以这种方法是不通用
             * 还有另外一种方法是OtherApi,通过JDBC中的api来获取
             */

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionUtils.free(null, cs, conn);
        }
    }
}
