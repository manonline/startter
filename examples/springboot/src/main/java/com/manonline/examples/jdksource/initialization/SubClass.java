package com.manonline.examples.jdksource.initialization;

/**
 * Created by davidqi on 2/9/17.
 * 1. 父类静态变量和静态初始化块
 * 2. 子类静态变量和静态初始化块
 * 3. 父类成员变量 -> 父类初始化块 -> 父类构造函数
 * 4. 子类成员变量 -> 子类初始化块 -> 子类构造函数
 */
public class SubClass extends Parent {
    // 静态变量
    public static String s_StaticField = "子类--静态变量";
    // 变量
    public String s_Field = "子类--变量";
    // 静态初始化块
    static {
        System.out.println(s_StaticField);
        System.out.println("子类--静态初始化块");
    }
    // 初始化块
    {
        System.out.println(s_Field);
        System.out.println("子类--初始化块");
    }

    // 构造器
    public SubClass() {
        System.out.println("子类--构造器");
    }

    // 程序入口
    public static void main(String[] args) {
        new SubClass();
    }
}