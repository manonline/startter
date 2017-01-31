package com.manonline.examples.generic.containertest;

/**
 * Created by davidqi on 1/31/17.
 */
public class WildCardOperator {
    public static void main(String[] args) {

        Container<String> name = new Container<String>("corn");
        Container<Integer> age = new Container<Integer>(712);
        Container<Number> number = new Container<Number>(314);

        getData(name);
        getData(age);
        getData(number);

        //getUpperNumberData(name); // 1
        getUpperNumberData(age);    // 2
        getUpperNumberData(number); // 3
    }

    public static void getData(Container<?> data) {
        System.out.println("data :" + data.getData());
    }

    public static void getUpperNumberData(Container<? extends Number> data) {
        System.out.println("data :" + data.getData());
    }
}