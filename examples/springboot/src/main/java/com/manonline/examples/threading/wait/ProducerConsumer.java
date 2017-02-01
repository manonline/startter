package com.manonline.examples.threading.wait;

/**
 * Created by davidqi on 2/2/17.
 * 这是个很重要的Thread例子。需要注意的是：
 * wait() 必须在synchronized 函数或者代码块里面
 * wait()会让已经获得synchronized 函数或者代码块控制权的Thread暂时休息，并且丧失控制权这个时候，由于该线程丧失控制权并且进入等待，
 * 其他线程就能取得控制权，并且在适当情况下调用notifyAll()来唤醒wait()的线程。
 * ------------------------------------
 * 需要注意的是，被唤醒的线程由于已经丧失了控制权，所以需要等待唤醒它的线程结束操作，从而才能重新获得控制权。
 * 所以wait()的确是马上让当前线程丧失控制权，其他的线程可以乘虚而入。
 * 所以wait()的使用，必须存在2个以上线程，而且必须在不同的条件下唤醒wait()中的线程。
 * -------------------------------------
 * 以下的例子：
 * ProductStack 是一个生产者跟消费者共享的同步机制，这个机制决定了什么情况生产者要wait()，什么情况消费者要wait()
 * 可以把ProductStack看作一个产品仓库。当产品仓库满的时候，生产者线程需要wait()，从而放弃对产品仓库的控制。
 * 这个时候消费者线程就可以进来了而取得仓库的控制权。一旦消费者消费了产品，那么仓库就不满了。
 * 这个时候消费者线程就要notifyAll()生产者线程，让等待的生产者线程唤醒。
 * 但是生产者被唤醒后不能马上进行生产，因为它在wait()的时候已经丧失了对仓库的控制权，所以就需要等待消费者线程结束操作，
 * 才能重新取得仓库的控制权，再进行生产。
 * <p>
 * 所以特别注意的是，notifyAll()并不是让当前线程马上让出控制权，而只是让其他wait()当中的线程唤醒而已，
 * 所以对不起，尽管我唤醒你，可你必须还是要等我用完仓库才能进来。这点必须清楚。
 * 相反，仓库如果空的时候，消费者线程就会wait()，然后等待生产者线程来生产产品，生产者进程乘虚而入后，让生产者线程生产产品
 * 并且唤醒消费者线程。这个情况跟上面就类似了。
 */
public class ProducerConsumer {
    public static void main(String[] args) {
        ProductStack ps = new ProductStack();
        Producer p = new Producer(ps, "生产者1");
        Consumer c = new Consumer(ps, "消费者1");
        new Thread(p).start();
        new Thread(c).start();
    }
}