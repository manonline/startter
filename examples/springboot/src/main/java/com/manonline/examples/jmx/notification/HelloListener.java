package com.manonline.examples.jmx.notification;

import com.manonline.examples.jmx.notification.beans.impl.Hello;

import javax.management.Notification;
import javax.management.NotificationListener;

/**
 * Created by davidqi on 2/21/17.
 */
public class HelloListener implements NotificationListener {

    public void handleNotification(Notification notification, Object handback) {
        if (handback instanceof Hello) {
            Hello hello = (Hello) handback;
            hello.printHello(notification.getMessage());
        }
    }

}