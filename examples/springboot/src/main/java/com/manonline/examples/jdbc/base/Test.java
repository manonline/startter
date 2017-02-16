package com.manonline.examples.jdbc.base;

import com.manonline.examples.jdbc.base.entity.User;
import com.manonline.examples.jdbc.base.service.UserService;

import java.sql.Date;

/**
 * Created by davidqi on 2/11/17.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        UserService userService = new UserService();
        System.out.println("添加用户:");
        userService.serviceRegist(new User(1, "jiangwei", new Date(System.currentTimeMillis()), 300));
    }
}