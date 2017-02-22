package com.manonline.examples.jndi.service;

/**
 * Created by davidqi on 2/22/17.
 */

import com.manonline.examples.jndi.service.service.DBService;
import com.manonline.examples.jndi.service.service.LogService;

import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * https://www.captechconsulting.com/blogs/a-riff-on-the-reffscontextfactory
 */
public class JNDIContainer {
    private Context ctx = null;

    public void init() throws Exception {
        /**
         * 配置JNDI Service Provider － 此示例使用FSContext
         */
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
        env.put(Context.PROVIDER_URL, "file:/c:/sample");

        /**
         * 初始化JNDI上下文
         */
        ctx = new InitialContext(env);

        /**
         * 向JNDI上下文中添加对象
         */
        loadServices();
    }

    /**
     * 通过properties文件实例化对象并绑定到jndi context中
     */
    private void loadServices() throws Exception {
        InputStream in = getClass().getResourceAsStream("JNDIContainer.properties");
        Properties props = new Properties();
        props.load(in);

        String s = props.getProperty("DBServiceClass");
        Object obj = Class.forName(s).newInstance();
        if (obj instanceof DBService) {
            DBService db = (DBService) obj;
            String[] ss = props.getProperty("DBServiceProperty").split(";");
            for (int i = 0; i < ss.length; i++)
                db.setProperty(i, ss[i]);
            /**
             * 绑定LogService到上下文中 － 对象名和对象引用
             */
            ctx.rebind(props.getProperty("DBServiceName"), db);
        }

        s = props.getProperty("LogServiceClass");
        obj = Class.forName(s).newInstance();
        if (obj instanceof LogService) {
            LogService log = (LogService) obj;
            /**
             * 绑定LogService到上下文中 － 对象名和对象引用
             */
            ctx.rebind(props.getProperty("LogServiceName"), log);
        }
    }

    public void close() throws NamingException {
        ctx.close();
    }

    public Context getContext() {
        return ctx;
    }
}
