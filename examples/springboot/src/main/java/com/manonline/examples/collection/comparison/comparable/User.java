package com.manonline.examples.collection.comparison.comparable;

/**
 * Created by davidqi on 2/1/17.
 */
@SuppressWarnings("unchecked")
class User implements Comparable {
    private String name;
    private int age;

    public User(String name, int age) {
        super();
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public int compareTo(Object o) {
        return this.age - ((User) o).getAge();
    }
}
