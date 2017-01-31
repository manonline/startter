package com.manonline.examples.collection.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by davidqi on 1/31/17.
 * fill —— 使用指定元素替换指定列表中的所有元素。
 * frequency —— 返回指定 collection 中等于指定对象的元素数。
 * indexOfSubList —— 返回指定源列表中第一次出现指定目标列表的起始位置，如果没有出现这样的列表，则返回 -1。
 * lastIndexOfSubList —— 返回指定源列表中最后一次出现指定目标列表的起始位置，如果没有出现这样的列表，则返回-1。
 * max —— 根据元素的自然顺序，返回给定 collection 的最大元素。
 * min —— 根据元素的自然顺序 返回给定 collection 的最小元素。
 * replaceAll —— 使用另一个值替换列表中出现的所有某一指定值。
 */
public class BasicUtil {
    public static void main(String[] args) {

        // Init a LinkedList
        List<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        for (Integer integer : list) {
            System.out.println(integer);
        }

        // Find the max element
        int max = Collections.max(list);
        System.out.println("Max Value is : " + max);

        // Replace all elements with a provided value
        Collections.fill(list, 6);
        System.out.println("After Replacement :");
        for (Integer integer : list) {
            System.out.println(integer);
        }

        // Get the occruance of a specific value
        int count = Collections.frequency(list, 6);
        System.out.println("Occurance of 6 is :" + count);
    }
}
