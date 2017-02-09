package com.manonline.examples.collection.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by davidqi on 1/31/17.
 * HashMap 维护了一个数组 Table[]
 * Table[] 中的元素是 Node<K, V>
 * Node<K, V> 实现了Entry<K, V>, 并包含
 * K
 * V
 * Node<K, V> 指向下一个元素
 * ===============================================
 * <K, V>
 * K 的 hashCode决定该键值对在Table[]中的位置；
 * 如果此位置已有元素（两个Key的HashCode一样），则把新元素添加到已有元素的后面，也即next
 * ===============================================
 * capacity (16): 数组的大小
 * loadFactor (0.75): 数组填充到多少百分比需要扩容
 * resize : JDK 1.7, 要通过indexFor(hash, capacity)重新计算元素在新数组中的位置
 * ===============================================
 * modcount : 追踪HashMap上的结构变化，也就是元素的个数或者扩容引起的rehash。很像一个乐观锁，来记录
 * 变动的次数。使用Iteractor的时候会检查这个标记，来保证iterator开始和结束时modcount是一致，没有被其它
 * 线程，操作更改。如果更改则会报错。参见示例三。
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

    private static void ChangesInIteractor() {
        Map<Object, String> map = new java.util.HashMap<Object, String>();
        map.put(new Object(), "a");
        map.put(new Object(), "b");

        Iterator<Object> it = map.keySet().iterator();

        while (it.hasNext()) {
            it.next();
            /**
             * 因为在迭代过程中修改了HashMap内部的元素导致modCount自增。
             * 若将上面代码中 map.put(new Object(), "b") 这句注释掉，程序会顺利通过，
             * 因为此时HashMap中只包含一个元素，经过一次迭代后已到了尾部，所以不会出现问题，
             * 也就没有抛出异常的必要了。
             */
            map.put("", "");
            System.out.println(map.size());
        }
    }
}