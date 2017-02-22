package com.manonline.examples.osgi.sample.impl;

import com.manonline.examples.osgi.sample.HelloService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * Created by davidqi on 2/23/17.
 */
public class HelloServiceActivator implements BundleActivator {
    ServiceRegistration helloServiceRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        HelloService helloService = new HelloServiceImpl();
        /**
         * 注册服务：
         * 1. service的接口名。如果service实现了多个接口，那样你需要传入一个包含所有接口名的String数组。在这里我们传入的是HelloService
         *    这个接口。
         * 2. 真正的service实现。在例子中我们传了一个HelloServiceImpl实现。
         * 3. service属性。这个参数可以在有多个service实现同一个接口的情况下，消费者用来区分真正感兴趣的service。
         */
        helloServiceRegistration = context.registerService(HelloService.class.getName(), helloService, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        helloServiceRegistration.unregister();
    }

}