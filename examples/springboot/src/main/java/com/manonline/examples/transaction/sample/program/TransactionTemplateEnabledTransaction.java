package com.manonline.examples.transaction.sample.program;

import com.manonline.examples.jdbc.base.dao.UserDao;
import com.manonline.examples.jdbc.base.entity.User;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by davidqi on 2/15/17.
 */
public class TransactionTemplateEnabledTransaction {
    private UserDao userDao;
    private TransactionTemplate txTemplate;

    public boolean updateUserService(User user) {
        return (Boolean) txTemplate.execute(new TransactionCallback(){
            public Object doInTransaction(TransactionStatus status) {
                Object result;
                try {
                    result = userDao.update(user);
                } catch (Exception e) {
                    // 设置成RollBackOnly, Template会去执行rollback()
                    status.setRollbackOnly();
                    result = false;
                    System.out.println("Transfer Error!");
                }
                return result;
            }
        });
    }
}

//<bean id="TransactionTemplateEnabledTransaction" class="com.manonline.examples.transaction.sample.program.TransactionTemplateEnabledTransaction">
//    <property name="UserDao" ref="UserDaoImpl"/>
//    <property name="transactionTemplate" ref="transactionTemplate"/>
//</bean>