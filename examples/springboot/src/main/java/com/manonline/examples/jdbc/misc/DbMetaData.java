package com.manonline.examples.jdbc.misc;

import com.manonline.examples.jdbc.utils.JdbcUtils;
import org.omg.CORBA.Object;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by davidqi on 2/11/17.
 */
public class DbMetaData {

    static void databaseMetaData() throws Exception {
        Connection conn = JdbcUtils.getConnection();
        /**
         * 这些信息对于那些框架的编写就很有用了，因为框架是要兼容各个数据库类型的,如Hibernate中有一个方言设置。如果没有设置的话，他就会
         * 自己使用以下的api进行查找是那个数据库
         */
        DatabaseMetaData metaData = conn.getMetaData();
        System.out.println("databaseName:" + metaData.getDatabaseProductName());
        System.out.println("driverName:" + metaData.getDriverName());
        System.out.println("isSupportBatch:" + metaData.supportsBatchUpdates());
        System.out.println("isSupportTransaction:" + metaData.supportsTransactions());
    }

    static void queryMetaData(String sql, Object[] params) throws Exception {
        Connection conn = JdbcUtils.getConnection();
        /**
         * 参数的元数据信息: Statement是没有参数的元数据信息的(因为Statement不支持?),查看源代码，返回的都是varchar
         */
        PreparedStatement ps = conn.prepareStatement(sql);
        //必须在连接数据库中的时候添加这个参数generateSimpleParameterMetadata=true
        //不然是获取不到参数的，而且会报异常
        ParameterMetaData pMetaData = ps.getParameterMetaData();
        int count = pMetaData.getParameterCount();
        for (int i = 1; i <= count; i++) {
            // 因为mysql没有去查询库，所以不能根据查询的字段就能获取字段的类型，所有都返回varchar
            System.out.println(pMetaData.getParameterClassName(i));
            System.out.println(pMetaData.getParameterType(i));
            System.out.println(pMetaData.getParameterTypeName(i));
            // 假定我们传入的参数的顺序和sql语句中的占位符的顺序一样的
            ps.setObject(i, params[i - 1]);
        }
        ps.executeQuery();
    }

    static void resultSetMetaData(String sql) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            // 获取元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            String[] colNames = new String[count];
            for (int i = 1; i <= count; i++) {
                // 列名
                System.out.println(rsmd.getColumnName(i));
                // 列别名 : select name as n from user;,有别名的话就返回的是别名，而不是原始的列名了
                System.out.println(rsmd.getColumnLabel(i));//列的别名:
                // 列数据类型 : Java类型，也即rs.getObject(columnName)返回的类全限定名
                System.out.println(rsmd.getColumnClassName(i));
                // 列数据类型 : sqlData
                System.out.println(rsmd.getColumnType(i));
                // 填充类名数组，后面程序会用到
                colNames[i - 1] = rsmd.getColumnName(i);
            }
            // 将结果构建一个Map，存储一行的所有列和列值；列名是key, 列的值是value
            Map<String, java.lang.Object> rowData = null;
            // 构建一个列表，用来存放所有的row
            List<Map<String, java.lang.Object>> resultSet = new LinkedList<>();
            while (rs.next()) {
                rowData = new HashMap<>();
                for (int i = 0; i < colNames.length; i++) {
                    // columnName 和 value构建一个entry
                    rowData.put(colNames[i], rs.getObject(colNames[i]));
                }
                resultSet.add(rowData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.free(rs, ps, conn);
        }
    }
}
