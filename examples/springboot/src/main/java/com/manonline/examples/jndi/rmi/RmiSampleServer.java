package com.manonline.examples.jndi.rmi;

import com.manonline.examples.jndi.rmi.object.impl.RmiSampleObjectImpl;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by davidqi on 2/22/17.
 * http://www.jianshu.com/p/2c78554a3f36
 * ==================================================
 * 1. 创建远程方法接口，该接口必须继承自Remote接口
 * 2. 创建远程方法接口实现类并继承UnicastRemoteObject类，由于方法参数与返回值最终都将在网络上传输，故必须是可序列化的；
 * 3. 利用java自带rmic工具生成sutb存根类(jdk1.5.0_15/bin/rmic)；
 * 3.1 jdk1.2以后的RMI可以通过反射API可以直接将请求发送给真实类，所以不需要skeleton类了
 * 4. 启动RMI注册服务
 * 4.1 命令行 ：[name@name jdk]$ jdk1.5.0_15/bin/rmiregistry 12312 &
 * 4.2 在代码中人工创建rmiregistry服务 : LocateRegistry.createRegistry(12312);
 * 5. 编写服务端代码，注册远程方法接口实现对象 ： Naming.bind("rmi://192.168.58.164:12312/Address", ojbName);
 * 6. 编写客户端代码, 查找远程对象，Interface int = (Interface)Naming.lookup("rmi://192.168.58.164:12312/Address");
 * 7. 调用方法 int.method();
 * ==================================================
 * 程对象必须实现java.rmi.server.UniCastRemoteObject类，这样才能保证客户端访问获得远程对象时，该远程对象将会把自身的一个
 * 拷贝以Socket的形式传输给客户端，此时客户端所获得的这个拷贝称为“存根”，而服务器端本身已存在的远程对象则称之为“骨架”。
 * 其实此时的存根是客户端的一个""""代理""""，用于与服务器端的通信，而骨架也可认为是服务器端的一个""""代理""""，用于接收客户
 * 端的请求之后调用远程方法来响应客户端的请求。
 * ==================================================
 *
 */
public class RmiSampleServer {

    public static void main(String[] args) {

        /**
         * 创建和安装一个安全管理器，令其支持RMI.作为Java开发包的一部分适用于RMI唯一一个是RMISecurityManager.
         * if(System.getSecurityManager() == null) {
         *     System.setSecurityManager(new  RMISecurityManager());
         * }
         */
        try {
            /**
             * 启动RMI服务器，也可以通过执行 rmiregistry 命令启动注册服务程序，注册服务程序的缺省运行端口为 1099。
             */
            LocateRegistry.createRegistry(8808);

            /**
             * 创建要被远程调用的对象
             */
            RmiSampleObjectImpl server = new RmiSampleObjectImpl();

            /**
             * 要被远程调用的对象绑定到服务器上面，并指定路径
             */
            Naming.rebind("//localhost:8808/SAMPLE-SERVER", server);

            System.out.println("远程对象注册成功，RMI服务已经启动，等待客户端调用....");

        } catch (MalformedURLException me) {
            System.out.println("Malformed URL:" + me.toString());
        } catch (RemoteException re) {
            System.out.println("Remote exception:" + re.toString());
        }
    }
}