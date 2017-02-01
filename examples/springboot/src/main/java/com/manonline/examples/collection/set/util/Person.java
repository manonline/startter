package com.manonline.examples.collection.set.util;

/**
 * Created by davidqi on 2/1/17.
 */
public class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getAge() {
        return age;
    }

    public String toString() {
        return "Person:" + name + "::" + age;
    }

    // 定义一个比较方式，按照年龄进行自然排序
    public int compareTo(Object o) {
        Person p = (Person) o;
        int temp = this.age - p.age;
        return temp == 0 ? this.name.compareTo(p.name) : temp;
    }

    // 覆盖hashCode()方法
    public int hashCode() {
        // 为什么要定义常量，是因为避免哈希值冲突的情况，比如说一个姓名取哈希值为20，年龄取哈希值为40.
        // 另外一个人的姓名取哈希值为40年龄哈希值为20；两个的和都是60，用它进行哈希运算，可能得到的值都是相同的，
        // 这样的话再哈希结构表中的数据存储都是冲突的。所以才定义这样一个常量类避免这种冲突。
        final int NUMBER = 28;
        return name.hashCode() + age * NUMBER;
    }

    // 覆写equals方法其实就是建立对象自身特有的判断对象是否相同的依据。
    public boolean equals(Object obj) {
        if (!(obj instanceof Person))
            throw new ClassCastException("数据错误");

        Person p = (Person) obj;

        return (this.name.equals(p.name) && this.age == p.age);
    }
}