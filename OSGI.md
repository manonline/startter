
Java的类加载器(Classloader)是一种分层结构，分为
# 引导类加载器(Bootstrap Class Loader)
# 扩展类加载器(Extension Class Loader)
# 系统类加载器(System Class Loader)
# 用户定义的类加载器(User-defined Class Loader)。
引导类加载器在JVM时负责加载rt.jar里面的类，扩展类加载器负责加载在扩展目录下的jar文件中的类，系统类加载器则在Classpath上面搜索类加载器，用户定义的类加载器则从用户指定的路径（比如一个网络URI）加载类。在该类加载体系中，一个类加载器总是先去上层类加载器加载类，一层一层迭代，当无法找到需要的类时在自己加载。


在这种类加载机制中，存在以下几个问题：
# 类版本冲突：当类路径上存在同一个类的不同版本时，如果类加载器找到一个版本，则不再搜索加载下一个版本；
# 无法确定jar之间的依赖关系：现有的JAR标准中缺乏对与Jar文件之间依赖关系的定义支持，因此只有在运行时间无法找到所需的类时，才会打出java.lang.ClassNotFoundException，但这通常不能有效帮助开发人员解决问题；
# 信息隐藏：如果一个jar在类路径上并且被加载，那么所有该jar中的公共类（public class）都会被加载，无法避免某些类被隐藏从而不被加载。尽管在J2EE中改进了类加载机制，可以支持以war或者ear应用为单元进行加载，但是这些问题还是没有被很好地解决，并且热部署效果让人忧心。

OSGi是一个动态的Java模块（Module）系统，它规定了如何定义一个Module以及这些模块之间如何交互。每个OSGi的Java模块被称为一个bundle。每个bundle都有自己的类路径，可以精确规定哪些Java包和类可以被导出，需要导入哪些其它bundle的哪些类和包，并从而指明bundle之间的依赖关系。另外bundle可以被在运行时间安装，更新，卸载并且不影响整个应用。通过这种方式，分层的类加载机制变成了网状的类加载机制。在应用程序启动之前，OSGi就可以检测出来是否所有的依赖关系被满足，并在不满足时精确报出是哪些依赖关系没被满足。

通常，OSGi 框架从概念上可以分为三层：模块层、生命周期层和服务层。各层的主要功能如下：
Module Layer : 模块层主要涉及包及共享的代码；
Lifecycle Layer : 生命周期层主要涉及 Bundle 的运行时生命周期管理；
Service Layer : 服务层主要涉及模块之间的交互和通信。

模块层
Bundle 是 OSGi 中的基本组件，其表现形式仍然为 Java 概念中传统的 Jar 包，同时通过 META-INF 目录下的 MANIFEST.MF 文件对其予以进一步的定义。通常一个 MANIFEST.MF 文件的内容如下所示：
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
 

Bundle之间的交互方式：
1.通过Package的Export(对外暴露自己的一个或多个package)和Import(导入别人的一个或多个package)来进行。
2.通过Service的方式进行。一个Bundle作为Service提供方，对外提供Servcie .使用者可以查找到提供的Service. 并使用这个ServÎce. 而提供/使用Service又存在两种方式：一种是经典的做法，通过BundlcContext ( Bundle 的上下文)来提供和获取.一种是使用Declarative Service来实现.

方法	描述
1.Export-Package	根据OSGi规范，每个工程可以通过声明Exprot-Package对外提供访问此工程中的类和接口，可以先把bundle导出，再导入到需要调用的bundle中
2.OSGi服务	通过将要对外提供功能声明为OSGi的服务实现面向接口、面向服务式的设计；
3.Event	基于OSGi的Event服务也是实现模块交互的一种可选方法，模块对外发布事件，订阅了此事件的模块就会相应地接收到消息，从而做出反应，以达到交互的目的。

生命周期层
Bundle的生命周期被OSGi框架所管理，具有如下几个状态：INSTALLED 、RESOLVED 、UNINSTALLED 、STARTING 、ACTIVE、STOPPING.


服务层
一个OSGi Service就是注册到OSGi框架中的一个Java对象。这注册的时候可以设置这个Service的属性。而在获取Service的进候可以根据属性进行过虑(Filter)，Bundle可以通过Bundle的上下文去注册Service或去查询Service。

Service-Oriented Component Model (SOCM)
首先来看Component 的慨念.Component和Service 从定义上看差不多，任何-个普通的J ava对象都可以通过配置文件中的定义而变为一个Component.Component对外提供了服务并且可以使用其他Component提供的服务，Component 的生命周期被OSGi 框架所管理.我们可以看到，Component是提供和使用服务的另外一种方式，并且具有生命周期.
SOCM 在字面上的意思就是面向服务的组件模型.在这个模型中.Component是服务的载体，提供对外使用的服务并可能使用外部的服务，而Component存在于Bundle 之中，系统由多个Bundle 组成.

Declarative Service (DS)
Declarative Service (DS)是OSGi Core Framework 的一个标准服务。DS让我们在Bundle中定义Component，通过配置的方式发布服务、获取服务，以帮助我们实现前面提到的SOCM。有了DS，我们就可以按照Component+Service的方式进委系统的设计与开发。


开发web的两种方式
基于OSGI开发B/S应用有两种方式：
1）在OSGI框架中嵌入Http服务器
2）在Servlet容器中嵌入OSGI框架

