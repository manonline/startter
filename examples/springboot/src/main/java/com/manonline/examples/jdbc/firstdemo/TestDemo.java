package com.manonline.examples.jdbc.firstdemo;

import com.manonline.examples.jdbc.firstdemo.User;
import com.manonline.examples.jdbc.firstdemo.UserService;

import java.sql.Date;

/**
 * Created by davidqi on 2/11/17.
 * http://blog.csdn.net/jiangwei0910410003/article/details/26164629
 */
public class TestDemo {

    public static void main(String[] args) throws Exception {
        UserService userService = new UserService();
        System.out.println("添加用户:");
        userService.regist(new User(1, "jiangwei", new Date(System.currentTimeMillis()), 300));
    }
}