package com.manonline.examples.threading.concurrent.blockingqueue.example;

/**
 * Created by davidqi on 2/1/17.
 */

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Indexer implements Runnable {
    private final BlockingQueue<File> blockingQueue;

    @Override
    public void run() {
        System.out.println("Indexer begins to run!");

        try {
            while (true) {
                indexer(blockingQueue.take());
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        System.out.println("Indexer is shutdown!");
    }

    private void indexer(File file) {
        System.out.println("Indexing " + file.getAbsolutePath());
    }

    public Indexer(BlockingQueue<File> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

}