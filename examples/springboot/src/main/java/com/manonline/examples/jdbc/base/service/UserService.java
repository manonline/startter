package com.manonline.examples.jdbc.base.service;

import com.manonline.examples.jdbc.base.dao.UserDao;
import com.manonline.examples.jdbc.base.entity.User;
import com.manonline.examples.jdbc.base.utils.BeanFactory;

/**
 * Created by davidqi on 2/11/17.
 */
public class UserService {

    private UserDao userDao;

    public UserService() {
        // 通过工厂实例化UserDao对象
        userDao = (UserDao) BeanFactory.getInstance().getBean("UserDao");
        System.out.println("userDao:" + userDao);
    }

    /**
     * 注册用户
     */
    public void serviceRegist(User user) {
        if (user == null) {
            System.out.println("注册信息无效!!");
        } else {
            userDao.addUser(user);
        }
    }

    /**
     * 查询用户
     */
    public User serviceQuery(int userId) {
        User user = userDao.getUserById(userId);
        if (user == null) {
            System.out.println("查询结果为空!!");
        } else {
            System.out.println(user.getId() + "\t" + user.getName() + "\t" + user.getBirthday() + "\t" + user.getMoney());
        }
        return userDao.getUserById(userId);
    }

    /**
     * 更新用户
     *
     * @param user
     */
    public void serviceUpdate(User user) {
        if (user.getId() <= 0) {
            System.out.println("用户id无效,无法更新");
        } else {
            userDao.update(user);
        }
    }

    /**
     * 删除用户
     *
     * @param user
     */
    public void serviceDelete(User user) {
        if (user.getId() <= 0) {
            System.out.println("用户id无效,无法删除!!");
        } else {
            userDao.delete(user);
        }
    }

}