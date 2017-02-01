# 容器 ：存放数据／对象的对象；实现增删改查操作；
* Collection
Collection是最基本的集合接口，一个Collection代表一组Object，Java SDK不提供直接继承自Collection的类，Java SDK提供的类都是继承自Collection的“子接口”如List和Set。
* List
以元素插入的次序来放置元素，不会重新排列。
* Set
不接爱重复元素，它会使用自己内部的一个排列机制。
* Map
一群成对的key-value对象，即所持有的是key-value pairs。Map中不能有重复的key，它拥有自己的内部排列机制.
* Vector
非常类似ArrayList，但是Vector是同步的。由Vector创建的Iterator，虽然和ArrayList创建的Iterator是同一接口，但是，因为Vector是同步的，当一个Iterator被创建而且正在被使用，另一个线程改变了Vector的状态（例 如，添加或删除了一些元素），这时调用Iterator的方法时将抛出ConcurrentModificationException，因此必须捕获该异常。
Java容器类类库的用途是“保存对象”，它分为两类：
Collection----一组独立的元素，通常这些元素都服从某种规则。List必须保持元素特定的顺序，而Set不能有重复元素。
Map----一组成对的“键值对”对象，即其元素是成对的对象，最典型的应用就是数据字典，并且还有其它广泛的应用。另外，Map可以返回其所有键组成的Set和其所有值组成的Collection，或其键值对组成的Set，并且还可以像数组一样扩展多维Map，只要让Map中键值对的每个“值”是一个Map即可。
1.迭代器
迭代器是一种设计模式，它是一个对象，它可以遍历并选择序列中的对象，而开发人员不需要了解该序列的底层结构。迭代器通常被称为“轻量级”对象，因为创建它的代价小。
Java中的Iterator功能比较简单，并且只能单向移动：
(1) 使用方法iterator()要求容器返回一个Iterator。第一次调用Iterator的next()方法时，它返回序列的第一个元素。
(2) 使用next()获得序列中的下一个元素。
(3) 使用hasNext()检查序列中是否还有元素。
(4) 使用remove()将迭代器新返回的元素删除。
Iterator是Java迭代器最简单的实现，为List设计的ListIterator具有更多的功能，它可以从两个方向遍历List，也可以从List中插入和删除元素。



1、容器类和Array的区别、择取 * 容器类仅能持有对象引用（指向对象的指针），而不是将对象信息copy一份至数列某位置。 * 一旦将对象置入容器内，便损失了该对象的型别信息。 
2、 * 在各种Lists中，最好的做法是以ArrayList作为缺省选择。当插入、删除频繁时，使用LinkedList()； Vector总是比ArrayList慢，所以要尽量避免使用。 * 在各种Sets中，HashSet通常优于HashTree（插入、查找）。只有当需要产生一个经过排序的序列，才用TreeSet。 HashTree存在的唯一理由：能够维护其内元素的排序状态。 * 在各种Maps中 HashMap用于快速查找。 * 当元素个数固定，用Array，因为Array效率是最高的。 
结论：最常用的是ArrayList，HashSet，HashMap，Array。而且，我们也会发现一个规律，用TreeXXX都是排序的。 
容器 (数组，Collection and Map)：存放数据／对象的对象；实现增删改查操作；
集合 (Collection)：交，并，补，差操作；

Collection和Map接口之间的主要区别在于 ：
Collection中存储了一组对象，接口是一组允许重复的对象；
Map存储关键字/值对，在Map对象中，每一个关键字最多有一个关联的值；

Collection是List和Set两个接口的基接口 ：
List在Collection之上增加了"有序"。以元素安插的次序来放置元素，不会重新排列；
Set在Collection之上增加了"唯一"。使用自己内部的一个排列机制；

List的两个常用实现类 ：
ArrayList里边存放的元素在排列上存在一定的先后顺序，而且ArrayList是采用数组存放元素；
List LinkedList采用的则是链表；

Collection 
Map接口是一组成对的键－值对象，即所持有的是key-value pairs。Map中不能有重复的key。拥有自己的内部排列机制。
## 数组(Array/[])
数组与其它容器的区别体现在三个方面：效率，类型识别以及可以持有primitives。数组是Java提供的，能随机存储和访问reference序列的诸多方法中的，最高效的一种。数组是一个简单的线性序列，所以它可以快速的访问其中的元素。但是速度是有代价的；当你创建了一个数组之后，它的容量就固定了，而且在其生命周期里不能改变。也许你会提议先创建一个数组，等到快不够用的时候，再创建一个新的，然后将旧的数组里的reference全部导到新的里面。其实（我们以后会讲的）ArrayList就是这么做的。但是这种灵活性所带来的开销，使得ArrayList的效率比起数组有了明显下降。
## Collection 
### 列表(List)
2.List的功能方法
List(interface)
次序是List最重要的特点；它确保维护元素特定的顺序。List为Collection添加了许多方法，使得能够向List中间插入与移除元素(只推荐LinkedList使用)。一个List可以生成ListIterator，使用它可以从两个方向遍历List，也可以从List中间插入和删除元素。
ArrayList
由数组实现的List。它允许对元素进行快速随机访问，但是向List中间插入与移除元素的速度很慢。ListIterator只应该用来由后向前遍历ArrayList，而不是用来插入和删除元素，因为这比LinkedList开销要大很多。
LinkedList
对顺序访问进行了优化，向List中间插入与删除的开销不大，随机访问则相对较慢(可用ArrayList代替)。它具有方法addFirst()、addLast()、getFirst()、getLast()、removeFirst()、removeLast()，这些方法(没有在任何接口或基类中定义过)使得LinkedList可以当作堆栈、队列和双向队列使用。
List是有序的Collection，使用此接口能够精确的控制每个元素插入的位置。用户能够使用索引（元素在List中的位置，类似于数组下标）来访问List中的元素，这类似于Java的数组。除了具有Collection接口必备的iterator()方法外，List还提供一个listIterator()方法，返回一个ListIterator接口，和标准的Iterator接口相比，ListIterator多了一些add()之类的方法，允许添加，删除，设定元素，还能向前或向后遍历。实现List接口的常用类有LinkedList，ArrayList，Vector和Stack。
#### LinkedList
实现一个链表。由这个类定义的链表也可以像栈或队列一样被使用。
#### ArrayList
ArrayList实现了可变大小的数组。它允许所有元素，包括null。ArrayList没有同步。size，isEmpty，get，set方法运行时间为常数。但是add方法开销为分摊的常数，添加n个元素需要O(n)的时间。其他的方法运行时间为线性。每个ArrayList实例都有一个容量（Capacity），即用于存储元素的数组的大小。这个容量可随着不断添加新元素而自动增加，但是增长算法并没有定义。ArrayList当需要插入大量元素时，在插入前可以调用ensureCapacity方法来增加ArrayList的容量以提高插入效率。和LinkedList一样，ArrayList也是非同步的（unsynchronized）。
#### Vector
实现一个类似数组一样的表，自动增加容量来容纳你所需的元素。使用下标存储和检索对象就象在一个标准的数组中一样。你也可以用一个迭代器从一个Vector中检索对象。Vector是唯一的同步容器类??当两个或多个线程同时访问时也是性能良好的。（同步的含义：即同时只能一个进程访问，其他等待）
#### Stack
这个类从Vector派生而来，并且增加了方法实现栈??一种后进先出的存储结构。
### 集合(Set)
3.Set的功能方法
Set(interface)
存入Set的每个元素必须是唯一的，因为Set不保存重复元素。加入Set的Object必须定义equals()方法以确保对象的唯一性。Set与Collection有完全一样的接口。Set接口不保证维护元素的次序。
HashSet
为快速查找而设计的Set。存入HashSet的对象必须定义hashCode()。
TreeSet
保持次序的Set，底层为树结构。使用它可以从Set中提取有序的序列。
LinkedHashSet
具有HashSet的查询速度，且内部使用链表维护元素的顺序(插入的次序)。于是在使用迭代器遍历Set时，结果会按元素插入的次序显示。
HashSet采用散列函数对元素进行排序，这是专门为快速查询而设计的；TreeSet采用红黑树的数据结构进行排序元素；LinkedHashSet内部使用散列以加快查询速度，同时使用链表维护元素的次序，使得看起来元素是以插入的顺序保存的。需要注意的是，生成自己的类时，Set需要维护元素的存储顺序，因此要实现Comparable接口并定义compareTo()方法。
Set接口
Set不允许包含相同的元素，如果试图把两个相同元素加入同一个集合中，add方法返回false。
Set判断两个对象相同不是使用==运算符，而是根据equals方法。也就是说，只要两个对象用equals方法比较返回true，Set就不 会接受这两个对象。

HashSet
HashSet有以下特点
 不能保证元素的排列顺序，顺序有可能发生变化
 不是同步的
 集合元素可以是null,但只能放入一个null
当向HashSet结合中存入一个元素时，HashSet会调用该对象的hashCode()方法来得到该对象的hashCode值，然后根据 hashCode值来决定该对象在HashSet中存储位置。
简单的说，HashSet集合判断两个元素相等的标准是两个对象通过equals方法比较相等，并且两个对象的hashCode()方法返回值相 等
注意，如果要把一个对象放入HashSet中，重写该对象对应类的equals方法，也应该重写其hashCode()方法。其规则是如果两个对 象通过equals方法比较返回true时，其hashCode也应该相同。另外，对象中用作equals比较标准的属性，都应该用来计算 hashCode的值。

LinkedHashSet
LinkedHashSet集合同样是根据元素的hashCode值来决定元素的存储位置，但是它同时使用链表维护元素的次序。这样使得元素看起 来像是以插入顺序保存的，也就是说，当遍历该集合时候，LinkedHashSet将会以元素的添加顺序访问集合的元素。
LinkedHashSet在迭代访问Set中的全部元素时，性能比HashSet好，但是插入时性能稍微逊色于HashSet。

TreeSet类
TreeSet是SortedSet接口的唯一实现类，TreeSet可以确保集合元素处于排序状态。TreeSet支持两种排序方式，自然排序 和定制排序，其中自然排序为默认的排序方式。向TreeSet中加入的应该是同一个类的对象。
TreeSet判断两个对象不相等的方式是两个对象通过equals方法返回false，或者通过CompareTo方法比较没有返回0
自然排序
自然排序使用要排序元素的CompareTo（Object obj）方法来比较元素之间大小关系，然后将元素按照升序排列。
Java提供了一个Comparable接口，该接口里定义了一个compareTo(Object obj)方法，该方法返回一个整数值，实现了该接口的对象就可以比较大小。
obj1.compareTo(obj2)方法如果返回0，则说明被比较的两个对象相等，如果返回一个正数，则表明obj1大于obj2，如果是 负数，则表明obj1小于obj2。
如果我们将两个对象的equals方法总是返回true，则这两个对象的compareTo方法返回应该返回0
定制排序
自然排序是根据集合元素的大小，以升序排列，如果要定制排序，应该使用Comparator接口，实现 int compare(T o1,T o2)方法

HashSet:底层数据结构是哈希表，本质就是对哈希值的存储，通过判断元素的hashCode方法和equals方法来保证元素的唯一性，当hashCode值不相同，就直接存储了，不用在判断equals了，当hashCode值相同时，会在判断一次euqals方法的返回值是否为true，如果为true则视为用一个元素，不用存储，如果为false，这些相同哈希值不同内容的元素都存放一个桶里（当哈希表中有一个桶结构，每一个桶都有一个哈希值）
TreeSet:底层的数据结构是二叉树，可以对Set集合中的元素进行排序,这种结构，可以提高排序性能, 根据比较方法的返回值确定的,只要返回的是0.就代表元素重复

#### HashSet
使用HashMap的一个集的实现。虽然集定义成无序，但必须存在某种方法能相当高效地找到一个对象。使用一个HashMap对象实现集的存储和检索操作是在固定时间内实现的.
#### TreeSet
在集中以升序对对象排序的集的实现。这意味着从一个TreeSet对象获得第一个迭代器将按升序提供对象。TreeSet类使用了一个TreeMap.
## 映射(Map)：
请注意，Map没有继承Collection接口，Map提供key到value的映射。一个Map中不能包含相同的key，每个key只能映射一个value。Map接口提供3种集合的视图，## Map的内容可以被当作一组key集合，一组value集合，或者一组key-value映射。
HashMap：Map的实现类，缺省情况下是非同步的，可以通过Map Collections.synchronizedMap(Map m)来达到线程同步
HashTable：Dictionary的子类，缺省是线程同步的。不允许关键字或值为null
当元素的顺序很重要时选用TreeMap，当元素不必以特定的顺序进行存储时，使用HashMap。Hashtable的使用不被推荐，因为HashMap提供了所有类似的功能，并且速度更快。当你需要在多线程环境下使用时，HashMap也可以转换为同步的

#### HashMap
HashMap和Hashtable类似，不同之处在于HashMap是非同步的，并且允许null，即null value和null key。，但是将HashMap视为Collection时（values()方法可返回Collection），其迭代子操作时间开销和HashMap的容量成比例。因此，如果迭代操作的性能相当重要的话，不要将HashMap的初始化容量设得过高，或者load factor过低。实现一个映象，允许存储空对象，而且允许键是空（由于键必须是唯一的，当然只能有一个）。
#### HashTable
实现一个映象，所有的键必须非空。为了能高效的工作，定义键的类必须实现hashcode()方法和equal()方法。这个类是前面java实现的一个继承，并且通常能在实现映象的其他类中更好的使用。
#### WeakHashMap
实现这样一个映象：通常如果一个键对一个对象而言不再被引用，键/对象对将被舍弃。这与HashMap形成对照，映象中的键维持键/对象对的生命周期，尽管使用映象的程序不再有对键的引用，并且因此不能检索对象。
#### TreeMap
实现这样一个映象，对象是按键升序排列的。



# 工具类

其实理解了集合框架也就差不多了。工具类，正如上面说过的，是对集合功能的扩展，其中大部分是static函数，一般不必实例化创建对象，而直接调用对集合进行操作。
接下来为了方便理解，以Array Arrays ArraysList
Array：认真看api索引的话，Array有两个。一个是sql中的接口，一个是类，我们在这里说的是这个类。　　
Arrays：对数组的一些列操作。
ArrayList是一个容器。


1）效率：
数组扩容是对ArrayList效率影响比较大的一个因素。
每当执行Add、AddRange、Insert、InsertRange等添加元素的方法，都会检查内部数组的容量是否不够了，如果是，它就会以当前容量的两倍来重新构建一个数组，将旧元素Copy到新数组中，然后丢弃旧数组，在这个临界点的扩容操作，应该来说是比较影响效率的。

ArrayList是Array的复杂版本
ArrayList内部封装了一个Object类型的数组，从一般的意义来说，它和数组没有本质的差别，甚至于ArrayList的许多方法，如Index、IndexOf、Contains、Sort等都是在内部数组的基础上直接调用Array的对应方法。

2）类型识别：
ArrayList存入对象时，抛弃类型信息，所有对象屏蔽为Object，编译时不检查类型，但是运行时会报错。
ArrayList与数组的区别主要就是由于动态增容的效率问题了

3）ArrayList可以存任何Object，如String等。



