package com.manonline.examples.generic;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用泛型时如果不指明参数类型，即泛型类没有参数化，会提示警告，此时类型为Object。
 * 当从集合中取出时，所有的元素都是Object类型，需要进行向下的强制类型转换，转换到特定的类型。
 * 泛型的思想就是由程序员指定类型，这样集合就只能容纳该类型的元素。
 */
public class BasicTest {

    public static void main(String args[]) {
        IfNoGeneric();
        WithGeneric();
    }

    public static void IfNoGeneric() {
        /**
         * NOTE : all the elements will be treated as Object after being put into List container;
         */
        List list = new ArrayList();
        list.add("qqyumidi");
        list.add("corn");
        list.add(100);

        for (int i = 0; i < list.size(); i++) {
            /**
             * NOTE : element 3 is an Object was the raw type of int, it could cause java.lang.ClassCastException error,
             * when trying to convert element 3 to String,
             * DURING RUN TIME (compiler is not able to determine this problem)
             */
            String name = (String) list.get(i);
            System.out.println("name:" + name);
        }
    }

    public static void WithGeneric() {
        /**
         * List list = new ArrayList();
         * list.add("qqyumidi");
         * list.add("corn");
         * list.add(100);
         */
        /**
         * Note : specify container type - by specifying the TYPE of PARAMETERS, therefore, actual arguments will
         * be tested before getting put into the container. Thereafter, see the next comments.
         */
        List<String> list = new ArrayList<String>();
        list.add("qqyumidi");
        list.add("corn");
        /**
         * Note : following statement will cause compiler error, since 1 won't pass the type test.
         */
        // list.add(1);

        for (int i = 0; i < list.size(); i++) {
            /**
             * Note : no need to try to convert the return value, since we know for sure the value is already tested.
             */
            String name = list.get(i);
            System.out.println("name:" + name);
        }
    }
}
