package com.manonline.examples.proxy.springaop;

/**
 * Created by davidqi on 2/14/17.
 */
public class Echo {
    public void say() {
        System.out.println("Actual Function is being invoked ..." + "Target Class" +  this.getClass());
    }
}
