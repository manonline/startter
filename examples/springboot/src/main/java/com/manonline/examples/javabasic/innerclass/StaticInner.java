package com.manonline.examples.javabasic.innerclass;

/**
 * Created by davidqi on 2/9/17.
 */
public class StaticInner {
    private static int age = 12;

    static class Inner {
        public void print() {
            System.out.println(age);
        }
    }
    public static void main(String[] args) {
        StaticInner.Inner in = new StaticInner.Inner();
        in.print();
    }
}