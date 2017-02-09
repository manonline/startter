package com.manonline.examples.generic.containertest;

/**
 * Created by davidqi on 1/31/17.
 */
public class TypeInheritTest {
    public static void main(String[] args) {
        typeInheritTest();
        typeInheritReasonTest();
    }
    public static void typeInheritTest() {
        Container<Number> age1 = new Container<Number>(99);
        Container<Integer> age2 = new Container<Integer>(712);

        getData(age1);
        /**
         * Note : The method getData(Box<Number>) in the type GenericTest is not applicable
         * for the arguments (Box<Integer>); Class Hirechary doesn't lead to parent/child relationship
         * in Generic. See the next example to figure out reasons.
         */
        // getData(age2);
    }

    public static void typeInheritReasonTest() {
        Container<Integer> a = new Container<Integer>(712);
        /**
         * 假设Box<Number>在逻辑上可以视为Box<Integer>的父类，那么//1和//2处将不会有错误提示了，那么问题就出来了，
         * 通过getData()方法取出数据时到底是什么类型呢？Integer? Float? 还是Number？且由于在编程过程中的顺序不可控性，
         * 导致在必要的时候必须要进行类型判断，且进行强制类型转换。显然，这与泛型的理念矛盾
         */
        // Container<Number> b = a;  // 1

        Container<Float> f = new Container<Float>(3.14f);
        // b.setData(f);        // 2
    }

    public static void getData(Container<Number> data){
        System.out.println("data :" + data.getData());
    }
}