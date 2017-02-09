package com.manonline.examples.javabasic.innerclass;

/**
 * Created by davidqi on 2/9/17.
 */
public class FieldInner {
    private int age = 12;

    // inner class as a field;

    /**
     * 内部类访问控制 ： private访问权限、protected访问权限、public访问权限及包访问权限。
     * 外部累访问控制 ： 外部类只能被public和包访问两种权限修饰。
     */
    class Inner {
        private int age = 13;
        public void print() {
            int age = 14;
            System.out.println("局部变量：" + age);
            System.out.println("内部类变量：" + this.age);
            System.out.println("外部类变量：" + FieldInner.this.age);
        }
    }

    public static void main(String[] args) {
        FieldInner out = new FieldInner();
        // create an inner class instance via outer object;
        FieldInner.Inner in = out.new Inner();
        in.print();
    }
}
