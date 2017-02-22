package com.manonline.examples.jndi;

/**
 * Created by davidqi on 2/21/17.
 */

import java.io.FileInputStream;
import java.util.Properties;
import javax.naming.*;
import javax.naming.Context;
import javax.naming.InitialContext;

public class FileJndiTest {
    /**
     *
     */
    public FileJndiTest() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        try {
            Properties env = new Properties();
            /**
             * 载入FileSystem的SPI相关参数,包括初始上下文工厂，服务URL，等等
             * ===================================================================
             * java.naming.factory.initial=com.sun.jndi.fscontext.RefFSContextFactory
             */
            env.load(new FileInputStream("fileSystemService.properties"));

            /**
             * 再额外增加一个参数，需要遍历的文件目录
             */
            env.put(Context.PROVIDER_URL, "file:///c:/");

            /**
             * 创建上下文
             */
            Context ctx = new InitialContext(env);

            /**
             * 创建子上下文
             */
            ctx.createSubcontext("sylilzy");

            /**
             * 获取文件列表
             */
            NamingEnumeration list = ctx.list("/");

            /**
             * 遍历
             */
            while (list.hasMore()) {
                NameClassPair nc = (NameClassPair) list.next();
                System.out.println(nc);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}