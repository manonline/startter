package com.manonline.examples.transaction.springcode;

/**
 * Created by davidqi on 2/15/17.
 */
public interface TransactionStatus {
    // 是否是新的事物
    boolean isNewTransaction();

    // 是否有恢复点
    boolean hasSavepoint();

    // 设置为只回滚
    void setRollbackOnly();

    // 是否为只回滚
    boolean isRollbackOnly();

    // 是否已完成
    boolean isCompleted();
}