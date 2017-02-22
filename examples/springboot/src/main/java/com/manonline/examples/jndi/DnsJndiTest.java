package com.manonline.examples.jndi;

import javax.naming.Context;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.directory.*;
import java.util.Properties;

/**
 * Created by davidqi on 2/22/17.
 */

public class DnsJndiTest {
    public static void main(String[] args) throws Exception {
        /**
         * 设置驱动和连接属性
         */
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.dns.DnsContextFactory");
        //此IP一定要为要访问的DNS服务器的IP,可通过网络设置查看
        env.put(Context.PROVIDER_URL, "dns://10.17.45.239");

        DirContext ctx = new InitialDirContext(env);
        // a:javax.naming.directory.InitialDirContext@1bf216a
        System.out.println("a:" + ctx);

        DirContext ctx1 = (DirContext) ctx.lookup("www.sina.com");
        // b:com.sun.jndi.dns.DnsContext@3a6727
        System.out.println("b:" + ctx1);

        // c:-----------------------------------------------
        // attribute: CNAME
        // value: us.sina.com.cn.
        printAttributes("c:", ctx1.getAttributes(""));
        // d:-----------------------------------------------
        // attribute: CNAME
        // value: us.sina.com.cn.
        printAttributes("d:", ctx.getAttributes("www.sina.com"));


        Attributes attrs1 = ctx.getAttributes("www.sina.com", new String[]{"a"});
        Attributes attrs2 = ctx.getAttributes("www.163.com", new String[]{"a"});
        Attributes attrs3 = ctx1.getAttributes("", new String[]{"a"});
        Attributes attrs4 = ctx.getAttributes("www.baidu.com", new String[]{"a"});
        // e:-----------------------------------------------
        // attribute: A
        // value: 218.30.66.67
        // value: 218.30.66.68
        // value: 218.30.66.69
        // ......
        printAttributes("e:", attrs1);
        // f:-----------------------------------------------
        // attribute: A
        // value: 220.181.28.42
        printAttributes("f:", attrs2);
        // g:-----------------------------------------------
        // attribute: A
        // value: 218.30.66.68
        // value: 218.30.66.69
        // value: 218.30.66.70
        // ......
        printAttributes("g:", attrs3);
        // attrs4:-----------------------------------------------
        // attribute: A
        // value: 220.181.27.5
        printAttributes("attrs4:", attrs4);

        System.out.println("nameParse:" + ctx1.getNameInNamespace());
        //list,此方法会导致程序lock
        //listEnumation("list:",ctx.list(""));
        //----------------------search
        Attributes matchAttrs = new BasicAttributes(true);
        matchAttrs.put(new BasicAttribute("a", "61.172.201.13"));
        NamingEnumeration answer = ctx1.search("www.sina.com", matchAttrs);
        printNamingEnumeration("search :", answer);
    }

    public static void printAttributes(String tag, Attributes attres)
            throws Exception {
        for (NamingEnumeration ae = attres.getAll(); ae.hasMore(); ) {
            Attribute attr = (Attribute) ae.next();
            System.out.println(tag
                    + "-----------------------------------------------\nattribute: "
                    + attr.getID());
            /* Print each value */
            for (NamingEnumeration e = attr.getAll(); e.hasMore(); System.out.println("value: " + e.next()));
        }
    }

    public static void listEnumation(String tag, NamingEnumeration name)
            throws Exception {
        for (; name.hasMore(); ) {
            NameClassPair nameClass = (NameClassPair) name.next();
            System.out.println(tag
                    + "-----------------------------------------------\nattribute: "
                    + nameClass.getName() + ":"
                    + nameClass.getClassName());
        }
    }

    public static void printNamingEnumeration(String tag, NamingEnumeration e)
            throws Exception {
        for (; e.hasMore(); ) {
            Attribute attr = (Attribute) e.next();
            System.out.println(tag
                    + "-----------------------------------------------\nattribute: "
                    + attr.getID());
            /* Print each value */
            for (NamingEnumeration ve = attr.getAll(); ve.hasMore(); System.out.println("value: " + ve.next()));
        }
    }
}
