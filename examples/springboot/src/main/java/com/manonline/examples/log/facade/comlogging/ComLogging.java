package com.manonline.examples.log.facade.comlogging;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by davidqi on 2/4/17.
 * =================================================================
 * 1. 首先在classpath下寻找自己的配置文件commons-logging.properties，如果找到，则使用其中定义的Log实现类；
 * 2. 如果找不到commons-logging.properties文件，则在查找是否已定义系统环境变量org.apache.commons.logging.Log，
 *    找到则使用其定义的Log实现类；如果在Tomact中可以建立一个叫 ：CATALINA_OPTS 的环境变量给他的值 ：
 *    -Dorg.apache.commons.logging.Log = org.apache.commons.logging.impl.SimpleLog
 *    -Dorg.apache.commons.logging.simplelog.defaultlog = warn
 * 3. 否则，查看classpath中是否有Log4j的包，如果发现，则自动使用Log4j作为日志实现类；
 * 4. 否则，使用JDK自身的日志实现类（JDK1.4以后才有日志实现类）；
 * 5. 否则，使用commons-logging自己提供的一个简单的日志实现类SimpleLog；
 * =================================================================
 * -org.apache.commons.logging.impl.Jdk14Logger : 使用JDK1.4。
 * -org.apache.commons.logging.impl.Log4JLogger : 使用Log4J。
 * -org.apache.commons.logging.impl.LogKitLogger: 使用 avalon-Logkit。
 * -org.apache.commons.logging.impl.SimpleLog   : common-logging自带日志实现类。它实现了Log接口，
 * 把日志消息都输出到系统错误流System.err 中。
 * -org.apache.commons.logging.impl.NoOpLog     : common-logging自带日志实现类。它实现了Log接口。
 * 其输出日志的方法中不进行任何操作。
 * =================================================================
 */
public class ComLogging {
    static Log log = LogFactory.getLog(ComLogging.class);

    public static void main(String[] args) {
        log.debug("Here is some DEBUG");
        log.info("Here is some INFO");
        log.warn("Here is some WARN");
        log.error("Here is some ERROR");
        log.fatal("Here is some FATAL");
    }
}
