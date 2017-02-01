package com.manonline.examples.collection.set;

import com.manonline.examples.collection.set.util.Person;

import java.util.*;

/**
 * Created by davidqi on 2/1/17.
 */
public class TreeSetTest {
    public static void main(String args[]) {
        // Initiate a treeset;
        Set set = new TreeSet(new ComparatorByName());
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

    /**
     * Note : every new element has to be tested / compared against the existing ones
     * to determine its position, therefore a comparator is needed;
     * Otherwise, following elements natural ordering
     */
    static class ComparatorByName implements Comparator {
        public int compare(Object o1, Object o2) {
            Person p1 = (Person) o1;
            Person p2 = (Person) o2;
            int temp = p1.getName().compareTo(p2.getName());
            return temp == 0 ? p1.getAge() - p2.getAge() : temp;
        }
    }

}
