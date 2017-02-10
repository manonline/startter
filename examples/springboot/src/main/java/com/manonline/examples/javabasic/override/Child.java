package com.manonline.examples.javabasic.override;

/**
 * Created by davidqi on 2/9/17.
 * 1. 重载 ：参数类型，参数个数；但是返回值不算；
 * 2. 重写（覆盖）：子类重写父类的方法。子类函数的访问修饰权限不能少于父类的；否则会改变父类的行为，通过多态调用时，
 * 父类生命引用无法调用子类函数。Liskov替换原则，子类对象可以被当成父类对象使用。如果可以降低子类的访问权限，那就违反了这一原则了。
 */
public class Child extends Parent {

    public String publicValue = "Child Public Value";
    public final String publicFinalValue = "Child Public Final Value";
    public static String publicStaticValue = "Child Public Static Value";
    public static final String publicStaticFinalValue = "Child Public Static Final Value";

    private String privateValue = "Child Public Value";
    private final String privateFinalValue = "Child Final Value";
    private static String privateStaticValue = "Child Static Value";
    private static final String privateStaticFinalValue = "Child Static Final Value";

    public String publicMethod() {
        return "Child Public Method ...";
    }

    /**
     * Final Method cannot be override
     */
//    public final String publicFinalMethod() {
//        return "Child Public Final Method ...";
//    }
    public static String publicStaticMethod() {
        return "Child Public Static Method ...";
    }
    /**
     * Final Method cannot be override
     */
//    public static final String publicStaticFinalMethod() {
//        return "Child Public Static Final Method ...";
//    }

    private String privateMethod() {
        return "Child Private Method ...";
    }
    private final String privateFinalMethod() {
        return "Child Private Final Method ...";
    }
    private static String privateStaticMethod() {
        return "Child Private Static Method ...";
    }
    private static final String privateStaticFinalMethod() {
        return "Child Private Static Final Method ...";
    }

    // expose the private method
    public String method1() {
        return privateMethod();
    }
    public final String method2() {
        return privateFinalMethod();
    }
    public static String method3() {
        return privateStaticMethod();
    }
    public static final String method4() {
        return privateStaticFinalMethod();
    }

    private int value = 1;

    // 正确，重写父类方法，可以扩大访问权限
    public String protectedMethod(int m, int n) {
        return "Child Protected Method ...";
    }

    // 错误，重写父类方法，不能降低了访问权限
    //private String protectedMethod(int m, int n) { return "Child Protected Method ..."; }

    // 错误，重写父类方法，不能改变返回值类型
    //private long protectedMethod(int m, int n) { return 1L; }

    // 正确，重载自身的方法，可以有不同的访问权限和返回值类型
    public String protectedMethod(int a, long b) {
        return "Child Protected Method ...";
    }

    // 正确，重载自身的方法，可以有不同的访问权限和返回值类型
    public int protectedMethod(int a, long b, int c) {
        return 0;
    }
}