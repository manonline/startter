package com.manonline.examples.collection.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by davidqi on 1/31/17.
 * HashMap 维护了一个数组 Table[]
 * Table[] 中的元素是 Node<K, V>
 * Node<K, V> 实现了Entry<K, V>, 并包含
 *      K
 *      V
 *      Node<K, V> 指向下一个元素
 * ===============================================
 * <K, V>
 *     K 的 hashCode决定该键值对在Table[]中的位置；
 *     如果此位置已有元素（两个Key的HashCode一样），则把新元素添加到已有元素的后面，也即next
 * ===============================================
 * capacity : 数组的大小
 * loadFactor : 数组填充到多少百分比需要扩容
 * resize : JDK 1.7, 要通过indexFor(hash, capacity)重新计算元素在新数组中的位置
 * ===============================================
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