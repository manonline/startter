package com.manonline.examples.jndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by davidqi on 2/21/17.
 * ===============================================================================================
 * 随着分布式应用的发展，远程访问对象访问成为常用的方法。虽然说通过 Socket等编程手段仍然可实现远程通信，但按照模式的理论来说，仍是有其
 * 局限性的。RMI技术，RMI-IIOP技术的产生，使远程对象的查找成为了技术焦点。JNDI技术就应运而生。JNDI技术产生后，就可方便的查找远程或是
 * 本地对象。JNDI的架构与JDBC的架构非常类似.JNDI架构提供了一组标准命名系统的API。
 * ===============================================================================================
 * ---- naming API
 * javax.naming
 * ---- naming manager
 * javax.naming.directory
 * javax.naming.event
 * javax.naming.ldap
 * ---- service provider api
 * javax.naming.spi
 *      LDAP(Lightweight Directory Access Protocol)服务提供者
 *      CORBA COS（Common Object Request Broker Architecture Common Object Services）命名服务提供者
 *      RMI(Java Remote Method Invocation)注册服务提供者
 *      DNS(Domain Name System)服务提供者.
 *      FSSP(File System Service Provider)文件系统服务提供者
 *      其它服务提供者
 * ===============================================================================================
 * 代码只涉及到了JNDI API，其它细节如初始化JBoss JNDI的初始上下文，建立网络连接，与服务器通信，对我们来说都是透明的，另外，我们
 * 将JBoss JNDI的SPI包中的类名作为参数传入了程序中,要访问一个远程对象，我们所做的就这么多。
 * ===============================================================================================
 */
public class JbossJndiTest {

    public JbossJndiTest() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        try {
            Properties env = new Properties();
            /**
             * 载入jboss的SPI相关参数,包括初始上下文工厂，服务URL，等等
             * ===================================================================
             * java.naming.factory.initial=org.jnp.interfaces.NamingContextFactory
             * java.naming.factory.url.pkgs=org.jboss.naming:org.jnp.interfaces
             * java.naming.provider.url=jnp://localhost:1099
             */
            env.load(new FileInputStream("jbossJndi.properties"));
            env.list(System.out);
            /**
             * 通过JNDI API 初始化上下文
             */
            InitialContext ctx = new javax.naming.InitialContext(env);
            System.out.println("Got context");

            // create a subContext
            ctx.createSubcontext("/sylilzy");
            ctx.createSubcontext("sylilzy/sily");
            // rebind a object
            ctx.rebind("sylilzy/sily/a", "I am sily a!");
            ctx.rebind("sylilzy/sily/b", "I am sily b!");
            // lookup context
            Context ctx1 = (Context) ctx.lookup("sylilzy");
            Context ctx2 = (Context) ctx1.lookup("/sylilzy/sily");
            ctx2.bind("/sylilzy/g", "this is g");
            // lookup binded object
            Object o;
            o = ctx1.lookup("sily/a");
            // get object from jndi:I am sily a!
            System.out.println("get object from jndi:" + o);
            //rename the object
            ctx2.rename("/sylilzy/g", "g1");
            o = ctx2.lookup("g1");
            // get object from jndi:this is g
            System.out.println("get object from jndi:" + o);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}