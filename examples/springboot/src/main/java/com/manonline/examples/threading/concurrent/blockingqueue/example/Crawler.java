package com.manonline.examples.threading.concurrent.blockingqueue.example;

/**
 * Created by davidqi on 2/1/17.
 */

import java.io.File;
import java.util.concurrent.BlockingQueue;

public class Crawler implements Runnable {
    private final BlockingQueue<File> bQueue;
    private final File root;

    @Override
    public void run() {
        System.out.println("Crawler begins to run!");
        if (root == null || !root.exists())
            return;
        crawl(root);
        System.out.println("Crawler is shutdown!");

    }

    public void crawl(File root) {
        try {
            if (root.isFile()) {
                System.out.println("Crawling " + root);
                bQueue.put(root);
            } else {
                for (File f : root.listFiles()) {
                    crawl(f);
                }
            }
        } catch (InterruptedException e) {
            System.out.println(e);
        }
    }

    public Crawler(BlockingQueue<File> b, File root) {
        this.bQueue = b;
        this.root = root;
    }

}