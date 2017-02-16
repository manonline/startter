package com.manonline.examples.proxy.aop.config;

/**
 * Created by davidqi on 2/16/17.
 */
public class App implements IApp {
    public void method() {
        System.out.println("this is method1");
    }

    public void add() {
        System.out.println("this is add");
    }
}
