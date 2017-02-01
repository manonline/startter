package com.manonline.examples.threading.concurrent.blockingqueue.example;

/**
 * Created by davidqi on 2/1/17.
 */

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class Starter {
    public static final int BOUND = 1;

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<File> blockingQueue = new SynchronousQueue<File>();
        File file = new File("E:\\BaiduYunDownload");

        Crawler crawler = new Crawler(blockingQueue, file);
        Indexer indexer = new Indexer(blockingQueue);

        Thread t1 = new Thread(crawler);
        Thread t2 = new Thread(indexer);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}

