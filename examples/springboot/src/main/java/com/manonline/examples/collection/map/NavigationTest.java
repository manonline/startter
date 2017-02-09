package com.manonline.examples.collection.map;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by davidqi on 2/9/17.
 */
public class NavigationTest {

    public static void main(String args[]) {

        /**
         * 这里的遍历指的是取出所有的值,用EntrySet()去遍历效率会高很多。因为：
         * EntrySet().Iterator -> 每次拿到一个Entry -> 直接取出Entry里面的值(或者Key)
         * KeySet().Iteractor -> 每次拿到一个Key -> """"需要再用Key，去遍历Map""""" 才能取出Value
         */

        // Using EntrySet : SUGGESTED
        Map map1 = new HashMap();
        Iterator iter1 = map1.entrySet().iterator();
        while (iter1.hasNext()) {
            // get specific entry
            Map.Entry entry = (Map.Entry) iter1.next();
            // get the key
            Object key = entry.getKey();
            // get the value
            Object val = entry.getValue();
        }
        // Using KeySet : SLOW!!!!
        Map map2 = new HashMap();
        Iterator iter2 = map2.keySet().iterator();
        while (iter2.hasNext()) {
            // get specific key
            Object key = iter2.next();
            // get the value according to key
            Object val = map2.get(key);
        }
    }

}
