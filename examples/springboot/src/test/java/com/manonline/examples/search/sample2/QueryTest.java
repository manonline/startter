package com.manonline.examples.search.sample2;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;

import static org.junit.Assert.assertEquals;

/**
 * Created by davidqi on 3/2/17.
 */
public class QueryTest {

    // 查询单个的词
    public void testTerm() throws Exception {

        // create searcher
        Directory dir = TestUtil.getBookIndexDirectory();
        IndexSearcher searcher = new IndexSearcher(dir);

        // 指定term和目标field
        Term t = new Term("subject", "ant");
        // 构造查询
        Query query = new TermQuery(t);
        // 执行查询，获取结果
        TopDocs docs = searcher.search(query, 10);
        assertEquals("Ant in Action", 1, docs.totalHits);

        t = new Term("subject", "junit");
        query = new TermQuery(t);
        docs = searcher.search(query, 10);
        assertEquals("Ant in Action, " + "JUnit in Action, Second Edition", 2, docs.totalHits);

        dir.close();
    }

    // 复杂查询，需要解析器
    public void testQueryParser() throws Exception {
        Directory dir = TestUtil.getBookIndexDirectory();
        IndexSearcher searcher = new IndexSearcher(dir);

        // 创建查询解析器
        QueryParser parser = new QueryParser(Version.LUCENE_30, "contents", new SimpleAnalyzer());
        // 解析输入，并构建查询
        Query query = parser.parse("+JUNIT +ANT -MOCK");
        // 执行查询
        TopDocs docs = searcher.search(query, 10);
        assertEquals(1, docs.totalHits);

        Document d = searcher.doc(docs.scoreDocs[0].doc);
        assertEquals("Ant in Action", d.get("title"));

        query = parser.parse("mock OR junit");
        docs = searcher.search(query, 10);
        assertEquals("Ant in Action, " + "JUnit in Action, Second Edition", 2, docs.totalHits);

        searcher.close();
        dir.close();
    }
}
