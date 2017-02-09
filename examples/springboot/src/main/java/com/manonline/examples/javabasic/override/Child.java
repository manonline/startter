package com.manonline.examples.javabasic.override;

/**
 * Created by davidqi on 2/9/17.
 * 1. 重载 ：参数类型，参数个数；但是返回值不算；
 * 2. 重写（覆盖）：子类重写父类的方法。子类函数的访问修饰权限不能少于父类的；否则会改变父类的行为，通过多态调用时，
 * 父类生命引用无法调用子类函数。Liskov替换原则，子类对象可以被当成父类对象使用。如果可以降低子类的访问权限，那就违反了这一原则了。
 */
public class Child extends Parent {

    private int value = 1;

    // 正确，重写父类方法，可以扩大访问权限
    public int method1(int a, int b) {
        return 0;
    }

    // 错误，重写父类方法，不能降低了访问权限
    //private int method1(int a, int b) { return 0; }

    // 错误，重写父类方法，不能改变返回值类型
    //private long method1(int a, int b) { return 0; }

    // 正确，重载自身的方法，可以有不同的访问权限和返回值类型
    public short method1(int a, long b, int c) {
        return 0;
    }

    // 正确，重载自身的方法，可以有不同的访问权限和返回值类型
    private int method1(int a, long b) {
        return 0;
    }

    public static void main(String[] arges) {
        Parent parent = new Child();

        parent.method1(1, 1);

        /**
         * 重写／覆盖只发生在函数上面，并不发生在变量上面；
         * 此处仍然访问parent中的value而不是sub中的value;
         * 如果parent 中的value为private会有编译错误；
         */
        parent.value = 5;
    }
}