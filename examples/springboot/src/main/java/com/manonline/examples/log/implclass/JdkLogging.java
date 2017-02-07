package com.manonline.examples.log.implclass;

import java.io.IOException;
import java.util.Date;
import java.util.logging.*;

/**
 * Created by davidqi on 2/4/17.
 */
public class JdkLogging {
    public static void main(String[] args) throws IOException {
        Logger logger = Logger.getLogger("tesglog");
        // set log level
        logger.setLevel(Level.ALL);
        // create a log handler
        FileHandler fileHandler = new FileHandler("testlog.log");
        fileHandler.setLevel(Level.ALL);
        // set log format
        fileHandler.setFormatter(new LogFormatter());
        // assign handler to logger
        logger.addHandler(fileHandler);
        logger.info("This is test java util log");
    }
}

class LogFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        Date date = new Date();
        String sDate = date.toString();
        return "[" + sDate + "]" + "[" + record.getLevel() + "]"
                + record.getClass() + record.getMessage() + "\n";
    }

}