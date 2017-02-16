package com.manonline.examples.transaction.springcode;

import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;

/**
 * Created by davidqi on 2/15/17.
 */
public interface PlatformTransactionManager {

    // 由TransactionDefinition得到TransactionStatus对象
    TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException;

    // 提交
    void commit(TransactionStatus status) throws TransactionException;

    // 回滚
    void rollback(TransactionStatus status) throws TransactionException;
}

/**

 JDBC Transaction
 <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="dataSource" />
 </bean>

 Hibernate Transaction
 <bean id="transactionManager" class="org.springframework.orm.hibernate3.HibernateTransactionManager">
    <property name="sessionFactory" ref="sessionFactory" />
 </bean>

 JPA Transaction
 <bean id="transactionManager" class="org.springframework.orm.jpa.JpaTransactionManager">
    <property name="sessionFactory" ref="sessionFactory" />
 </bean>

 JTA Transaction
 <bean id="transactionManager" class="org.springframework.transaction.jta.JtaTransactionManager">
    <property name="transactionManagerName" value="java:/TransactionManager" />
 </bean>
*/