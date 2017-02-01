package com.manonline.examples.collection.misc;

import java.util.HashSet;

/**
 * Created by davidqi on 2/1/17.
 * -------------------------------------
 * ==       默认比较对象在JVM中的地址。
 * equal    默认比较对象在JVM中的地址，同==。
 * hashCode 默认返回对象在JVM中的存储地址。
 * 实际应用
 * ==       比较对象在JVM中的地址。
 * equal    比较对象的值，需要重写。
 * hashCode 如果equals相等，则hashCode需要相等，需重写。equals不相等，hashCode也可能相等，hash碰撞。
 *          换句话讲，hashcode相等，equals也不一定相等
 * -------------------------------------
 * 如果你的class永远不可能放在hash code为基础的容器内，不必劳神，您真的不必override hashCode().
 * Object类中定义了一个hashCode()方法来返回每个Java对象的哈希码，当从HashSet集合中查找某个对象时，
 * Java系统首先调用对象的hashCode()方法获得该对象的哈希码表，然后根据哈希吗找到相应的存储区域，
 * 最后取得该存储区域内的每个元素与该对象进行equals方法比较；
 * -------------------------------------
 */
public class EqualsTest {

    public static void main(String args[]) {
        HashSet<RectObject> set = new HashSet<RectObject>();
        RectObject r1 = new RectObject(3, 3);
        RectObject r2 = new RectObject(5, 5);
        RectObject r3 = new RectObject(3, 3);
        // no issue
        set.add(r1);
        // no issue
        set.add(r2);
        // hashcode same with r1, equals also the same, getting blocked.
        // if different hashcode returns, since equals
        // if equals return false - then this will be added in

        set.add(r3);
        set.add(r1);
        System.out.println("size:" + set.size());
    }

    static class RectObject {
        public int x;
        public int y;

        public RectObject(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final RectObject other = (RectObject) obj;
            if (x != other.x) {
                return false;
            }
            if (y != other.y) {
                return false;
            }
            return true;
        }
    }
}
