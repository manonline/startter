package com.manonline.examples.proxy.configsample.xml;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.aop.ThrowsAdvice;

import java.lang.reflect.Method;

/**
 * Created by davidqi on 2/16/17.
 */
public class Audience implements MethodBeforeAdvice, AfterReturningAdvice, ThrowsAdvice {

    /**
     * 如果是用<aop:before > 去绑定切点和advice，则不需要实现任何方法，因为这个标签，允许指定具体方法
     * <aop:before pointcut-ref="p" method="takeSeats"/>
     * 直接绑定到方法
     */
    public void takeSeats() {
        System.out.println("The audience is taking their seats.");
    }
    public void turnOffCellphone() {
        System.out.println("The audience is turning off their cellphones");
    }
    public void applaud() {
        System.out.println("CLAP CLAP CLAP CLAP CLAP");
    }
    public void demandRefund() {
        System.out.println("Boo! we want our money back!");
    }

    /**
     * 如果是用<aop:advisor> 去绑定切点和advice，则需要继承实现Advice，才能确定到底执行什么方法
     * <aop:advisor pointcut-ref="p" advice-ref="audience" />
     * 绑定到advice, 到底执行什么方法，由advice实现的借口确定。
     */
    @Override
    public void afterReturning(Object o, Method method, Object[] objects, Object o1) throws Throwable {
        applaud();
    }

    @Override
    public void before(Method method, Object[] objects, Object o) throws Throwable {
        takeSeats();
        turnOffCellphone();
    }
}