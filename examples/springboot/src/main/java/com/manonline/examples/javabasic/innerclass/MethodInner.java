package com.manonline.examples.javabasic.innerclass;

/**
 * Created by davidqi on 2/9/17.
 */
public class MethodInner {
    private int age = 12;

    /**
     * 在方法中定义的内部类只能访问方法中final类型的局部变量，这是因为在方法中定义的局部变量相当于一个常量，它的生命周期超出方法运行的
     * 生命周期，由于局部变量被设置为final，所以不能再内部类中改变局部变量的值。
     */
    public void outPrint(final int x) {    //这里局部变量x必须设置为final类型！
        class Inner {
            public void inPrint() {
                System.out.println(x);
                System.out.println(age);
            }
        }
        new Inner().inPrint();
    }

    public static void main(String[] args) {
        MethodInner out = new MethodInner();
        out.outPrint(10);
    }
}
