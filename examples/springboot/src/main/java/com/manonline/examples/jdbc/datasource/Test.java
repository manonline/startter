package com.manonline.examples.jdbc.datasource;

import java.sql.Connection;

/**
 * Created by davidqi on 2/11/17.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Connection conn = JdbcUtils.getConnection();
            System.out.println(conn);
            JdbcUtils.free(null, null, conn);
        }
    }

}
