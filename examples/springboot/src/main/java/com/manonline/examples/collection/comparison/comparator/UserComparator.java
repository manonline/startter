package com.manonline.examples.collection.comparison.comparator;

import java.util.Comparator;

/**
 * Created by davidqi on 2/1/17.
 */
class UserComparator implements Comparator<User> {

    @Override
    public int compare(User user1, User user2) {
        int age1 = user1.getAge();
        int age2 = user2.getAge();
        if (age1 < age2) {
            return 1;
        }
        return 0;
    }
}
