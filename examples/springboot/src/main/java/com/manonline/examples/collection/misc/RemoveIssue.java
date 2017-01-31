package com.manonline.examples.collection.misc;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by davidqi on 2/1/17.
 */
public class RemoveIssue {
    public static void main(String[] args) {
        removeIssue();
        removeOK();
    }

    private static void removeIssue() {
        List<String> list = new LinkedList<String>();
        list.add("A");
        list.add("B");
        list.add("C");

        for (int i = 0; i < list.size(); i++) {
            // remove here will change list.size() that is used to determine the loop;
            list.remove(i);
        }

        for (String item : list) {
            System.out.println(item);
        }
    }

    private static void removeOK() {
        List<String> list = new LinkedList<String>();
        list.add("A");
        list.add("B");
        list.add("C");

        for (int i = 0; i < list.size(); i++) {
            // size reduce by 1
            list.remove(i);
            // i also need to reduce by 1
            i -= 1;
        }

        for (String item : list) {
            System.out.println(item);
        }

    }
}