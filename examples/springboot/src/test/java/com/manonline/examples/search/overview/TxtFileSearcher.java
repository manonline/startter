package com.manonline.examples.search.overview;

/**
 * Created by davidqi on 3/1/17.
 */

import java.io.File;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.store.FSDirectory;

/**
 * This class is used to demonstrate the process of searching on an existing Lucene index
 * ===================================
 * Query - 这是一个抽象类，他有多个实现，比如TermQuery, BooleanQuery, PrefixQuery. 这个类的目的是把用户输入的查询字符串封装成
 * Lucene 能够识别的 Query。
 *
 * Term - Term 是搜索的基本单位，一个 Term 对象有两个String类型的域组成。生成一个 Term 对象可以有如下一条语句来完成：
 * Term term = new Term(“fieldName”,”queryWord”); 其中第一个参数代表了要在文档的哪一个 Field 上进行查找，第二个参数代表了要查
 * 询的关键词。
 *
 * TermQuery - TermQuery 是抽象类 Query 的一个子类，它同时也是 Lucene 支持的最为基本的一个查询类。生成一个TermQuery对象由如下语
 * 句完成： TermQuery termQuery = new TermQuery(new Term(“fieldName”,”queryWord”)); 它的构造函数只接受一个参数，那就是一个
 * Term 对象。
 *
 * IndexSearcher - IndexSearcher 是用来在建立好的索引上进行搜索的。它只能以只读的方式打开一个索引，所以可以有多个 IndexSearcher
 * 的实例在一个索引上进行操作
 *
 * Hits - Hits 是用来保存搜索的结果的。
 * ===================================
 */
public class TxtFileSearcher {
    public static void main(String[] args) throws Exception{
        String queryStr = "lucene";
        //This is the directory that hosts the Lucene index
        File indexDir = new File("D:\\luceneIndex");
        FSDirectory directory = FSDirectory.getDirectory(indexDir,false);
        IndexSearcher searcher = new IndexSearcher(directory);
        if(!indexDir.exists()){
            System.out.println("The Lucene index is not exist");
            return;
        }
        Term term = new Term("contents",queryStr.toLowerCase());
        TermQuery luceneQuery = new TermQuery(term);
        Hits hits = searcher.search(luceneQuery);
        for(int i = 0; i < hits.length(); i++){
            Document document = hits.doc(i);
            System.out.println("File: " + document.get("path"));
        }
    }
}