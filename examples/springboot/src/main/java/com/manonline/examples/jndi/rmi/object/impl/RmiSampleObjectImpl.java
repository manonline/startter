package com.manonline.examples.jndi.rmi.object.impl;

import com.manonline.examples.jndi.rmi.object.RmiSampleObject;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * Created by davidqi on 2/22/17.
 * 需要被远程调用的对象必须扩展远程对象java.rmi.UnicastRemoteObject类，并实现所定义的远程接口。
 * 必须为远程对象定义构造函数，即使只准备定义一个默认构造函数，用它调用基础类构造函数。因为基础类构造函数可能会抛出
 * java.rmi.RemoteException，所以即使别无它用必须抛出java.rmi.RemoteException例外。
 */
public class RmiSampleObjectImpl extends UnicastRemoteObject implements RmiSampleObject {

    // 覆盖默认构造函数并抛出RemoteException
    public RmiSampleObjectImpl() throws RemoteException {
        super();
    }

    // 所有远程实现方法必须抛出RemoteException
    public int sum(int a, int b) throws RemoteException {
        return a + b;
    }
}