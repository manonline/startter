package com.manonline.examples.collection.list;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by davidqi on 1/31/17.
 */
public class ArrayListTest {
    public static void main(String[] args) {
        // Create an ArrayList
        List<String> list = new ArrayList<String>();
        System.out.println("Initial Size :" + list.size());

        // Add elements
        list.add("zzz");
        list.add("egg");
        list.add("hell");
        list.add("child");
        System.out.println("Current Capacity :" + list.size());
        System.out.println("--------------");

        // Resize ArrayList size to the number of current elements
        ((ArrayList)list).trimToSize();
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

        // Clear the list
        list.clear();
        // Navigate through the list
        for (String string : list) {
            System.out.println(string);
        }
        System.out.println("--------------");
    }
}
