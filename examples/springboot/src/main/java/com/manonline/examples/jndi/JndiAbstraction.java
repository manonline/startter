package com.manonline.examples.jndi;

/**
 * Created by davidqi on 2/22/17.
 * ==============================================================
 * JNDI其实并不做什么特别的事情,只是根据你提供的键名,在目录服务中查找到对应的键值返回给你. 设计JNDI的目的是为了用一个通用的接口来访问
 * 到系统上的一些特殊资源,从而屏蔽了对于不同资源的访问方式的不同。JNDI是用来访问目录服务的一套接口。这其实分成了两块
 * 1. 一个是JNDI访问
 * 2. 一个是实际数据存放的目录服务
 * 关于目录服务,可以去GOOGLE一下,看看LDAP,AD之类的说明应该就清楚了.简单的说就是一组键值对的数据。
 * JNDI可访问的现有的目录及服务有： DNS、XNam 、Novell目录服务、LDAP、 CORBA对象服务、文件系统、Windows XP/2000/NT/Me/9x的注册表、
 * RMI、DSML v1&v2、NIS
 * =============================注册==============================
 * 使用System.setProperty注册，
 * System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.fscontext.FSContextFactory");
 * 默认注册方式 ：jndi.properties。如果程序不显示说明，那么java会在classpath内查找jndi.properties文件来完成注册。
 * jdni.properties例子：java.naming.factory.initial=com.codeline.db.MockInitialContextFactory
 * ---------------------------------------------------------------
 * 命名服务
 * java.naming.factory.initial  : 服务提供者用来创建InitialContext的类名。
 * java.naming.provider.url     : 用来配置InitialContext的初始url
 * java.naming.factory.object   : 用来创建name-to-object映射的类，用于NameClassPair和References。
 * java.naming.factory.state    : 用来创建jndi state的类
 * 目录服务 : 一般还需要安全设置
 * java.naming.security.authentication  : 安全类型，三个值：none，simple或strong。
 * java.naming.security.principal       : 认证信息。
 * java.naming.security.credentials     : 证书信息。
 * java.naming.security.protocol        : 安全协议名。
 * ===============================================================
 *
 * =============================连接命名服务========================
 * 名字服务由InitialContext提供，
 * InitialContext ctx = new InitialContext();
 * 目录服务则使用InitialDirContext。
 * InitialDirContext ctx = new InitialDirContext();
 * ---------------------------------------------------------------
 * InitialContext/InitialDirContext可以接受一个HashMap参数，来做初始化，HashMap中包含的是properties文件中指定的属性。
 * ===============================================================
 *
 * =============================查找对象===========================
 * 不论名字服务还是目录服务，都是使用lookup来查找对象的。使用名字来查找
 * Greeter greeter = (Greeter)ctx.lookup("SayHello");
 * ---------------------------------------------------------------
 * 使用list来获取素有的对象，其中list包含所有的所有的对象名和类名
 * NamingEnumeration list = ctx.list("awt");
 * 使用listBindings来获取素有的对象，其中listBindings包含所有的所有的对象名和实际对象
 * NamingEnumeration bindings = ctx.listBindings("awt")
 * ===============================================================
 *
 * =============================对象操作===========================
 * 对象绑定: bind/rebind/unbind把对象绑定/解绑定到名字上面
 * Fruit fruit = new Fruit("orange");
 * ctx.bind("favorite", fruit);
 * ---------------------------------------------------------------
 * 更改对象名称 : rename
 * ctx.rename("report.txt", "old_report.txt");
 * ---------------------------------------------------------------
 * 获取属性(目录服务 - javax.naming.directory)
 * Context user = (Context)ctx.lookup(dn);
 * Attribute test= attrs .get("test");
 * ---------------------------------------------------------------
 * 修改属性 : modifyAttribute()
 * initial_ctx.modifyAttributes(dn, mod_items);
 * ---------------------------------------------------------------
 * 查找过滤 : search()
 * NamingEnumeration results = initial_ctx.search("ou=Customer,o=ExampleApp", search_attrs);
 * ===============================================================
 *
 * =============================创建上下文=========================
 * 创建上下文 : createSubcontext()
 * initial_ctx.createSubcontext(dn, attrs);
 * 删除上下文 : destroySubcontext()
 * initial_ctx.destroySubcontext(dn)
 */
public class JndiAbstraction {

}
