package com.manonline.examples.jndi.service.factory;

/**
 * Created by davidqi on 2/22/17.
 */

import com.manonline.examples.jndi.service.service.impl.SimpleDBService;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

/**
 * 数据库服务对象工厂类被JNDI提供者调用来创建数据库服务实例，对JNDI的客户不可见。
 */
public class SimpleDBServiceFactory implements ObjectFactory {
    /**
     * 根据Reference中存储的信息创建出SimpleDBService实例
     */
    public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable<?, ?> env) throws Exception {
        if (obj instanceof Reference) {
            // 创建实例
            SimpleDBService db = new SimpleDBService();

            // 解析传入的参数并赋值给新创建的对象；
            Reference ref = (Reference) obj;

            String location = (String) ref.get("location").getContent();
            db.setProperty(0, location);

            String state = (String) ref.get("state").getContent();
            db.setProperty(1, state);

            // 返回对象
            return db;
        }
        return null;
    }
}