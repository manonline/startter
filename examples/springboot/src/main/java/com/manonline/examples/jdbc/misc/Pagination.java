package com.manonline.examples.jdbc.misc;

import com.manonline.examples.jdbc.utils.JdbcUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by davidqi on 2/11/17.
 * ResultSet中实现的pagination效率较低，因为要把数据加载到内存。尽量使用数据库的pagination, 也即
 * select * from abc limit 150, 50
 */
public class Pagination {
    static void test() throws Exception {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            //结果集可滚动的
            /**
             * 参数的含义：
             * ResultSet.RTYPE_FORWORD_ONLY：这是缺省值，只可向前滚动；
             * ResultSet.TYPE_SCROLL_INSENSITIVE：双向滚动，但不及时更新，就是如果数据库里的数据修改过，并不在ResultSet中反应出来。
             * ResultSet.TYPE_SCROLL_SENSITIVE：双向滚动，并及时跟踪数据库的更新,以便更改ResultSet中的数据。
             *
             * ResultSet.CONCUR_READ_ONLY：这是缺省值，指定不可以更新 ResultSet
             * ResultSet.CONCUR_UPDATABLE：指定可以更新 ResultSet
             */
            st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = st.executeQuery("select id,name,money,birthday from user");

            /**
             * 开始的时候这个游标的位置是第一条记录之前的一个位置。当执行rs.next的时候这个游标的位置就到第一条记录了
             */
            while (rs.next()) {
                System.out.println("id=" + rs.getInt("id") + "\t" +
                        "name=" + rs.getString("name") + "\t" +
                        "birthday=" + rs.getDate("birthday") + "\t" +
                        "money=" + rs.getFloat("money"));
            }
            /**
             * 上面的代码执行之后，这个游标就到最后一条记录的下一个位置了。所以这里在调用previous方法之后，这个游标就回到了最后一条记录
             * 中，所以打印了最后一条记录的值
             */
            if (rs.previous()) {
                System.out.println("id=" + rs.getInt("id") + "\t" +
                        "name=" + rs.getString("name") + "\t" +
                        "birthday=" + rs.getDate("birthday") + "\t" +
                        "money=" + rs.getFloat("money"));
            }

            /**
             * 绝对定位到第几行结果集
             * 这里传递的参数的下标是从1开始的，比如这里查询出来的记录有3条，那么这里的参数的范围是:1-3,如果传递的参数不在这个范围内就会
             * 报告异常的
             */
            rs.absolute(2);
            System.out.println("id=" + rs.getInt("id") + "\tname=" + rs.getString("name") + "\tbirthday=" + rs.getDate("birthday") + "\tmoney=" + rs.getFloat("money"));

            /**
             * 滚到到第一行的前面(默认的就是这种情况)
             */
            rs.beforeFirst();

            /**
             * 滚动到最后一行的后面
             */
            rs.afterLast();
            /**
             * 判断当前位置
             */
            rs.isFirst();//判断是不是在第一行记录
            rs.isLast();//判断是不是在最后一行记录
            rs.isAfterLast();//判断是不是第一行前面的位置
            rs.isBeforeFirst();//判断是不是最后一行的后面的位置

            /**
             * 以上的api实现的分页功能是针对于那些本身不支持分页查询功能的数据库的，如果一个数据库支持分页功能，上面的代码就不能使用的，
             * 因为效率是很低的。以上的api可以实现翻页的效果(这个效率很低的，因为是先把数据都查询到内存中，然后再进行分页显示的)
             * 效率高的话是直接使用数据库中的分页查询语句：
             * select * from user limit 150,10;
             */
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.free(rs, st, conn);
        }
    }

    static void test2() throws Exception {
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            conn = JdbcUtils.getConnection();
            /**
             * 第三个字段的含义是，在读取数据的时候(已经返回了结果集到内存中了)，再去修改结果集中的数据，这时候数据库中的数据就可以感知到
             * 结果集中的变化了进行修改。这种操作是不可取的，因为查询和更新交互在一起，逻辑就乱了，只有在特定的场合中使用
             */
            st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = st.executeQuery("select * from user");
            while (rs.next()) {
                /**
                 * 这里我们获取到name列的值，如果是lisi我们就将结果集中的他的记录中的money变成170，然后再更行行信息，这时候数据库中的
                 * 这条记录的值也发生变化了，内存中的结果集中的记录的值发生改变了，影响到了数据库中的值.
                 */
                String name = rs.getString("name");
                if ("jiangwei".equals(name)) {
                    rs.updateFloat("money", 170);
                    rs.updateRow();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.free(rs, st, conn);
        }
    }
}
