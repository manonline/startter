package com.manonline.examples.log.facade.slf4j;

/**
 * Created by davidqi on 2/4/17.
 * SLF4J只是各种日志实现的一个接口抽象(facade), 而最佳的实现是Logback, 相对于Log4J的同门兄弟
 * (皆出自Ceki Gülcü之手), 它的性能表现更加优异。
 * ================
 * slf4j
 * log.debug("Found {} records matching filter: '{}'", records, filter);
 * others
 * log.debug("Found " + records + " records matching filter: '" + filter + "'");
 * ================
 * if(log.isDebugEnabled()) {
 *     log.debug("Place for your commercial");
 * }
 * 这种做法对性能的提高几乎微乎其微(前面在提到SLF4J的时候已经说明), 而且是一种过度优化的表现. 极少情况下需要这样写,
 * 除非构造日志信息非常耗性能.
 * ================
 * 默认配置(实现)
 * common-logging -> log4j;
 * slf4j -> logback;
 */
public class Slf4j {

}
