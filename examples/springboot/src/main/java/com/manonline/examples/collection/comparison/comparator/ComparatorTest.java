package com.manonline.examples.collection.comparison.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by davidqi on 2/1/17.
 */
public class ComparatorTest {

    public static void main(String[] args) {
        // init a list
        List<com.manonline.examples.collection.comparison.comparator.User> users = new ArrayList<User>();
        users.add(new User("egg", 21));
        users.add(new User("niu", 22));
        users.add(new User("gg", 29));
        // get userComparator
        UserComparator comparator = new UserComparator();
        // comparator to determine how to sort
        Collections.sort(users, comparator);

        for (User user : users) {
            System.out.println(user.getUsername() + " " + user.getAge());
        }
    }

}

