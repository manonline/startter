package com.manonline.examples.jdbc.firstdemo;

import com.manonline.examples.jdbc.firstdemo.User;

/**
 * Created by davidqi on 2/11/17.
 */
public interface UserDao {

    //添加用户
    public void addUser(User user);
    //通过userid查询用户,id是唯一的,所以返回的是一个user
    public User getUserById(int userId);
    //更新用户信息
    public int update(User user);
    //删除用户信息
    public int delete(User user);

}