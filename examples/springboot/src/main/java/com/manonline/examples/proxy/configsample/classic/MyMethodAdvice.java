package com.manonline.examples.proxy.configsample.classic;

import org.springframework.aop.MethodBeforeAdvice;
import java.lang.reflect.Method;

/**
 * Created by davidqi on 2/16/17.
 */
public class MyMethodAdvice implements MethodBeforeAdvice {
    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        System.out.println("write log : before－advice method name :" + method.getName());
    }
}