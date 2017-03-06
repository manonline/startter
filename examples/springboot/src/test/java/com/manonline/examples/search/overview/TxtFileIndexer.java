package com.manonline.examples.search.overview;

/**
 * Created by davidqi on 3/1/17.
 */

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

/**
 * This class demonstrate the process of creating index with Lucene for text files
 * http://www.open-open.com/lib/view/open1348219378948.html
 * ===================================
 * 索引是现代搜索引擎的核心，建立索引的过程就是把源数据处理成非常方便查询的索引文件的过程。为什么索引这么重要呢，试想你现在要在大量的
 * 文档中搜索含有某个关键词的文档，那么如果不建立索引的话你就需要把这些文档顺序的读入内存，然后检查这个文章中是不是含有要查找的关键词，
 * 这样的话就会耗费非常多的时间。Lucene 采用的是一种称为反向索引（inverted index）的机制。反向索引就是说我们维护了一个词 / 短语表，
 * 对于这个表中的每个词 / 短语，都有一个链表描述了有哪些文档包含了这个词 / 短语。也即 Word -> File, 而不是File -> Word
 *
 * 全文检索 ：计算机索引程序通过扫描文章中的每一个词，对每一个词建立一个索引，指明改词在文章中出现的频次和位置。当用户查询时，检索程序
 * ===================================
 * Document - Document 是用来描述文档的，这里的文档可以指一个 HTML 页面，一封电子邮件，或者是一个文本文件。一个Document对象由多个
 * Field 对象组成的。可以把一个 Document 对象想象成数据库中的一个记录，而每个 Field 对象就是记录的一个字段。
 *
 * Field - Field 对象是用来描述一个文档的某个属性的，比如一封电子邮件的标题和内容可以用两个 Field 对象分别描述。
 *
 * Analyzer - 在一个文档被索引之前，首先需要对文档内容进行分词处理，这部分工作就是由 Analyzer 来做的。Analyzer 类是一个抽象类，它
 * 有多个实现。针对不同的语言和应用需要选择适合的 Analyzer。Analyzer 把分词后的内容交给 IndexWriter 来建立索引。
 *
 * IndexWriter - IndexWriter 是 Lucene 用来创建索引的一个核心的类，他的作用是把一个个的 Document 对象加到索引中来。
 *
 * Directory - 这个类代表了 Lucene 的索引的存储的位置，这是一个抽象类，它目前有两个实现，第一个是 FSDirectory，它表示一个存储在文件
 * 系统中的索引的位置。第二个是 RAMDirectory，它表示一个存储在内存当中的索引的位置。
 * ===================================
 *
 */
public class TxtFileIndexer {
    public static void main(String[] args) throws Exception {
        // 索引存放的位置
        File indexDir = new File("D:\\luceneIndex");

        // 需要被索引的文档
        File dataDir = new File("D:\\luceneData");
        File[] dataFiles = dataDir.listFiles();

        // 定义分词器
        Analyzer luceneAnalyzer = new StandardAnalyzer();

        // 定义索引生成器 －
        // 第一个参数指定了所创建的索引要存放的位置，他可以是一个 File 对象，也可以是一个 FSDirectory对象或者RAMDirectory 对象。
        // 第二个参数指定了 Analyzer 类的一个实现，也就是指定这个索引是用哪个分词器对文挡内容进行分词。
        // 第三个参数是一个布尔型的变量，如果为 true 的话就代表创建一个新的索引，为 false 的话就代表在原来索引的基础上进行操作。
        IndexWriter indexWriter = new IndexWriter(indexDir, luceneAnalyzer, true);

        long startTime = new Date().getTime();
        for (int i = 0; i < dataFiles.length; i++) {
            if (dataFiles[i].isFile() && dataFiles[i].getName().endsWith(".txt")) {
                System.out.println("Indexing file " + dataFiles[i].getCanonicalPath());

                // 获取源文件内容
                Reader txtReader = new FileReader(dataFiles[i]);

                // 定义目标文档格式 - 一个文档相当于一行，一个Field相当于一列；Document是用来描述文档的，这里的文档可以指一个HTML页面，
                // 一封电子邮件，或者是一个文本文件。
                // 接着程序遍历了目录下面的所有文本文档，并为每一个文本文档创建了一个 Document 对象。然后把文本文档的两个属性：路径和
                // 内容加入到了两个 Field 对象中，接着在把这两个 Field 对象加入到 Document 对象中，最后把这个文档用IndexWriter类的
                // add 方法加入到索引中去。这样我们便完成了索引的创建。接下来我们进入在建立好的索引上进行搜索的部分。
                Document document = new Document();
                document.add(Field.Text("path", dataFiles[i].getCanonicalPath()));
                document.add(Field.Text("contents", txtReader));

                // 加索引
                indexWriter.addDocument(document);
            }
        }

        indexWriter.optimize();
        indexWriter.close();
        long endTime = new Date().getTime();

        System.out.println("It takes " + (endTime - startTime)
                + " milliseconds to create index for the files in directory "
                + dataDir.getPath());
    }
}