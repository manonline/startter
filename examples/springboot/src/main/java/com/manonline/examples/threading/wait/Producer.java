package com.manonline.examples.threading.wait;

/**
 * Created by davidqi on 2/2/17.
 */
class Producer implements Runnable {
    String name;
    ProductStack ps = null;

    Producer(ProductStack ps, String name) {
        this.ps = ps;
        this.name = name;
    }

    public void run() {
        for (int i = 0; i < 20; i++) {
            // create a new product
            Product product = new Product(i, name);
            // push product into productstack
            ps.push(product);
            try {
                Thread.sleep((int) (Math.random() * 200));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}