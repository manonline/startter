package com.manonline.examples.log.implclass;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by davidqi on 2/4/17.
 */
public class LogbackLogging {
    private static Logger log = LoggerFactory.getLogger(LogbackLogging.class);

    public static void main(String[] args) {
        log.trace("======trace");
        log.debug("======debug");
        log.info("======info");
        log.warn("======warn");
        log.error("======error");
    }
}