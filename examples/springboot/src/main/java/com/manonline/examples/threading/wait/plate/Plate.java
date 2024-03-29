package com.manonline.examples.threading.wait.plate;

/**
 *  生产者/消费者模式其实是一种很经典的线程同步模型，很多时候，并不是光保证多个线程对某共享资源操作的互斥性就够了，往往多个线程之间都是有
 *  协作的。假设有这样一种情况，有一个桌子，桌子上面有一个盘子，盘子里只能放一颗鸡蛋，A专门往盘子里放鸡蛋，如果盘子里有鸡蛋，则一直等到盘
 *  子里没鸡蛋，B专门从盘子里拿鸡蛋，如果盘子里没鸡蛋，则等待直到盘子里有鸡蛋。"""""其实盘子就是一个互斥区"""""，每次往盘子放鸡蛋应该都
 *  是互斥的，A的等待其实就是主动放弃锁，B 等待时还要提醒A放鸡蛋。
 *
 *  如何让线程主动释放锁。很简单，调用锁的wait()方法就好。wait方法是从Object来的，所以任意对象都有这个方法。如果一个线程获得了锁lock，
 *  进入了同步块，执行lock.wait()，那么这个线程会进入到lock的阻塞队列。如果调用lock.notify()则会通知阻塞队列的某个线程进入就绪队列。
 */
import java.util.ArrayList;
import java.util.List;

public class Plate {

    // 一个队列，其实就是互斥区
    List<Object> eggs = new ArrayList<Object>();

    // 从队列中去一个元素
    public synchronized Object getEgg() {
        while (eggs.size() == 0) {
            try {
                // 释放锁，并将本线程放入锁等待队列
                wait();
            } catch (InterruptedException e) {
            }
        }

        // 有元素可取
        Object egg = eggs.get(0);
        eggs.clear();
        System.out.println("拿到鸡蛋");

        // 唤醒阻塞队列的某线程到就绪队列
        notify();

        return egg;
    }

    // 向队列中放一个元素
    public synchronized void putEgg(Object egg) {
        while (eggs.size() > 0) {
            try {
                // 释放锁，并将本线程放入锁等待队列
                wait();
            } catch (InterruptedException e) {
            }
        }

        // 往盘子里放鸡蛋
        eggs.add(egg);
        System.out.println("放入鸡蛋");

        // 唤醒阻塞队列的某线程到就绪队列
        notify();
    }

    static class AddThread extends Thread {
        private Plate plate;
        private Object egg = new Object();

        // 构造一个操作Plate(互斥区)的线程对象
        public AddThread(Plate plate) {
            this.plate = plate;
        }

        public void run() {
            for (int i = 0; i < 5; i++) {
                plate.putEgg(egg);
            }
        }
    }

    static class GetThread extends Thread {
        private Plate plate;

        // 构造一个操作Plate(互斥区)的线程对象
        public GetThread(Plate plate) {
            this.plate = plate;
        }

        public void run() {
            for (int i = 0; i < 5; i++) {
                plate.getEgg();
            }
        }
    }

    public static void main(String args[]) {
        try {
            Plate plate = new Plate();
            Thread add = new Thread(new AddThread(plate));
            Thread get = new Thread(new GetThread(plate));
            add.start();
            get.start();
            // 等待add线程执行玩再继续执行
            add.join();
            // 等待get线程执行玩再继续执行
            get.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("测试结束");
    }
}