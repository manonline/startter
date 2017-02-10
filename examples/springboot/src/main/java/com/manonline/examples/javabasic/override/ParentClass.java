package com.manonline.examples.javabasic.override;

/**
 * Created by davidqi on 2/10/17.
 */
public class ParentClass {
    /**
     * 私有 方法在它所在类之外是不能被访问的。如果子类中碰巧定义了一个方法和父类中的私有方法具有完全相同名字、签名和返回值类型，那么这两个
     * 方法是完全没有任何 联系的——子类中的方法将不会覆盖父类中的方法。
     * 子类是不能覆盖掉父类中的私有方法的，也就意味着子类没有从父类继承私有方法，但是子类可以继承父类的非私有方法。
     *
     *
     * 当通过对象引用来调用一个方法的时候，那么对象的实际的类将决定使用哪一个实现（the actual class of the object governs which
     * implementation is used）；当访问一个字段的时候，引用的声明类型将会被使用（the declared type of the reference is used）。
     */
    private String msg() {
        return "I am an attribute in ParentClass.";
    }
    public String getMsg() {
        return msg();
    }
}