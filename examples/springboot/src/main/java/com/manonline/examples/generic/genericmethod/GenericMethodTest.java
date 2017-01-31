package com.manonline.examples.generic.genericmethod;

/**
 * Created by davidqi on 1/31/17.
 */
public class GenericMethodTest {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        GenericMethod genericMethod = new GenericMethod();
        //调用泛型方法
        Object obj = genericMethod.getObject(Class.forName("com.manonline.examples.generic.genericmethod.User"));
        //判断obj的类型是否是指定的User类型
        System.out.println(obj instanceof User);
    }
}