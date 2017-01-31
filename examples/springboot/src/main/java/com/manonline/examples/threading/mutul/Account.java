package com.manonline.examples.threading.mutul;

/**
 * public synchronized void add(int num) 锁就是这个方法所在的对象。 public static
 * synchronized void add(int num)，那么锁就是这个方法所在的class。
 * 理论上，每个对象都可以做为锁，但一个对象做为锁时，应该被多个线程共享，这样才显得有意义， 在并发环境下，一个没有共享的对象作为锁是没有意义的。
 * 
 * 每个锁对象都有两个队列，一个是就绪队列，一个是阻塞队列，就绪队列存储了将要获得锁的线程，阻塞队列存储了被阻塞的线程，当一个被线程被唤醒
 * (notify)后，才会进入到就绪队列，等待cpu的调度。
 * 
 * 当一开始线程a第一次执行account.add方法时，jvm会检查锁对象account的就绪队列是否已经有线程在等待，如果有则表明account的锁已经被占用了，
 * 由于是第一次运行，account的就绪队列为空，所以线程a获得了锁，执行account.add方法。如果恰好在这个时候，线程b要执行account.withdraw方法，
 * 因为线程a已经获得了锁还没有释放，所以线程b要进入account的就绪队列，等到得到锁后才可以执行。 一个线程执行临界区代码过程如下： 
 * 1 获得同步锁 
 * 2 清空工作内存 
 * 3 从主存拷贝变量副本到工作内存
 * 4 对这些变量计算
 * 5 将变量从工作内存写回到主存
 * 6 释放锁
 * 可见，synchronized既保证了多线程的并发有序性，又保证了多线程的内存可见性。
 * 
 */
public class Account {

    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public synchronized void add(int num) {
        balance = balance + num;
    }

    public synchronized void withdraw(int num) {
        balance = balance - num;
    }

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(1000);
        Thread a = new Thread(new AddThread(account, 20), "add");
        Thread b = new Thread(new WithdrawThread(account, 20), "withdraw");
        a.start();
        b.start();
        a.join();
        b.join();
        System.out.println(account.getBalance());
    }

    static class AddThread implements Runnable {

        Account account;
        int amount;

        public AddThread(Account account, int amount) {
            this.account = account;
            this.amount = amount;
        }

        public void run() {
            for (int i = 0; i < 200000; i++) {
                account.add(amount);
            }
        }
    }

    static class WithdrawThread implements Runnable {

        Account account;
        int amount;

        public WithdrawThread(Account account, int amount) {
            this.account = account;
            this.amount = amount;
        }

        public void run() {
            for (int i = 0; i < 100000; i++) {
                account.withdraw(amount);
            }
        }
    }
}