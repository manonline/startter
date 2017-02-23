package com.manonline.examples.datastructure.sort;

/**
 * Created by davidqi on 2/23/17.
 * 归并排序原理：先拆分，再合并，归并排序首先将序列进行拆分直到每个序列不能拆分为止，然后对序列进行两两合并(合并的过程需要排序),
 * 直到最终只存在一个序列为止
 *
 * 归并排序效率分析：归并排序运行的时间复杂度为O(nlogn) 空间代价为O(n)最好情况和最差情况下时间复杂度均为O(nlogn),归并排序是一种
 * 稳定的外部排序算法归并排序C 代码递归方式实现(当然可以对其采用迭代方式实现)
 *
 * 好处
 * 1. You can modify it in order to reduce the memory footprint, in a way that you don’t create new arrays but
 * you directly modify the input array. Note: this kind of algorithms is called in-place.
 *
 * 2. You can modify it in order to use disk space and a small amount of memory at the same time without a huge
 * disk I/O penalty. The idea is to load in memory only the parts that are currently processed. This is important
 * when you need to sort a multi-gigabyte table with only a memory buffer of 100 megabytes.
 * Note: this kind of algorithms is called external sorting.
 *
 * 4. You can modify it to run on multiple processes/threads/servers. For example, the distributed merge sort is
 * one of the key components of Hadoop (which is THE framework in Big Data).
 * ==========
 * 内排序：指在排序期间数据对象全部存放在内存的排序。
 * 外排序：指在排序期间全部对象太多，不能同时存放在内存中，必须根据排序过程的要求，不断在内，外存间移动的排序。
 * ==========
 * 稳定的排序方法指的是，对于两个关键字相等的记录，它们在序列中的相对位置，在排序之前和经过排序之后，没有改变。
 */
public class MergeSort {

    public static void main(String[] args) {
        // 待排序数组
        Integer[] intgArr = {5, 9, 1, 4, 2, 6, 3, 8, 0, 7};

        MergeSort insertSort = new MergeSort();

        insertSort.partition(intgArr, 0, intgArr.length - 1);

        for (Integer a : intgArr) {
            System.out.print(a + " ");
        }
    }

    /**
     * 递归算法来拆分目标数组，直到不能再拆分，然后返回排序结果
     */
    public void partition(Integer[] arr, int from, int end) {
        // 划分到数组只有一个元素时才不进行再划分
        if (from < end) {
            // 从中间划分成两个数组
            int mid = (from + end) / 2;
            partition(arr, from, mid);
            partition(arr, mid + 1, end);
            // 合并划分后的两个数组
            merge(arr, from, end, mid);
        }
    }

    /**
     * 数组合并，合并过程中对两部分数组进行排序
     * 前后两部分数组里是有序的
     */
    public void merge(Integer[] arr, int from, int end, int mid) {
        Integer[] tmpArr = new Integer[10];
        // 指向临时数组
        int tmpArrIndex = 0;
        // 指向第一部分数组
        int part1ArrIndex = from;
        // 指向第二部分数组
        int part2ArrIndex = mid + 1;

        // 由于两部分数组里是有序的，所以每部分可以从第一个元素依次取到最后一个元素，再对两部分。取出的元素进行比较。
        // 只要某部分数组元素取完后，退出循环
        while ((part1ArrIndex <= mid) && (part2ArrIndex <= end)) {
            // 从两部分数组里各取一个进行比较，取最小一个并放入临时数组中
            if (arr[part1ArrIndex] - arr[part2ArrIndex] < 0) {
                // 如果第一部分数组元素小，则将第一部分数组元素放入临时数组中，并且临时数组指针
                // tmpArrIndex下移一个以做好下次存储位置准备，前部分数组指针part1ArrIndex
                // 也要下移一个以便下次取出下一个元素与后部分数组元素比较
                tmpArr[tmpArrIndex++] = arr[part1ArrIndex++];
            } else {
                // 如果第二部分数组元素小，则将第二部分数组元素放入临时数组中
                tmpArr[tmpArrIndex++] = arr[part2ArrIndex++];
            }
        }
        //由于退出循环后，两部分数组中可能有一个数组元素还未处理完，所以需要额外的处理，当然不可
        //能两部分数组都有未处理完的元素，所以下面两个循环最多只有一个会执行，并且都是大于已放入
        //临时数组中的元素
        while (part1ArrIndex <= mid) {
            tmpArr[tmpArrIndex++] = arr[part1ArrIndex++];
        }
        while (part2ArrIndex <= end) {
            tmpArr[tmpArrIndex++] = arr[part2ArrIndex++];
        }

        //最后把临时数组拷贝到源数组相同的位置
        System.arraycopy(tmpArr, 0, arr, from, end - from + 1);
    }
}