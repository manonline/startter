# 背景
Java的类加载器(Classloader)是一种分层结构，分为
- 引导类加载器(Bootstrap Class Loader) : 引导类加载器在JVM时负责加载rt.jar里面的类。
- 扩展类加载器(Extension Class Loader) : 扩展类加载器负责加载在扩展目录下的jar文件中的类。
- 系统类加载器(System Class Loader) : 系统类加载器则在Classpath上面搜索类加载器。
- 用户定义的类加载器(User-defined Class Loader) : 用户定义的类加载器则从用户指定的路径（比如一个网络URI）加载类。

在该类加载体系中，一个类加载器总是先去上层类加载器加载类，一层一层迭代，当无法找到需要的类时在自己加载。在这种类加载机制中，存在以下几个问题：
- 类版本冲突：当类路径上存在同一个类的不同版本时，如果类加载器找到一个版本，则不再搜索加载下一个版本；
- 无法确定jar之间的依赖关系：现有的JAR标准中缺乏对与Jar文件之间依赖关系的定义支持，因此只有在运行时间无法找到所需的类时，才会打 java.lang.ClassNotFoundException，但这通常不能有效帮助开发人员解决问题；
- 信息隐藏：如果一个jar在类路径上并且被加载，那么所有该jar中的公共类（public class）都会被加载，无法避免某些类被隐藏从而不被加载。尽管在J2EE中改进了类加载机制，可以支持以war或者ear应用为单元进行加载，但是这些问题还是没有被很好地解决，并且热部署效果让人忧心。

OSGi是一个动态的Java模块（Module/Bundle）系统，它规定了如何定义一个模块以及这些模块之间如何交互。每个OSGi的Java模块被称为一个bundle。
- 每个bundle都有自己的类路径，
- 可以精确规定哪些Java包和类可以被导出，
- 需要导入哪些其它bundle的哪些类和包，并从而指明bundle之间的依赖关系。
- bundle可以被在运行时间安装，更新，卸载并且不影响整个应用。
通过这种方式，分层的类加载机制变成了网状的类加载机制。在应用程序启动之前，OSGi就可以检测出来是否所有的依赖关系被满足，并在不满足时精确报出是哪些依赖关系没被满足。

# 分层
通常，OSGi 框架从概念上可以分为三层 
- Module Layer : 模块层主要涉及包及共享的代码；
- Lifecycle Layer : 生命周期层主要涉及 Bundle 的运行时生命周期管理；
- Service Layer : 服务层主要涉及模块之间的交互和通信。

## 模块层
Bundle 是 OSGi 中的基本组件，其表现形式仍然为 Java 概念中传统的 Jar 包，同时通过 META-INF 目录下的 MANIFEST.MF 文件对其予以进一步的定义。每一个Bundle都有自己的ClassLoader
通常一个 MANIFEST.MF文件的内容如下所示：
<code>
Manifest-Version: 1.0
Bundle-ManifestVersion: 2
Bundle-Name: Util
Bundle-SymbolicName : com.ibm.director.la.util
Bundle-Version : 1.0.0.qualifier
Bundle-Activator : com.ibm.director.la.util.Activator
Bundle-Vendor : IBM
Bundle-RequiredExecutionEnvironment : JavaSE-1.6
Import-Package : org.osgi.framework;version="1.3.0"
Bundle-ActivationPolicy : lazy
Export-Package : com.ibm.director.la.util;uses:="org.osgi.framework"
Bundle-ClassPath : libs/jfreechart-1.0.13-swt.jar,
libs/jfreechart-1.0.13.jar,
libs/jfreechart-1.0.13-experimental.jar
</code>

Bundle之间的交互方式：
- 通过Package的Export(对外暴露自己的一个或多个package)和Import(导入别人的一个或多个package)来进行。
- 通过Service的方式进行。一个Bundle作为Service提供方，对外提供Servcie .使用者可以查找到提供的Service. 并使用这个ServÎce. 而提供/使用Service又存在两种方式：一种是经典的做法，通过BundlcContext ( Bundle 的上下文)来提供和获取.一种是使用Declarative Service来实现.

## 生命周期层
Bundle的生命周期被OSGi框架所管理，具有如下几个状态：INSTALLED 、RESOLVED 、UNINSTALLED 、STARTING 、ACTIVE、STOPPING.


## 服务层
一个OSGi Service就是注册到OSGi框架中的一个Java对象。这注册的时候可以设置这个Service的属性。而在获取Service的进候可以根据属性进行过虑(Filter)，Bundle可以通过Bundle的上下文去注册Service或去查询Service。

# Service-Oriented Component Model (SOCM)

首先来看Component 的慨念.Component和Service 从定义上看差不多，任何-个普通的J ava对象都可以通过配置文件中的定义而变为一个Component.Component对外提供了服务并且可以使用其他Component提供的服务，Component 的生命周期被OSGi 框架所管理.我们可以看到，Component是提供和使用服务的另外一种方式，并且具有生命周期.SOCM 在字面上的意思就是面向服务的组件模型.在这个模型中.Component是服务的载体，提供对外使用的服务并可能使用外部的服务，而Component存在于Bundle 之中，系统由多个Bundle 组成.

Declarative Service (DS)
Declarative Service (DS)是OSGi Core Framework 的一个标准服务。DS让我们在Bundle中定义Component，通过配置的方式发布服务、获取服务，以帮助我们实现前面提到的SOCM。有了DS，我们就可以按照Component+Service的方式进委系统的设计与开发。


## 开发web的两种方式
基于OSGI开发B/S应用有两种方式：
- 在OSGI框架中嵌入Http服务器
- 在Servlet容器中嵌入OSGI框架

# 底层框架
- Equinox是OSGI Service Platform Release 4的一个实现。是Eclipse 模块化运行时的核心。
- Knopflerfish另一个选择。
- Apache Felix是Apache软件基金会赞助的一个OSGI容器
- Spring Dynamic Module -> VXXXX

http://hellojava.info/?p=152

# 弊端
## osgi最明显的缺陷 ：
* 模块级别的隔离（仍在一个虚拟机内)，而服务级别的隔离可以隔离计算资源 *

bundle尽管可以为隔离的服务建立独立生命周期管理的热部署方式，以及明确的服务导出和导入依赖能力，但是其最终基于jvm，无法对bundle对应的服务实现计算资源的隔离，一个服务的故障依然会导致整个jvm crash,这使得在一个运行时的osgi上部署模块级服务只获得了模块部署和启停隔离，服务明确依赖的好处，但是没办法实现计算节点的线性扩展，在当前分布式，微服务，网络计算的趋势下，使得osgi只适合构建单一服务节点的内部应用，但是其分离的bundle的部署负担对于微服务架构来说，有点用大炮打蚊子的臭味。

## 推荐的应用架构方式

- 因此必须将基于进程间构建的分布式应用和进程内的单一应用分开来架构设计，对于进程间构建的分布式应用，采取基于soa的理念进行容器模式的服务部署模式，服务交互基于远程服务交互相关协议，采用可忍受网络失败的架构设计原则；
- 对于进程内的应用，如果需要模块级的独立生命周期热部署和模块管理，可以考虑采用OSGI,但是，容器内基于本地进程间通信的模块交付方式不仅能提供同样的独立生命热部署和模块管理，而且具备随时脱离出去部署成单独容器级服务应用的能力，加速进程间的服务交付提供的整体管理和监视环境基础.
- osgi还有用武只地吗？当然我前述都是以构建分布式企业和面向互联网这类应用为前提来讨论的，对于嵌入式的jvm应用，比如著名的osgi案例宝马的车载系统，osgi依然是最好的原则，不过我怀疑基于andriod系统的机制构建类似应用，osgi的采用依然值得商榷。因此，osgi确实面临鸡肋之嫌。

## 分布式应用的关键技术点及解决思路汇总
### 为什么要分布 
- 为得到吞吐量和可靠性及故障隔离的架构属性，需要将传统的单一应用按照业务逻辑进行垂直拆分以实现构建工程的独立，部署的独立。

### 分布失去了什么 
- 进程内服务调用的便利性和可测试性
- 代价巨大的资源分布导致的跨资源事务能力
- 部署和运维工作量指数级增长
- 不可靠网络的应用状态一致性
- 及其复杂的分布式应用依赖关系

### 分布式关键技术选择 
- 容器级的分布式应用工程和部署管理；
- 可视化的分布式应用及服务监视管理视图；
- 前端和后端应用的分离；
- 客户端路由
- 服务注册中心
- 分布式协调
- 消息中件间
- 分布式存储
- 集成框架
