package com.manonline.examples.collection.set;

import com.manonline.examples.collection.set.util.Person;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by davidqi on 2/1/17.
 */
public class HashSetTest {
    public static void main(String args[]) {
        // Initiate a hashset
        Set set = new HashSet();
        set.add(new Person("lisi1", 20));
        set.add(new Person("zhangsan", 20));
        set.add(new Person("wangwu", 20));
        set.add(new Person("sunba", 20));
        set.add(new Person("zhangsan", 20));

        // Loop through the set and get the elements
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Person p = (Person) it.next();
            System.out.println(p.getName() + p.getAge());
        }
    }
}