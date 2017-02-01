package com.manonline.examples.threading.wait;

/**
 * Created by davidqi on 2/2/17.
 */
class ProductStack {
    int index = 0;
    Product[] arrProduct = new Product[6];

    public synchronized void push(Product product) {
        // 如果仓库满了
        // 这里本来可以用if(),但是如果catch exception会出问题，让满的index越界
        while (index == arrProduct.length) {
            try {
                // here, "this" means the thread that is using "push"
                // so in this case it's a producer thread instance.
                // the BIG difference between sleep() and wait() is, once
                // wait(),
                // the thread won't have the lock anymore
                // so when a producer wait() here, it will lost the lock of
                // "push()"
                // While sleep() is still keeping this lock
                // Important: wait() and notify() should be in "synchronized"
                // block
                System.out.println(product.getProducedBy() + " is waiting.");
                // 等待，并且从这里退出push()
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(product.getProducedBy() + " sent a notifyAll().");

        // 因为我们不确定有没有线程在wait()，所以我们既然生产了产品，就唤醒有在这个仓库（锁）上等待的消费者，让他们醒来，准备消费
        notifyAll();

        // 注意，notifyAll()以后，并没有退出，而是继续执行直到完成。
        arrProduct[index] = product;
        index++;
        System.out.println(product.getProducedBy() + " 生产了: " + product);
    }

    public synchronized Product pop(String consumerName) {
        // 如果仓库空了
        while (index == 0) {
            try {
                // here will be the consumer thread instance will be waiting ,
                // because empty
                System.out.println(consumerName + " is waiting.");
                // 等待，并且从这里退出pop()
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(consumerName + " sent a notifyAll().");
        // 因为我们不确定有没有线程在wait()，所以我们既然消费了产品，就唤醒有在这个仓库（锁）上等待的生产者，让他们醒来，准备生产
        notifyAll();
        // 注意，notifyAll()以后，并没有退出，而是继续执行直到完成。
        // 取出产品
        index--;
        Product product = arrProduct[index];
        product.consume(consumerName);
        System.out.println(product.getConsumedBy() + " 消费了: " + product);
        return product;
    }
}