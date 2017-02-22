package com.manonline.examples.jndi.ldap.client;

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**
 * Created by davidqi on 2/22/17.
 */
public class LadpRemoveData {

    public static void main(String[] args) {

        Hashtable hs = new Hashtable();

        // 设置连接LDAP的实现工厂为com.sun.jndi.ldap.LdapCtxFactory
        hs.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        // 指定提供服务的服务器IP地址和端口号
        hs.put(Context.PROVIDER_URL, "ldap://localhost:389");
        // 使用简单认证来认证用户
        hs.put(Context.SECURITY_AUTHENTICATION, "simple");
        // 指定DN
        hs.put(Context.SECURITY_PRINCIPAL, "uid=Jordan,ou=Bull,o=NBA");
        // 指定认证密码
        hs.put(Context.SECURITY_CREDENTIALS, "good");

        try {

            // 指定了JNDI服务提供者中工厂类（factory class）的名称。Factory负责为其服务创建适当的InitialContext对象。
            // 在上面的代码片断中,为文件系统服务提供者指定了工厂类。
            DirContext ctx = new InitialDirContext(hs);

            // 删除指定DN条目
            ctx.destroySubcontext("uid=Jordan,ou=Bull,o=NBA");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
