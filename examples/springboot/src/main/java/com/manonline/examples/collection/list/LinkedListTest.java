package com.manonline.examples.collection.list;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by davidqi on 1/31/17.
 */
public class LinkedListTest {
    public static void main(String[] args) {
        // Create a LinkedList
        List<String> list = new LinkedList<String>();
        System.out.println("Initial Size :" + list.size());

        // Add elements
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.add("ddd");
        System.out.println("Current Capacity :" + list.size());
        System.out.println("--------------");

        // Navigate through the list
        for (String string : list) {
            System.out.println(string);
        }
        System.out.println("--------------");

        // Add elements to specific positions
        list.add(2, "bbb2");
        // Navigate through the list
        for (String string : list) {
            System.out.println(string);
        }
        System.out.println("--------------");

        // Get the first element
        System.out.println("The First Element is : " + ((LinkedList) list).getFirst());
        // Get the last element
        System.out.println("The Last Element is :" + ((LinkedList) list).getLast());
        System.out.println("--------------");

        // Clear the list
        list.clear();
        // Navigate through the list
        for (String string : list) {
            System.out.println(string);
        }
        System.out.println("--------------");
    }
}
