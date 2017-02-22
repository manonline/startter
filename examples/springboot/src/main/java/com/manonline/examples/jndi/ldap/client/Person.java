package com.manonline.examples.jndi.ldap.client;

import java.io.Serializable;

/**
 * Created by davidqi on 2/22/17.
 * JNDI定义了一个Serializable接口类来为应用信息的表达提供一种统一的方式。Serializable接口类包含了诸如地址、类型信息等用于访问具体对象
 * 的信息。为了能将对象的引用绑定到目录树中，该对象的类必须实现Referenceable接口，其中包含了方法 getReference()。开发者可以在该对象上
 * 调用getReference()方法来获得Reference以用于绑定。Serializable接口与Referenceable接口有颇多相似之处，不同在于Referenceable可引
 * 用的对象只包含一些用于创建实际对象的信息，而Serializable会包含更多的甚至不适合存储在目录结构中的信息。
 */
public class Person implements Serializable {
    String Name = "";
    String Age = "";

    public Person() {
    }

    public Person(String namePara, String age) {
        Name = namePara;
        Age = age;
    }

    //用于返回变量Name的值
    public String getName() {
        return Name;
    }

    //用于返回变量Age的值
    public String getAge() {
        return Age;
    }
}