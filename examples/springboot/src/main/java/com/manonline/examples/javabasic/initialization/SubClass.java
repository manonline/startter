package com.manonline.examples.javabasic.initialization;

import com.manonline.examples.javabasic.initialization.Parent;

/**
 * Created by davidqi on 2/9/17.
 * ==================================================
 * 1. 父类静态变量和静态初始化块(顺序取决于代码中的位置)
 * 2. 子类静态变量和静态初始化块(顺序取决于代码中的位置)
 * 3. 父类成员变量 -> 父类初始化块 -> 父类构造函数
 * 4. 子类成员变量 -> 子类初始化块 -> 子类构造函数
 * ==================================================
 * 初始化块的作用：
 * 1. 减少构造函数中的重复性代码(但是不是主要目的)
 * 2. 匿名内部类无法写构造方法，但是很多时候还是要完成相应的初始化工作
 * ==================================================
 * super()
 * 1. 子类构造函数会默认调用super()；所以父类需要有默认构造函数，除非子类先是调用其它的super方法；
 * 2. this()和super()不能同时出现在一个构造函数里面，因为this必然会调用其它的构造函数，
 * 3. super()和this()均需放在构造方法内第一行。
 * 4. 每个子类构造方法的第一条语句，都是隐含地调用super()，
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