package com.manonline.examples.proxy.rawproxy.jdkproxy;

import com.manonline.examples.proxy.rawproxy.aspect.PerformanceMonitor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by davidqi on 2/13/17.
 * InvocationHandler可以看成是一个编织器，将业务代码和切面逻辑编织在一起。
 */
public class PerformanceHandler implements InvocationHandler {

    private Object target;

    public PerformanceHandler(Object target) {
        // target为被代理的业务类
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // proxy参数不用

        // 加入额外的处理
        PerformanceMonitor.begin(target.getClass().getName() + "." + method.getName());

        // 通过反射调用被代理类的方法，并获取返回值
        Object returnVal = method.invoke(target, args);

        // 加入额外的处理
        PerformanceMonitor.end();

        // 返回值
        return returnVal;
    }
}
