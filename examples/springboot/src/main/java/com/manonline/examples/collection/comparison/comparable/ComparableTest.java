package com.manonline.examples.collection.comparison.comparable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by davidqi on 2/1/17.
 */
@SuppressWarnings("unchecked")
public class ComparableTest {
    public static void main(String[] args) {
        List<User> users = new ArrayList<User>();
        users.add(new User("egg", 23));
        users.add(new User("niu", 22));
        users.add(new User("qing", 28));

        /**
         * Note : the target object (the object being compared) must implement Comparable interface, so that
         * sort function knows how to compare and sort;
         */
        Collections.sort(users);
        for (User user : users) {
            System.out.println(user.getName() + " " + user.getAge());
        }
    }
}