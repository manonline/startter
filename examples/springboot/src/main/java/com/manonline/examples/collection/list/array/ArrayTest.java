package com.manonline.examples.collection.list.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by davidqi on 2/1/17.
 */
public class ArrayTest {
    public static void main(String[] args) {
        listToArrayViaLoop();
        listToArrayViaToArray();
        arrayToListViaLoop();
        arrayToListViaArrays();
        arrayToListViaArrays2();
    }

    public static void listToArrayViaLoop() {
        // create a list
        List list = new ArrayList();
        list.add("王利虎");
        list.add("张三");
        list.add("李四");
        int size = list.size();

        // create an array
        String[] array = new String[size];

        // convert the list to array via loop through
        for (int i = 0; i < list.size(); i++) {
            // convert each element, since no generic is used, i.e. Objects
            array[i] = (String) list.get(i);
        }

        // loop through the array and print out
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    public static void arrayToListViaLoop() {
        // create an array
        String[] array = new String[3];
        array[0] = "王利虎";
        array[1] = "张三";
        array[2] = "李四";

        // create a list
        List<String> list = new ArrayList<String>();

        // loop through array and put element into the list
        for (int i = 0; i < array.length; i++) {
            list.add(array[i]);
        }

        // loop through list and print out
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static void listToArrayViaToArray() {
        // create a list
        List<String> list = new ArrayList<String>();
        list.add("王利虎");
        list.add("张三");
        list.add("李四");
        int size = list.size();

        // declare an array
        String[] array;

        // convert list to array
        /**
         * @param a the array into which the elements of this list are to
         *          be stored, if it is big enough; otherwise, a new array of the
         *          same runtime type is allocated for this purpose.
         * @return an array containing the elements of this list
         */
        array = (String[]) list.toArray(new String[size]);

        // loop through the array and print out
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    public static void arrayToListViaArrays() {
        // create an array
        String[] array = new String[3];
        array[0] = "王利虎";
        array[1] = "张三";
        array[2] = "李四";

        // declare a list
        List<String> list;

        // convert array to list
        list = Arrays.asList(array);

        // loop through list and print out
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

    public static void arrayToListViaArrays2() {
        // declare a list
        List<String> list;

        // convert array to list
        list = Arrays.asList("王利虎", "张三", "李四");

        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}
