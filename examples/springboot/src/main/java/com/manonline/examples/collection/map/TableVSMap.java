package com.manonline.examples.collection.map;

/**
 * Created by davidqi on 2/1/17.
 */
public class TableVSMap {

}

// HashTable
//public class Hashtable extends Dictionary implements Map, Cloneable, Serializable {
//
//}

// HashMap
//public class HashMap extends AbstractMap implements Map, Cloneable, Serializable {
//
//}

// HashTable.put()
//public synchronized V put(K key, V value) { //###### 注意1
//    // Make sure the value is not null
//    if (value == null) { //###### 注意2
//        throw new NullPointerException();
//    }
//    // Makes sure the key is not already in the hashtable.
//    Entry tab[] = table;
//    int hash = key.hashCode(); //###### 注意3
//    int index = (hash & 0x7FFFFFFF) % tab.length;
//    for (Entry e = tab[index]; e != null; e = e.next) {
//        if ((e.hash == hash) && e.key.equals(key)) {
//            V old = e.value;
//            e.value = value;
//            return old;
//        }
//    }
//    modCount++;
//    if (count >= threshold) {
//        // Rehash the table if the threshold is exceeded
//        rehash();
//        tab = table;
//        index = (hash & 0x7FFFFFFF) % tab.length;
//    }
//    // Creates the new entry.
//    Entry e = tab[index];
//    tab[index] = new Entry(hash, key, value, e);
//    count++;
//    return null;
//}
// 注意1 方法是同步的
// 注意2 方法不允许(value == null)
// 注意3 方法调用了key的hashCode方法，如果key == null,会抛出空指针异常

// HashMap.put()
//public V put(K key, V value) { //###### 注意1
//    if (key == null)  //###### 注意2
//        return putForNullKey(value);
//    int hash = hash(key.hashCode());
//    int i = indexFor(hash, table.length);
//    for (Entry e = table[i]; e != null; e = e.next) {
//        Object k;
//        if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
//            V oldValue = e.value;
//            e.value = value;
//            e.recordAccess(this);
//            return oldValue;
//        }
//    }
//    modCount++;
//    addEntry(hash, key, value, i);  //###### 注意3
//    return null;
//}
//注意1 方法是非同步的
//注意2 方法允许key==null
//注意3 方法并没有对value进行任何调用，所以允许为null