package com.manonline.examples.proxy.cglibproxy;

import com.manonline.examples.proxy.aspect.PerformanceMonitor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;


import java.lang.reflect.Method;

/**
 * Created by davidqi on 2/13/17.
 * CGlib = Code Generation Library
 * ======================
 * JDK动态代理所创建的代理对象，在JDK 1.3下，性能强差人意。虽然在高版本的JDK中，动态代理对象的性能得到了很大的提高，但是有研究表明，
 * CGLib所创建的动态代理对象的性能依旧比JDK的所创建的代理对象的性能高不少（大概10倍）。但CGLib在创建代理对象时所花费的时间却比JDK动态
 * 代理多（大概8倍），所以对于singleton的代理对象或者具有实例池的代理，因为无须频繁创建代理对象，所以比较适合用CGLib动态代理技术，反之
 * 适合用JDK动态代理技术。值得一提的是，由于CGLib采用动态创建子类的方式生成代理对象，所以不能对目标类中的final方法进行代理。
 * ======================
 * 关于此示例
 * 1. 目标类的所有方法都添加了性能监视横切逻辑，而有时，这并不是我们所期望的，我们可能只希望对业务类中的某些特定方法添加横切逻辑；
 * 2. 我们通过硬编码的方式指定了织入横切逻辑的织入点，即在目标类业务方法的开始和结束前织入代码；
 * 3. 我们手工编写代理实例的创建过程，为不同类创建代理时，需要分别编写相应的创建代码，无法做到通用。
 * ======================
 * 以上三个问题，在AOP中占用重要的地位，因为Spring AOP的主要工作就是围绕以上三点展开：
 * 1. Spring AOP通过Pointcut（切点）指定在哪些类的哪些方法上织入横切逻辑，
 * 2. 通过Advice（增强）描述横切逻辑和方法的具体织入点（方法前、方法后、方法的两端等）。
 * 3. Spring通过Advisor（切面）将Pointcut和Advice两者组装起来。有了Advisor的信息，Spring就可以利用JDK或CGLib的动态代理技术采用
 * 统一的方式为目标Bean创建织入切面的代理对象了。
 */
public class PerformanceProxy implements MethodInterceptor {

    private Enhancer enhancer = new Enhancer();

    public Object getProxy(Class targetClazz) {
        // 将被代理设置成父类
        enhancer.setSuperclass(targetClazz);
        // 将本类设置成子类，并作为callback
        enhancer.setCallback(this);
        // 通过字节码技术动态创建子类实例
        Object subClass = enhancer.create();
        // 返回subClass也即代理类
        return subClass;

    }

    // 拦截父类所有方法的调用
    public Object intercept(Object target, Method method, Object[] args,
                            MethodProxy proxy) throws Throwable {
        // 加入额外的处理
        PerformanceMonitor.begin(target.getClass().getName() + "." + method.getName());

        // 通过反射调用被代理类的方法，并获取返回值
        Object result = proxy.invokeSuper(target, args);

        // 加入额外的处理
        PerformanceMonitor.end();
        return result;
    }
}