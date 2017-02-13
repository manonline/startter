package com.manonline.examples.proxy.aop;

/**
 * Created by davidqi on 2/14/17.
 */
public class YellImpl implements Yell {

    @Override
    public void say() {
        System.out.println("Actual Function is being invoked ..." + "Target Class" +  this.getClass());
    }
}
