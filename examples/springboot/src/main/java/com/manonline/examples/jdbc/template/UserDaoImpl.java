package com.manonline.examples.jdbc.template;

import com.manonline.examples.jdbc.firstdemo.User;
import com.manonline.examples.jdbc.strategy.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by davidqi on 2/11/17.
 */
public class UserDaoImpl extends AbstractDao {

    /**
     * 更新用户信息
     */
    public int update(User user) {
        String sql = "udpate user set name=?,birthday=?,money=?,where id=?";
        Object[] args = new Object[]{user.getName(), user.getBirthday(), user.getMoney(), user.getId()};
        return super.update(sql, args);//相同的代码调用父类的方法即可
    }

    /**
     * 删除用户
     *
     * @param user
     */
    public void delete(User user) {
        String sql = "delete from user where id=?";
        Object[] args = new Object[]{user.getId()};
        super.update(sql, args);
    }

    /**
     * 查找用户
     *
     * @param loginName
     * @return
     */
    public User findUser(String loginName) {
        String sql = "select id,name,money,birthday from user where name=?";
        Object[] args = new Object[]{loginName};
        return (User) super.find(sql, args);
    }

    @Override
    protected Object rowMapper(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setMoney(rs.getFloat("money"));
        user.setBirthday(rs.getDate("birthday"));
        return user;
    }

    /**
     * 查询名称
     *
     * @param id
     * @return
     */
    public String findUserName(int id) {
        String sql = "select name from user where id=?";
        Object[] args = new Object[]{id};
        Object user = super.find(sql, args, new RowMapper() {
            public Object mapRow(ResultSet rs) throws SQLException {
                return rs.getObject("name");
            }
        });
        return ((User) user).getName();
    }

//    /**
//     * 采用策略模式：传递不同的行为：C++中可以使用函数指针来实现，Java中可以使用接口的回调来实现
//     *
//     * @param loginName
//     * @return
//     */
//    public User findUser(String loginName) {
//        String sql = "select id,name,money,birthday from user where name=?";
//        Object[] args = new Object[]{loginName};
//        return (User) super.find(sql, args, new RowMapper() {
//
//            public Object mapRow(ResultSet rs) throws SQLException {
//                User user = new User();
//                user.setId(rs.getInt("id"));
//                user.setName(rs.getString("name"));
//                user.setMoney(rs.getFloat("money"));
//                user.setBirthday(rs.getDate("birthday"));
//                return user;
//            }
//
//        });
//    }

    //如果insert的时候不需要获取主键的话，也可以使用super.update方法实现的，这样代码就显得很整洁，相同的代码只需要一份即可(放在父类中)
    //不同的地方放到子类来实现
    //首先要区分哪些是变动的部分，哪些是不变的部分即可

}