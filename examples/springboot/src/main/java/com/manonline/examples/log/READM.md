#Java Logging
Apache Common-Logging是广泛使用的Java日志门面库。Apache Common-Logging通过动态查找的机制，在程序运行时自动找出真正使用的日志库。 但是在写OSGI插件时，它不能工作了。 原因是Apache Common-Logging使用了ClassLoader寻找和载入底层的日志库。而OSGI中，不同的插件使用自己的ClassLoader。 
一个线程的ClassLoader在执行不同的插件时，其执行能力是不同的。 OSGI的这种机制保证了插件互相独立，然而确使Apache Common-Logging无法工作！ 解决之道是使用新的日志门面库Slf4j。 Slf4j库类似于Apache Common-Logging。但是，他在编译时静态绑定真正的Log库。使用Slf4j时，如果你需要使用某一种日志实现，那么你必须选择正确的Slf4j的jar包的集合。 这确实麻烦了一点，但总算可以在OSGI中开发日志了，并且稍微提高执行效率。

## Facade 
### Apache(Jakarta) Common Logging
- Log4j
### SLF4J
- SLF4J(slf4j.org)又称Simple Logging Facade for Java，是一个通用的logging接口。
- 它试图一统Logging框架的天下，兼容了(Log4j 1, java.util.logging和Apache Commons Logging(JCL)）这三个最流行的Logging框架。
- Logback就是SLF4J的默认实现。
 
## Concerete Implementation
### JBoss Logging
### Java Logging API
### Log4J 1.0
### Logback
### Log4J 2.0

## SLF4J with Common Logging
使用CommonLog接口而实际由Slf4j和Log4j实现的过程 
1. 项目中照常使用一下导入声明
<pre><code>
import org.apache.commons.logging.Log; 
import org.apache.commons.logging.LogFactory; 
</code></pre>
2. 仍然在src下使用log4j.properties文件进行配置。 
  
3. 但是使用不同的jar包 

3.1 jcl to slf4j bridge : jcl-over-slf4j-1.5.2.jar 这提供了Commons-Logging接口，以及使用common-loggin的接口，底层还是由SLF4J来决定哪种实现机制 。
3.2 slf4j libray : slf4j-api-1.5.2.jar   这是Slf4j库。 
3.3 slf4j to log4j bridge : slf4j-log4j12-1.5.2.jar 这包含Log4j的适配器和静态绑定log4j底层实现。
3.4 log4j implementation : log4j-1.2.15.jar 这是log4j的库。 Slf4j并不改变这个底层实现库。 

## SLF4J with legacy code
### log4j or common logging
SLF4J对于Log4J 1和Apache commons Logging的支持方式是提供了实现Log4j和Apache commons Logging接口的SLF4J实现。使用方式是

去取对Log4J和Apache commons Logging的Jar包的引用
引入SLF4J的对应接口的实现包。
2.4.1.1 移除引用

如果你的系统是直接的使用了Log4j或者Apache commons Logging框架的话，你可以直接把对他们的引用去掉就可以了。如果是你所引用的第三方包里面引用了Log4j或者Apache commons Logging，可以使用<exclusions>标签去掉对他们的引用，如下所示：
<dependency>
  <groupId>org.springframework.ldap</groupId>
  <artifactId>spring-ldap-core</artifactId>
  <exclusions>
      <exclusion>
          <artifactId>commons-logging</artifactId>
          <groupId>commons-logging</groupId>
      </exclusion>
  </exclusions>
</dependency>

Maven导入对应的SLF4J实现包

<!-- Log4j 的SLF4J 实现 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>log4j-over-slf4j</artifactId>
    <version>1.7.6</version>
</dependency>
<!-- Apache commons Logging 的SLF4J实现 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jcl-over-slf4j</artifactId>
    <version>1.7.6</version>
</dependency>

### jdk logging
SLF4J的jul-to-slf4j模块实现了一个java.util.logging handler，该handler会把对java.util.logging的调用都转化成对SLF4J实现的调用。所以需要以下俩个步骤：

导入jul-to-slf4j模块
启用jul-to-slf4j模块
2.4.2.1 导入jul-to-slf4j模块maven版

<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>jul-to-slf4j</artifactId>
    <version>1.7.6</version>
</dependency>
2.4.2.2 导入jul-to-slf4j模块非Maven版

把http://slf4j.org/dist/slf4j-1.7.6.zip下载下来，把压缩包里的jul-to-slf4j-1.7.6.jar放到classpath中。

2.4.2.3 启用jul-to-slf4j模块

在logging.properties中添加如下一行：
handlers = org.slf4j.bridge.SLF4JBridgeHandler 

## Best Practices
### Static Logger
两种方式的优劣概述如下：
静态Logger对象相对来说更符合语义，节省CPU，节省内存，不支持注入
对象变量Logger支持注入，对于一个JVM中运行的多个引用了同一个类库的应用程序，可以在不同的应用程序中对同个类的Logger进行不同的配置。比如Tomcat上部署了俩个应用，他们都引用了同一个lib。

### isXXXXEnabled
这组方法的作用主要是避免没必要的log信息对象的产生，尤其是对于不支持参数化信息的Log框架(Log4j 1, commons-logging)。如下面的例子所示，如果没有加debug级别判断，在Debug级别被禁用的环境（生产环境）中，第二行的代码将没有必要的产生多个String对象。
1 if(logger.isDebugEnabled()){
2   logger.debug("["+resultCount+"]/["+totalCount+"] of users are returned");
3 }
如果使用了参数信息的方法，在如下代码中，即使没有添加debug级别（第一行）判断，在生产环境中，第二行代码只会生成一个String对象。
1 if(logger.isDebugEnabled()){
2   logger.debug("[{}]/[{}] of users in group are returned", resultCount,totalCount);
3 }
因此，为了代码的可读性，我一般情况下使用参数化信息的方法，并且不做Logger级别是否开启的判断，换句话说，这组方法我一般情况下不会用。

### Context
在Log中必须尽量带入上下文的信息，对比以下俩个Log信息，后者比前者更有作用
"开始导入配置文件"
"开始导入配置文件[/etc/myService/config.properties]"

### Using []

把变量和普通文本隔离有这么几个作用
在你阅读Log的时候容易捕捉到有用的信息
在使用工具分析Log的时候可以更方便抓取
在一些情况下不容易混淆
对比以下下面的两条Log，前者发生了混淆：
"获取用户lj12月份发邮件记录数"
"获取用户[lj1][2]月份发邮件记录数"

### 对于Exception，要每次都Log StackTrace吗？

在一些Exception处理机制中，我们会每层或者每个Service对应一个RuntimeException类，并把他们抛出去，留给最外层的异常处理层处理。典型代码如下:
try{
  
}catch(Exception ex){
  String errorMessage=String.format("Error while reading information of user [%s]",userName);
  logger.error(errorMessage,ex);
  throw new UserServiceException(errorMessage,ex);
}
这个时候问题来了，在最底层出错的地方Log了异常的StackTrace，在你把这个异常外上层抛的过程中，在最外层的异常处理层的时候，还会再Log一次异常的StackTrace，这样子你的Log中会有大篇的重复信息。
我碰到这种情况一般是这么处理的：Log之！原因有以下这几个方面：
这个信息很重要，我不确认再往上的异常处理层中是否会正常的把它的StackTrace打印出来。
如果这个异常信息在往上传递的过程中被多次包装，到了最外层打印StackTrace的时候最底层的真正有用的出错原因有可能不会被打印出来。
如果有人改变了LogbackException打印的配置，使得不能完全打印的时候，这个信息可能就丢了。
就算重复了又怎么样？都Error了都Warning了还省那么一点空间吗？