package com.manonline.examples.jndi.service;

/**
 * Created by davidqi on 2/22/17.
 */

import com.manonline.examples.jndi.service.service.DBService;
import com.manonline.examples.jndi.service.service.LogService;

import javax.naming.Context;

public class JNDIClient {

    public static void main(String[] args) {
        try {
            /**
             * 初始化JNDI容器
             */
            JNDIContainer container = new JNDIContainer();
            container.init();

            /**
             * 获取JNDI上下文
             */
            Context ctx = container.getContext();

            /**
             * 在上下文中通过名字查找对象 － DBService
             */
            DBService db = (DBService) ctx.lookup("DBService");
            System.out.println("db location is:" + db.getLocation() + ",state is:" + db.getState());
            db.accessDB();

            /**
             * 在上下文中通过名字查找对象 － LogService
             */
            LogService ls = (LogService) ctx.lookup("LogService");
            ls.log("this is a log message.");

            /**
             * 关闭上下文环境
             */
            container.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}