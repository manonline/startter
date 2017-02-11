package com.manonline.examples.jdbc.misc;

import com.manonline.examples.jdbc.utils.JdbcUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by davidqi on 2/11/17.
 */
public class Conversion {

    /**
     * 日期转换
     */

    /**
     * 插入大文本
     */
    static void insert(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = JdbcUtils.getConnection();
            String sql = "insert into clob_test(bit_text) values(?)";
            ps = conn.prepareStatement(sql);
            File file = new File("src/com/weijia/type/ClubDemo.java");
            Reader reader = new BufferedReader(new FileReader(file));
            //ps.setAsciiStream(1, new FileInputStream(file), (int)file.length());//英文的文档
            ps.setCharacterStream(1, reader, (int)file.length());
            ps.executeUpdate();
            reader.close();
            /**
             * 读取出来
             * Clob clob = rs.getClob(1);
             * InputStream is = clob.getAsciiStream();
             */
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            JdbcUtils.free(rs,ps,conn);
        }
    }
}
