/**
 * Created by Administrator on 2017/1/31.
 */
public class Testing {
    public static void main(String args[]) {
        private static final String SH = "ShangHai";
        private static final String BJ = "BeiJing";
        private static final String TS = "TangShan";

    }
}

/**
 * Created by Administrator on 2017/1/31.
 */
public class IfNoGeneric {

    public static void main(String[] args) {
        List list = new ArrayList();
        /**
         * NOTE : all the elements will be treated as objects after being put into List container;
         */
        list.add("qqyumidi");
        list.add("corn");
        list.add(100);

        for (int i = 0; i < list.size(); i++) {
            /**
             * NOTE : element 3 is an Object was the raw type of int, it could cause java.lang.ClassCastException error,
             * when trying to convert element 3 to String,
             * DURING RUN TIME (compiler is not able to determine this problem)
             */
            String name = (String) list.get(i);
            System.out.println("name:" + name);
        }
    }
}
/**
 * Created by Administrator on 2017/1/31.
 */
public class WithGeneric {
    public static void main(String[] args) {
    /**
     * List list = new ArrayList();
     * list.add("qqyumidi");
     * list.add("corn");
     * list.add(100);
     */
        /**
         * Note : specify the container type - by specifying the TYPE of PARAMETERS, therefore, actual arguments will
         * be tested before getting put into the container. Thereafter, see the next comments.
         */
        List<String> list = new ArrayList<String>();
        list.add("qqyumidi");
        list.add("corn");
        /**
         * Note : following statement will cause complier error, sicne 1 won't pass the type test.
         */
        // list.add(1);

        for (int i = 0; i < list.size(); i++) {
            /**
             * Note : no need to try to convert the return value, since we know for sure the value is already tested.
             */
            String name = list.get(i); // 2
            System.out.println("name:" + name);
        }
    }
}

/**
 * Created by Administrator on 2017/1/31.
 */

/**
 * 使用泛型时如果不指明参数类型，即泛型类没有参数化，会提示警告，此时类型为Object。
 * 当从集合中取出时，所有的元素都是Object类型，需要进行向下的强制类型转换，转换到特定的类型。
 * 泛型的思想就是由程序员指定类型，这样集合就只能容纳该类型的元素。
 */
public class BookTest {
    public static void main(String[] args) {
        Box<String> name = new Box<String>("corn");
        System.out.println("name:" + name.getData());
    }

    class Box<T> {
        private T data;
        public Box() {
        }
        public Box(T data) {
            this.data = data;
        }
        public T getData() {
            return data;
        }
    }
}

public class GenericTest {

    public static void main(String[] args) {

        Box<String> name = new Box<String>("corn");
        Box<Integer> age = new Box<Integer>(712);

        /**
         * 由此，我们发现，在使用泛型类时，虽然传入了不同的泛型实参，但并没有真正意义上生成不同的类型，传入不同泛型实参的泛型类在内存上只有一个，即还是原来的最基本的类型（本实例中为Box），当然，在逻辑上我们可以理解成多个不同的泛型类型。

         究其原因，在于Java中的泛型这一概念提出的目的，导致其只是作用于代码编译阶段，在编译过程中，对于正确检验泛型结果后，会将泛型的相关信息擦出，也就是说，成功编译过后的class文件中是不包含任何泛型信息的。泛型信息不会进入到运行时阶段。

         对此总结成一句话：泛型类型在逻辑上看以看成是多个不同的类型，实际上都是相同的基本类型。
         */
        System.out.println("name class:" + name.getClass());      // com.qqyumidi.Box
        System.out.println("age class:" + age.getClass());        // com.qqyumidi.Box
        System.out.println(name.getClass() == age.getClass());    // true

    }

}

public class GenericTest {

    public static void main(String[] args) {

        Box<Number> name = new Box<Number>(99);
        Box<Integer> age = new Box<Integer>(712);

        getData(name);
        /**
         * Note : The method getData(Box<Number>) in the type GenericTest is not applicable
         * for the arguments (Box<Integer>); Class Hirechary doesn't lead to parent/child relationship in Generic.
         * See the next example to figure out reasons.
         */
        getData(age);

    }

    public static void getData(Box<Number> data){
        System.out.println("data :" + data.getData());
    }
}

public class GenericTest {

    public static void main(String[] args) {
        Box<Integer> a = new Box<Integer>(712);
        Box<Number> b = a;  // 1
        Box<Float> f = new Box<Float>(3.14f);
        b.setData(f);        // 2
    }

    public static void getData(Box<Number> data) {
        System.out.println("data :" + data.getData());
    }
}

class Box<T> {
    private T data;
    public Box() {
    }
    public Box(T data) {
        setData(data);
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
}

public class GenericTest {

    public static void main(String[] args) {

        Box<String> name = new Box<String>("corn");
        Box<Integer> age = new Box<Integer>(712);
        Box<Number> number = new Box<Number>(314);

        getData(name);
        getData(age);
        getData(number);
    }

    public static void getData(Box<?> data) {
        System.out.println("data :" + data.getData());
    }

}

public class GenericTest {

    public static void main(String[] args) {

        Box<String> name = new Box<String>("corn");
        Box<Integer> age = new Box<Integer>(712);
        Box<Number> number = new Box<Number>(314);

        getData(name);
        getData(age);
        getData(number);

        //getUpperNumberData(name); // 1
        getUpperNumberData(age);    // 2
        getUpperNumberData(number); // 3
    }

    public static void getData(Box<?> data) {
        System.out.println("data :" + data.getData());
    }

    public static void getUpperNumberData(Box<? extends Number> data){
        System.out.println("data :" + data.getData());
    }

}

class Father<T> {
    T data;

    public Father(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Father [data=" + data + "]";
    }

}

class Son1<T> extends Father<T> {// 最正常的继承，子类的泛型参数和父类的参数是一致的  

    public Son1(T data) {
        super(data);
    }

    @Override
    public String toString() {
        return "Son1 [data=" + data + "]";
    }

}

class Son2<E, F> extends Father<F> {// 子类增加了一个泛型参数，父类的泛型参数不能遗漏，所以仍然要定义  

    E otherData;

    public Son2(F data, E otherData) {
        super(data);
        this.otherData = otherData;
    }

    @Override
    public String toString() {
        return "Son2 [otherData=" + otherData + ", data=" + data + "]";
    }

}

class Son3 extends Father {// 继承时不指定父类的泛型参数,会有警告信息：Father is a raw type.  
    // References to generic type Father<T> should be
    // parameterized

    public Son3(Object data) {// 这个的data类型为Object  
        super(data);
    }

    @Override
    public String toString() {
        return "Son3 [data=" + data + "]";
    }

}

// 继承时指定父类的泛型参数，子类就不用再写泛型参数，如果写了，那就是子类自己新增加的  
class Son4 extends Father<Integer> {

    public Son4(Integer data) {
        super(data);
    }

    @Override
    public String toString() {
        return "Son4 [data=" + data + "]";
    }

}

// 父类指定了类型，子类又增加了，这时子类的只是新增加的泛型参数，跟父类没有关系  
class Son5<T> extends Father<Integer> {
    T otherData;

    public Son5(Integer data, T otherData) {
        super(data);
        this.otherData = otherData;
    }

    @Override
    public String toString() {
        return "Son5 [otherData=" + otherData + ", data=" + data + "]";
    }

}

// 子类的泛型参数是Integer 这个是我无意使用的，当然真实项目是绝对不允许这样使用的，一般泛型参数命名都是单个大写字母  
// 这里使用只是来说明万一泛型参数和一个类名相同了，别糊涂了（相同了都是来故意迷糊人的）  
// 这里的Integer 不是java.lang.Integer 它只是一个泛型参数名称 ，恰好相同，跟Son1是没有区别的  
// 它出现这里会把类中所有的Integer(java.lang.Integer) 都替换成 泛型参数  
// 警告提示：The type parameter Integer is hiding the type Integer  
// 所以传给父类的Integer，也不是java.lang.Integer，也只是一个类型参数  
class Son6<Integer> extends Father<Integer> {

    Integer otherData;// 它是什么类型呢？java.lang.Integer？NONONO 只能说不确定！  

    public Son6(Integer data, Integer otherData) {
        super(data);
        this.otherData = otherData;
    }

    @Override
    public String toString() {
        return "Son6 [otherData=" + otherData + ", data=" + data + "]";
    }

}
// 下面是错误的情况 父类的类型参数不明确，这会让编译器迷糊 ，让它迷糊了，就是错了！  
// class Son7 extends Father<T>{}  

// 父类和子类的泛型参数具有关系  
class Son8<T, E extends T> extends Father<T> {

    E otherData;

    public Son8(T data, E otherData) {
        super(data);
        this.otherData = otherData;
    }

    @Override
    public String toString() {
        return "Son8 [otherData=" + otherData + ", data=" + data + "]";
    }

}

// 下面的写法也是错误的，要是父类的T为Object 这时E为什么呢？  
// class Son9<E, E super T> extends Father<T> {  
//  
// public Son9(T data) {  
// super(data);  
// }  
// }  
