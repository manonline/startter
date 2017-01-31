package com.manonline.examples.collection.comparison.comparator;

/**
 * Created by davidqi on 2/1/17.
 */
class User {
    private String username;
    private int age;

    public User(String username, int age) {
        super();
        this.username = username;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
