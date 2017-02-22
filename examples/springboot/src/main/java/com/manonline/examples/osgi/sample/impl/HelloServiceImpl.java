package com.manonline.examples.osgi.sample.impl;

import com.manonline.examples.osgi.sample.HelloService;

/**
 * Created by davidqi on 2/23/17.
 */
public class HelloServiceImpl implements HelloService {
    public String sayHello() {
        System.out.println("Inside HelloServiceImple.sayHello()");
        return "Say Hello";
    }
}