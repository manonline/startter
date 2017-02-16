package com.manonline.examples.transaction.springcode;

/**
 * Created by davidqi on 2/15/17.
 */
public interface TransactionDefinition {
    // 返回事务的传播行为
    int getPropagationBehavior();

    // 返回事务的隔离级别，事务管理器根据它来控制另外一个事务可以看到本事务内的哪些数据
    int getIsolationLevel();

    // 返回事务必须在多少秒内完成
    int getTimeout();

    // 事务是否只读，事务管理器（数据库）能够根据这个返回值尝试进行优化，确保事务是只读的
    boolean isReadOnly();
}