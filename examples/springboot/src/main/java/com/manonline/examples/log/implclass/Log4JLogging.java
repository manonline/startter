package com.manonline.examples.log.implclass;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Created by davidqi on 2/4/17.
 */
public class Log4JLogging {
        public static void main(String[] args) {
            // 1. create log
            Logger logger = Logger.getLogger(Log4JLogging.class);
            // 2. get log config file
            PropertyConfigurator.configure("log4j.properties");
            // 3. start log
            logger.debug("Here is some DEBUG");
            logger.info("Here is some INFO");
            logger.warn("Here is some WARN");
            logger.error("Here is some ERROR");
            logger.fatal("Here is some FATAL");
        }
}
