package com.manonline.examples.jndi.service.service.impl;

import com.manonline.examples.jndi.service.service.LogService;
import com.manonline.examples.jndi.service.factory.SimpleLogServiceFactory;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Referenceable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by davidqi on 2/22/17.
 */
public class SimpleLogService implements Referenceable, LogService {
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * SimpleLogService没有任何属性，通过SimpleLogService类名创建出来的SimpleLogService实例都是一样的，因此这里无需添加RefAddr了。
     */
    public Reference getReference() throws NamingException {
        return new Reference(getClass().getName(), SimpleLogServiceFactory.class.getName(), null);
    }

    public void log(String message) {
        String date = sdf.format(new Date());
        System.out.println(date + ":" + message);
    }
}