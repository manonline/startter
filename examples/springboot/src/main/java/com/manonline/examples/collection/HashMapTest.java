package com.manonline.examples.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by davidqi on 1/31/17.
 */
public class HashMapTest {

    public static void main(String[] args) {
        // Initialize a map
        Map<String, Integer> map = new HashMap<String, Integer>();

        System.out.println("HashMap Elements :" + map.size());
        System.out.println("HashMap is Empty :" + (map.isEmpty() ? "Yes" : "No"));

        // Add elements to map
        map.put("element 1", 1);
        map.put("element 2", 2);
        map.put("element 3", 3);

        System.out.println("HashMap Elements :" + map.size());
        System.out.println("HashMap is Empty :" + (map.isEmpty() ? "Yes" : "No"));

        // Navigate through the map
        Set<String> set = map.keySet();
        for (String s : set) {
            System.out.println("Key :" + s + "; Value : " + map.get(s) + "; Hashcode : " + s.hashCode());
        }

        // Contains Specific Key
        System.out.println(map.containsKey("element 1"));

        // Contains Specific Value
        System.out.println(map.containsValue(2));

        // Hashcode
        System.out.println(map.hashCode());
    }
}