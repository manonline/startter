package com.manonline.examples.jndi.rmi.object;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by davidqi on 2/22/17.
 * 接口声明每个要被远程调用的方法。客户端只要获取接口，而不需要获得具体实现类来进行调用。
 * 1. 远程接口必须为public属性。如果不这样，除非客户端与远程接口在同一个包内，否则当试图装入实现该远程接口的远程对象时，
 *    调用会得到错误结果。
 * 2. 远程接口必须扩展接口java.rmi.Remote。
 * 3. 除与应用程序本身特定的例外之外，远程接口中的每个方法都必须在自己的throws从句中声明java.rmi.RemoteException。
 */
public interface RmiSampleObject extends Remote {
    // 需要被远程调用的方法
    int sum(int a, int b) throws RemoteException;
}