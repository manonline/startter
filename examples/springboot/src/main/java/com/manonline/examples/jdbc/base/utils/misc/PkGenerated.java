package com.manonline.examples.jdbc.base.utils.misc;

import com.manonline.examples.jdbc.base.utils.ConnectionUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by davidqi on 2/12/17.
 */
public class PkGenerated {
    public static void main(String[] args) throws Exception {
        test();
    }

    static void test() throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionUtils.getConnection();
            String sql = "insert into user(name,birthday,money) values('jiangwei','1987-01-01',400)";
            ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);//生成主键id
            ps.executeUpdate();
            //可能是组合主键,可能会返回一个ResultSet
            rs = ps.getGeneratedKeys();
            int id = 0;
            if (rs.next()) {
                id = rs.getInt(1);
            }
            System.out.println("id:" + id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionUtils.free(rs, ps, conn);
        }
    }
}
