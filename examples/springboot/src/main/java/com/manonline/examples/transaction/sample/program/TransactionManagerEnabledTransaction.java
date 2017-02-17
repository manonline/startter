package com.manonline.examples.transaction.sample.program;

import com.manonline.examples.jdbc.base.dao.UserDao;
import com.manonline.examples.jdbc.base.entity.User;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;


/**
 * Created by davidqi on 2/15/17.
 * https://www.ibm.com/developerworks/cn/education/opensource/os-cn-spring-trans/
 */
public class TransactionManagerEnabledTransaction {
    private UserDao userDao;
    private PlatformTransactionManager txManager;
    private TransactionDefinition txDefinition;

    private TransactionStatus txStatus;

    public boolean addUserService(User user) {
        // 通过TransactionManager开启事务
        txStatus = txManager.getTransaction(txDefinition);
        boolean result = false;
        try {
            userDao.update(user);
            // 提交事务
            txManager.commit(txStatus);
            result = true;
        } catch (Exception ex) {
            result = false;
            txManager.rollback(txStatus);
            System.out.println("Transaction Error!");
        } finally {
            // 用closeConnection吗？
        }
        return result;
    }
}

//<bean id="TransactionManagerEnabledTransaction" class="com.manonline.examples.transaction.sample.program.TransactionManagerEnabledTransaction">
//    <property name="userDao" ref="userDaoImpl"/>
//    <property name="txManager" ref="transactionManager"/>
//    <property name="txDefinition">
//        <bean class="org.springframework.transaction.support.DefaultTransactionDefinition">
//            <property name="propagationBehaviorName" value="PROPAGATION_REQUIRED"/>
//        </bean>
//    </property>
//</bean>