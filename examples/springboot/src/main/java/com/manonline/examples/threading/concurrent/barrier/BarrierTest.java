package com.manonline.examples.threading.concurrent.barrier;

import java.awt.image.DataBuffer;
import java.util.concurrent.Exchanger;

/**
 * Created by davidqi on 2/2/17.
 *         栅栏（Bariier）类似于闭锁，它能阻塞一组线程知道某个事件发生。栅栏与闭锁的关键区别在于，所有的线程必须同时到达栅栏位置，才能继续执行。闭锁用于等待等待时间，而栅栏用于等待线程。
 CyclicBarrier 可以使一定数量的参与方反复的在栅栏位置汇聚，它在并行迭代算法中非常有用：将一个问题拆成一系列相互独立的子问题。当线程到达栅栏位置时，调用await() 方法，这个方法是阻塞方法，直到所有线程到达了栅栏位置，那么栅栏被打开，此时所有线程被释放，而栅栏将被重置以便下次使用。
 另一种形式的栅栏是Exchanger，它是一种两方（Two-Party）栅栏，各方在栅栏位置上交换数据。例如当一个线程想缓冲区写入数据，而另一个线程从缓冲区中读取数据。这些线程可以使用 Exchanger 来汇合，并将慢的缓冲区与空的缓冲区交换。当两个线程通过 Exchanger 交换对象时，这种交换就把这两个对象安全的发布给另一方。
 Exchanger 可能被视为 SynchronousQueue 的双向形式。我们也可以用两个SynchronousQueue来实现 Exchanger的功能。
 */

public class BarrierTest {

}

//class FillAndEmpty {
//    Exchanger<DataBuffer> exchanger = new Exchanger<DataBuffer>();
//    DataBuffer initialEmptyBuffer = ... a made-up type
//    DataBuffer initialFullBuffer = ...
//
//    class FillingLoop implements Runnable {
//        public void run() {
//            DataBuffer currentBuffer = initialEmptyBuffer;
//            try {
//                while (currentBuffer != null) {
//                    addToBuffer(currentBuffer);
//                    if (currentBuffer.isFull())
//                        currentBuffer = exchanger.exchange(currentBuffer);
//                }
//            } catch (InterruptedException ex) { ... handle ... }
//        }
//    }
//
//    class EmptyingLoop implements Runnable {
//        public void run() {
//            DataBuffer currentBuffer = initialFullBuffer;
//            try {
//                while (currentBuffer != null) {
//                    takeFromBuffer(currentBuffer);
//                    if (currentBuffer.isEmpty())
//                        currentBuffer = exchanger.exchange(currentBuffer);
//                }
//            } catch (InterruptedException ex) { ... handle ...}
//        }
//    }
//
//    void start() {
//        new Thread(new FillingLoop()).start();
//        new Thread(new EmptyingLoop()).start();
//    }
//}