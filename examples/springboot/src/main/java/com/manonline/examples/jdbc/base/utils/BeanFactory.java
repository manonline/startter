package com.manonline.examples.jdbc.base.utils;

/**
 * Created by davidqi on 2/11/17.
 */

import java.io.FileInputStream;
import java.util.Properties;

public class BeanFactory {
    /**
     * 单例模式
     */
    private static Properties prop = new Properties();
    private static BeanFactory instance = new BeanFactory();

    private BeanFactory() {
        /**
         * 通过读取属性文件来动态的加载Dao层类
         */
        Properties prop = new Properties();
        try {
            FileInputStream fis = new FileInputStream("src/com/manonline/example/jdbc/daoconfig.properties");
            prop.load(fis);
            fis.close();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static BeanFactory getInstance() {
        // 没有考虑多线程????
        return instance;
    }

    public Object getBean(String beanName) {
        /**
         * 非常简单的模型，每次均尝试去加载类，并创建对象，并没有做缓存和单例模式；
         */
        String className = prop.getProperty(beanName);
        Class<?> clazz = null;

        // 根据配置文件中指定的包名类名来加载Class
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Object beanInstance = null;

        // 根据类信息(上一步获取)来创建实例
        try {
            beanInstance = clazz.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // 返回Bean的实例
        return beanInstance;
    }

}