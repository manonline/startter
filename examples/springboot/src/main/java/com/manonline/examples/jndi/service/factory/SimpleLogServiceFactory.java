package com.manonline.examples.jndi.service.factory;

/**
 * Created by davidqi on 2/22/17.
 */

import com.manonline.examples.jndi.service.service.impl.SimpleLogService;

import javax.naming.Reference;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.spi.ObjectFactory;

public class SimpleLogServiceFactory implements ObjectFactory {
    public Object getObjectInstance(Object obj, Name name, Context ctx, Hashtable<?, ?> env) throws Exception {
        if (obj instanceof Reference) {
            return new SimpleLogService();
        }
        return null;
    }
}