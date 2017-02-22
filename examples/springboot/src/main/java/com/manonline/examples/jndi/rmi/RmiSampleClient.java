package com.manonline.examples.jndi.rmi;

import com.manonline.examples.jndi.rmi.object.RmiSampleObject;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;

/**
 * Created by davidqi on 2/22/17.
 */
public class RmiSampleClient {
    public static void main(String[] args) {
        try {
            /**
             * 远程对象的地址 : //RMI SERVER HOST + PORT/OBJECT NAME;
             */
            String url = "//localhost:8808/SAMPLE-SERVER";

            /**
             * 获取远程对象
             */
            RmiSampleObject RmiObject = (RmiSampleObject) Naming.lookup(url);

            /**
             * 调用远程对象的方法
             */
            System.out.println(" 1 + 2 =  " + RmiObject.sum(1, 2));
        } catch (RemoteException exc) {
            System.out.println("Error  in lookup: " + exc.toString());
        } catch (MalformedURLException exc) {
            System.out.println("Malformed URL: " + exc.toString());
        } catch (java.rmi.NotBoundException exc) {
            System.out.println("NotBound:  " + exc.toString());
        }
    }
}