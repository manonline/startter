package com.manonline.examples.jdbc.base.utils.misc;

import com.manonline.examples.jdbc.base.entity.User;
import com.manonline.examples.jdbc.base.utils.ConnectionUtils;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * Created by davidqi on 2/11/17.
 * 其实就是使用反射技术得到实体类中所有属性的set方法，然后通过set方法进行属性值的填充。
 * 这里唯一要注意的问题就是返回的结果集中的字段名称必须要和实体类中的属性名称相同，要做到这一点我们又两种方式：
 * 1. 一种是直接将表中的字段名称和实体类中的属性名称相同
 * 2. 一种是使用别名的方式来操作，将别名设置的和实体类中的属性名称相同
 */
public class OrMapping {

    public static void main(String[] args) throws Exception{
        // User user = test("select * from user",User.class);
        // 使用别名来规定列名和属性名相同
        User user = test("select id as Id,name as Name,birthday as Birthday,money as Money from user", User.class);
        System.out.println(user);
    }

    /**
     * 使用泛型
     */
    static <T> T test(String sql, Class<T> clazz) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // 执行SQL
            conn = ConnectionUtils.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();

            /**
             * 获取数据库元数据
             */
            ResultSetMetaData rsmd = rs.getMetaData();
            int count = rsmd.getColumnCount();
            String[] colNames = new String[count];
            for (int i = 1; i <= count; i++) {
                // 使用字段名
                // colNames[i - 1] = rsmd.getColumnName(i);

                // 使用SQL中的别名；
                // 使别名等于Java字段名。这样别名起到了数据库列名和Java属性名的mapping作用。
                colNames[i - 1] = rsmd.getColumnLabel(i);
            }

            /**
             * 通过传入的类信息创建类对象。此处调用的是默认构造函数(无参)，这也就是为什么Hibernate要求默认构造函数。
             */
            T object = clazz.newInstance();
            /**
             * 通过反射来调用setter方法，给属性赋值
             */
            if (rs.next()) {
                // 获取所有类方法
                Method[] ms = clazz.getMethods();
                for (int i = 0; i < colNames.length; i++) {
                    String colName = colNames[i];
                    String methodName = "set" + colName;
                    for (Method method : ms) {
                        /**
                         * 通过列名来找到实体类中的属性方法(这里要注意的是set方法的格式是:setXxx首字母是大写的)。这里直接使用忽视大小
                         * 写的相等的方法，或者使用上面的重命名来解决这个问题
                         */
                        if (methodName.equalsIgnoreCase(method.getName())) {
                            // rs.getObject会尝试转换成对应的Java类型
                            method.invoke(object, rs.getObject(colNames[i]));
                        }
                    }
                }
            }
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtils.free(rs, ps, conn);
        }
        return null;
    }
}
