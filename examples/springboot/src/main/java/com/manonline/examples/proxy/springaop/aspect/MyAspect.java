package com.manonline.examples.proxy.springaop.aspect;

/**
 * Created by davidqi on 2/14/17.
 */
public class MyAspect {

    public void doBefore() {
        System.out.println("Before Executing Proxied Object-Method ....");
        System.out.println("===========================================");
    }

    public void doAfter() {
        System.out.println("===========================================");
        System.out.println("Before Executing Proxied Object-Method ....");
    }
}
