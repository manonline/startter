
Java的类加载器(Classloader)是一种分层结构，分为
# 引导类加载器(Bootstrap Class Loader)
# 扩展类加载器(Extension Class Loader)
# 系统类加载器(System Class Loader)
# 用户定义的类加载器(User-defined Class Loader)。
引导类加载器在JVM时负责加载rt.jar里面的类，扩展类加载器负责加载在扩展目录下的jar文件中的类，系统类加载器则在Classpath上面搜索类加载器，用户定义的类加载器则从用户指定的路径（比如一个网络URI）加载类。在该类加载体系中，一个类加载器总是先去上层类加载器加载类，一层一层迭代，当无法找到需要的类时在自己加载。
