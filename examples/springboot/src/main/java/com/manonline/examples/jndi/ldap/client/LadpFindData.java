package com.manonline.examples.jndi.ldap.client;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**
 * Created by davidqi on 2/22/17.
 */
public class LadpFindData {

    public static void main(String[] args) {
        /**
         * 设置链接参数
         */
        // 创建Hashtable以存储JNDI将用于连接目录服务的环境变量
        Hashtable connProperties = new Hashtable();
        // 设置连接Ldap的实现工厂
        connProperties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        // 指定LDAP服务器IP地址为本机及端口号为389
        connProperties.put(Context.PROVIDER_URL, "ldap://localhost:389");

        try {
            // 得到初始目录环境的一个引用
            DirContext ctx = new InitialDirContext(connProperties);

            /**
             * 对于作为引用绑定在目录树中的对象，JNDI SPI指定针对引用创建实际的对象。因此，在程序中只需要认为用lookup()方法返回的对象
             * 就是实际对象，而不用在调用什么方法来将引用转换为实际对象了，因为所有的工作都由JNDI内部完成了。
             */
            // 利用lookup查找返回指定DN的条目对象
            Person person = (Person) ctx.lookup("uid=Jordan,ou=Bull,o=NBA");
            // 利用远程对象调用远程方法,返回Age变量的值
            String age = person.getAge();
            // 利用远程对象调用远程方法,返回Name变量的值
            String name = person.getName();
            // 输出Name的值
            System.out.println("name is :" + name);

            // 根据结点的DN来查找它的所有属性, 然后再从属性中得到所有的值,注意一个属性可以有多个值
            Attributes attrs = ctx.getAttributes("uid=Jordan,ou=Wizzard,o=NBA");
            // 循环获取并输出这个属性的所有属性值
            for (NamingEnumeration ae = attrs.getAll(); ae.hasMore(); ) {
                // 获取一个属性
                Attribute attr = (Attribute) ae.next();
                System.out.println("Attribute : " + attr.getID());
                // 循环取得输出这个属性的所有属性值
                for (NamingEnumeration ve = attr.getAll(); ve.hasMore(); ) {
                    System.out.println("  Value : " + ve.next());
                }
            }

            // 成功打印提示信息
            System.out.println("find object success ");
            // 调用该对象的函数
            person.toString();

            // 关闭初始目录环境
            ctx.close();

        } catch (NamingException ex) {
            System.err.println(ex.toString());
        }
    }
}