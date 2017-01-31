package com.manonline.examples.collection.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by davidqi on 2/1/17.
 * reverse —— 对List中的元素进行转置
 * shuffle —— 对List中的元素随即排列
 * sort —— 对List中的元素排序
 * swap —— 交换List中某两个指定下标位元素在集合中的位置
 * rotate —— 循环移动
 */
public class SortUtil {
    public static void main(String[] args) {
        sortDemo();
        rotateDemo();
    }

    public static void sortDemo() {
        // Init a LinkedList
        List<Integer> list = new ArrayList<Integer>();
        list.add(5);
        list.add(2);
        list.add(1);
        list.add(9);
        list.add(0);
        System.out.println("Before Sorting :");
        for (Integer integer : list) {
            System.out.print(integer + " ");
        }
        System.out.println("-----------");

        // Sort ....
        Collections.sort(list);

        System.out.println("After Soring :");
        for (Integer integer : list) {
            System.out.print(integer + " ");
        }
    }

    private static void rotateDemo() {
        // Init a list
        List<Integer> list = new ArrayList<Integer>();
        list.add(5);
        list.add(2);
        list.add(1);
        list.add(9);
        list.add(0);
        System.out.println("Before Rotate :");
        for (Integer integer : list) {
            System.out.print(integer + " ");
        }
        System.out.println("--------");

        // Rotate based on the distance specified
        Collections.rotate(list, -1);
        System.out.println("After Rotate :");
        for (Integer integer : list) {
            System.out.print(integer + " ");
        }
    }
}
