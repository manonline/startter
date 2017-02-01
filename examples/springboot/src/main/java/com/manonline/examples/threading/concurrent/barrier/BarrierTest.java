package com.manonline.examples.threading.concurrent.barrier;

import java.awt.image.DataBuffer;
import java.util.concurrent.Exchanger;

/**
 * Created by davidqi on 2/2/17.
 */
class FillAndEmpty {
    Exchanger<DataBuffer> exchanger = new Exchanger<DataBuffer>();
    DataBuffer initialEmptyBuffer = ... a made-up type
    DataBuffer initialFullBuffer = ...

    class FillingLoop implements Runnable {
        public void run() {
            DataBuffer currentBuffer = initialEmptyBuffer;
            try {
                while (currentBuffer != null) {
                    addToBuffer(currentBuffer);
                    if (currentBuffer.isFull())
                        currentBuffer = exchanger.exchange(currentBuffer);
                }
            } catch (InterruptedException ex) { ... handle ... }
        }
    }

    class EmptyingLoop implements Runnable {
        public void run() {
            DataBuffer currentBuffer = initialFullBuffer;
            try {
                while (currentBuffer != null) {
                    takeFromBuffer(currentBuffer);
                    if (currentBuffer.isEmpty())
                        currentBuffer = exchanger.exchange(currentBuffer);
                }
            } catch (InterruptedException ex) { ... handle ...}
        }
    }

    void start() {
        new Thread(new FillingLoop()).start();
        new Thread(new EmptyingLoop()).start();
    }
}