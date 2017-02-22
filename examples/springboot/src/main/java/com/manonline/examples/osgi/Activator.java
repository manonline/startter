package com.manonline.examples.osgi;

import com.manonline.examples.osgi.sample.HelloService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 * Created by davidqi on 2/23/17.
 * http://www.tuicool.com/articles/B7RZRf
 * 1. Bundle : OSGi中的构建模块，jar包 + 增强的MANIFEST文件
 * 2. OSGi容器初始化，并管理Bundle生命周期
 * 2.1 classloader ： OSGI容器自定义了java class loader来有选择的加载类。OSGI容器为每一个bundle都创建了不同的class loader。这也就
 *     使得可以在MANFEST文件中指定，暴露或者屏蔽什么样的package/class.bundle可以访问的classes包括
 *     - Boot classpath：所有的java基础类。
 *     - Framework classpath：OSGI框架级别的classloader加载的类
 *     - Bundle classpath：Bundle本身引用的关系紧密的JAR的路径
 *     - Imported packages：就是在MANIFEST.MF里声明的导入包，一旦声明，在bundle内就可见了。
 * 2.2 Activator : start()/stop()
 * 3. Bundle中通过ServiceRegistration向容器注册服务 (提供实现类，并暴露接口)
 * 4. 客户端通过context.getService来取得实现引用，并通过接口对方法进行调用。
 */
public class Activator implements BundleActivator {

    ServiceReference helloServiceReference;

    public void start(BundleContext context) throws Exception {
        System.out.println("Hello World!!");
        helloServiceReference = context.getServiceReference(HelloService.class.getName());
        HelloService helloService = (HelloService) context.getService(helloServiceReference);
        System.out.println(helloService.sayHello());
    }

    public void stop(BundleContext context) throws Exception {
        System.out.println("Goodbye World!!");
        context.ungetService(helloServiceReference);
    }
}