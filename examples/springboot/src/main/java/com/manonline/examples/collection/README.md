# 容器 ：
存放数据／对象的对象；实现增删改查操作；
* 容器 (Collection and Map)：存放数据／对象的对象；实现增删改查操作；
* 集合 (Collection)：交，并，补，差操作；
## 类型
* Collection : 一组独立的元素，通常这些元素都服从某种规则。List以元素插入的次序来放置元素，不会重新排列。而Set不接爱重复元素，它会使用自己内部的一个排列机制。
* Map : 一组成对的“键值对”对象，一群成对的key-value对象，即所持有的是key-value pairs。Map中不能有重复的key，它拥有自己的内部排列机制。或其键值对组成的Set，并且还可以像数组一样扩展多维Map，只要让Map中键值对的每个“值”是一个Map即可。
## 容器和Array区别
容器类仅能持有对象引用（指向对象的指针），而不是将对象信息copy一份至数列某位置。一旦将对象置入容器内，便损失了该对象的型别信息。 
## 常用
1. 当元素个数固定，用Array，因为Array效率是最高的。 
2. 最常用的是Array[]，ArrayList，HashSet，HashMap。而且，我们也会发现一个规律，用TreeXXX都是排序的。 
3. 在各种Lists中，最好的做法是以ArrayList作为缺省选择。当插入、删除频繁时，使用LinkedList()；Vector总是比ArrayList慢，所以要尽量避免使用。 
4. 在各种Sets中，HashSet通常优于HashTree（插入、查找）。只有当需要产生一个经过排序的序列，才用TreeSet。 HashTree存在的唯一理由：能够维护其内元素的排序状态。 
5. 在各种Maps中 HashMap用于快速查找。 

# 详解
## 数组(Array/[])
数组与其它容器的区别体现在三个方面：效率，类型识别以及可以持有primitives。数组是Java提供的，能随机存储和访问reference序列的诸多方法中的，最高效的一种。数组是一个简单的线性序列，所以它可以快速的访问其中的元素。但是速度是有代价的；当你创建了一个数组之后，它的容量就固定了，而且在其生命周期里不能改变。也许你会提议先创建一个数组，等到快不够用的时候，再创建一个新的，然后将旧的数组里的reference全部导到新的里面。其实（我们以后会讲的）ArrayList就是这么做的。但是这种灵活性所带来的开销，使得ArrayList的效率比起数组有了明显下降。
## Collection 
### 列表(List)
List是有序的Collection，使用此接口能够精确的控制每个元素插入的位置。用户能够使用索引（元素在List中的位置，类似于数组下标）来访问List中的元素，这类似于Java的数组。除了具有Collection接口必备的iterator()方法外，List还提供一个listIterator()方法，返回一个ListIterator接口，和标准的Iterator接口相比，ListIterator多了一些add()之类的方法，允许添加，删除，设定元素，还能向前或向后遍历。实现List接口的常用类有LinkedList，ArrayList，Vector和Stack。
#### ArrayList
实现了可变大小的数组,由数组实现的List。它允许所有元素，包括null。它允许对元素进行快速随机访问，但是向List中间插入与移除元素的速度很慢。ListIterator只应该用来由后向前遍历ArrayList，而不是用来插入和删除元素，因为这比LinkedList开销要大很多。
#### LinkedList
实现一个链表。由这个类定义的链表也可以像栈或队列一样被使用。对顺序访问进行了优化，向List中间插入与删除的开销不大，随机访问则相对较慢(可用ArrayList代替)。它具有方法addFirst()、addLast()、getFirst()、getLast()、removeFirst()、removeLast()，这些方法(没有在任何接口或基类中定义过)使得LinkedList可以当作堆栈、队列和双向队列使用。
#### Vector
实现一个类似数组一样的表，自动增加容量来容纳你所需的元素。使用下标存储和检索对象就象在一个标准的数组中一样。你也可以用一个迭代器从一个Vector中检索对象。Vector是唯一的同步容器类??当两个或多个线程同时访问时也是性能良好的。（同步的含义：即同时只能一个进程访问，其他等待）
#### Stack
这个类从Vector派生而来，并且增加了方法实现栈??一种后进先出的存储结构。
### 集合(Set)
存入Set的每个元素必须是唯一的，因为Set不保存重复元素。Set判断两个对象相同不是使用==运算符，而是根据equals方法。也就是说，只要两个对象用equals方法比较返回true，Set就不会接受这两个对象。Set与Collection有完全一样的接口。Set接口不保证维护元素的次序。

#### HashSet
为快速查找而设计的Set。存入HashSet的对象必须定义hashCode()。当向HashSet结合中存入一个元素时，HashSet会调用该对象的hashCode()方法来得到该对象的hashCode值，然后根据 hashCode值来决定该对象在HashSet中存储位置。简单的说，HashSet集合判断两个元素相等的标准是两个对象通过equals方法比较相等，并且两个对象的hashCode()方法返回值相等。注意，如果要把一个对象放入HashSet中，重写该对象对应类的equals方法，也应该重写其hashCode()方法。其规则是如果两个对 象通过equals方法比较返回true时，其hashCode也应该相同。另外，对象中用作equals比较标准的属性，都应该用来计算 hashCode的值。
* 不能保证元素的排列顺序，顺序有可能发生变化
* 不是同步的
* 集合元素可以是null,但只能放入一个null

#### TreeSet
保持次序的Set，底层为树结构。使用它可以从Set中提取有序的序列。底层的数据结构是二叉树，可以对Set集合中的元素进行排序,这种结构，可以提高排序性能, 根据比较方法的返回值确定的,只要返回的是0.就代表元素重复是SortedSet接口的唯一实现类，TreeSet可以确保集合元素处于排序状态。TreeSet支持两种排序方式，自然排序 和定制排序，其中自然排序为默认的排序方式。向TreeSet中加入的应该是同一个类的对象。TreeSet判断两个对象不相等的方式是两个对象通过equals方法返回false，或者通过CompareTo方法比较没有返回0


#### LinkedHashSet
LinkedHashSet集合同样是根据元素的hashCode值来决定元素的存储位置，但是它同时使用链表维护元素的次序。这样使得元素看起 来像是以插入顺序保存的，也就是说，当遍历该集合时候，LinkedHashSet将会以元素的添加顺序访问集合的元素。LinkedHashSet在迭代访问Set中的全部元素时，性能比HashSet好，但是插入时性能稍微逊色于HashSet。具有HashSet的查询速度，且内部使用链表维护元素的顺序(插入的次序)。于是在使用迭代器遍历Set时，结果会按元素插入的次序显示。
HashSet采用散列函数对元素进行排序，这是专门为快速查询而设计的；TreeSet采用红黑树的数据结构进行排序元素；LinkedHashSet内部使用散列以加快查询速度，同时使用链表维护元素的次序，使得看起来元素是以插入的顺序保存的。需要注意的是，生成自己的类时，Set需要维护元素的存储顺序，因此要实现Comparable接口并定义compareTo()方法。
## 映射(Map)：
请注意，Map没有继承Collection接口，Map提供key到value的映射。一个Map中不能包含相同的key，每个key只能映射一个value。Map接口提供3种集合的视图，## Map的内容可以被当作一组key集合，一组value集合，或者一组key-value映射。
#### HashMap
HashMap和Hashtable类似，不同之处在于HashMap是非同步的，并且允许null，即null value和null key。，但是将HashMap视为Collection时（values()方法可返回Collection），其迭代子操作时间开销和HashMap的容量成比例。因此，如果迭代操作的性能相当重要的话，不要将HashMap的初始化容量设得过高，或者load factor过低。实现一个映象，允许存储空对象，而且允许键是空（由于键必须是唯一的，当然只能有一个）。
#### HashTable
实现一个映象，所有的键必须非空。为了能高效的工作，定义键的类必须实现hashcode()方法和equal()方法。这个类是前面java实现的一个继承，并且通常能在实现映象的其他类中更好的使用。
#### WeakHashMap
实现这样一个映象：通常如果一个键对一个对象而言不再被引用，键/对象对将被舍弃。这与HashMap形成对照，映象中的键维持键/对象对的生命周期，尽管使用映象的程序不再有对键的引用，并且因此不能检索对象。
#### TreeMap
实现这样一个映象，对象是按键升序排列的。

# 工具类
## Arrays
其实理解了集合框架也就差不多了。工具类，正如上面说过的，是对集合功能的扩展，其中大部分是static函数，一般不必实例化创建对象，而直接调用对集合进行操作。
接下来为了方便理解，以Array Arrays ArraysList
* Array：认真看api索引的话，Array有两个。一个是sql中的接口，一个是类，我们在这里说的是这个类。　　
* Arrays：对数组的一些列操作。
* ArrayList是一个容器。

## Iterator

## Comparable & Comparator
* 自然排序

自然排序使用要排序元素的CompareTo（Object obj）方法来比较元素之间大小关系，然后将元素按照升序排列。Java提供了一个Comparable接口，该接口里定义了一个compareTo(Object obj)方法，该方法返回一个整数值，实现了该接口的对象就可以比较大小。obj1.compareTo(obj2)方法如果返回0，则说明被比较的两个对象相等，如果返回一个正数，则表明obj1大于obj2，如果是 负数，则表明obj1小于obj2。如果我们将两个对象的equals方法总是返回true，则这两个对象的compareTo方法返回应该返回0
* 定制排序

自然排序是根据集合元素的大小，以升序排列，如果要定制排序，应该使用Comparator接口，实现 int compare(T o1,T o2)方法
