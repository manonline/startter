package com.manonline.examples.jndi.ldap.client;

import javax.naming.Context;
import javax.naming.directory.*;
import java.util.Hashtable;

/**
 * Created by davidqi on 2/22/17.
 */
public class LadpUpdateData {

    public static void main(String[] args) {
        /**
         * 设置链接参数
         */
        Hashtable hs = new Hashtable();
        // 设置连接LDAP的实现工厂为com.sun.jndi.ldap.LdapCtxFactory
        hs.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        // 指定提供服务的服务器IP地址和端口号
        hs.put(Context.PROVIDER_URL, "ldap://localhost:389");
        // 使用简单认证来认证用户
        hs.put(Context.SECURITY_AUTHENTICATION, "simple");
        hs.put(Context.SECURITY_PRINCIPAL, "uid=Jordan,ou=Bull,o=NBA");
        hs.put(Context.SECURITY_CREDENTIALS, "good");

        try {

            // 指定了JNDI服务提供者中工厂类（factory class）的名称。Factory负责为其服务创建适当的InitialContext对象。
            // 在上面的代码片断中,为文件系统服务提供者指定了工厂类
            DirContext ctx = new InitialDirContext(hs);
            System.out.println("成功创建初始化context对象!");
            //新建生成一个修改条目类对象,用于存放条目属性
            ModificationItem[] mdi = new ModificationItem[2];
            // 把属性mail的值置为jordan@163.com
            Attribute att0 = new BasicAttribute("mail", "jordan@163.com");
            // 把属性call的值置为12745827
            Attribute att1 = new BasicAttribute("call", "12745827");
            //修改指定属性mail
            mdi[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, att0);
            //增加新属性call到条目
            mdi[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, att1);
            // 修改指定DN条目的属性
            ctx.modifyAttributes("uid=Jordan,ou=Bull,o=NBA", mdi);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}