package com.manonline.examples.search.sample2;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.NumericUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by davidqi on 3/2/17.
 */
public class TestPagation {

    public void paginationQuery(String keyWord, int pageSize, int currentPage) throws ParseException, CorruptIndexException, IOException {
        String[] fields = { "title", "content" };
        // 创建一个分词器,和创建索引时用的分词器要一致
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);

        QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_36, fields, analyzer);
        Query query = queryParser.parse(keyWord);

        // 打开索引目录
        File indexDir = new File("./indexDir");
        Directory directory = FSDirectory.open(indexDir);

        IndexSearcher indexSearcher;
        TopDocs topDocs;
        ScoreDoc[] scoreDocs;
        int begin;
        int end;
        try (IndexReader indexReader = IndexReader.open(directory)) {
            indexSearcher = new IndexSearcher(indexReader);
        }

        // TopDocs 搜索返回的结果
        topDocs = indexSearcher.search(query, 100);
        int totalCount = topDocs.totalHits; // 搜索结果总数量
        scoreDocs = topDocs.scoreDocs;

        // 查询起始记录位置
        begin = pageSize * (currentPage - 1);
        // 查询终止记录位置
        end = Math.min(begin + pageSize, scoreDocs.length);

        // 进行分页查询
        for (int i = begin; i < end; i++) {
            int docID = scoreDocs[i].doc;
            Document doc = indexSearcher.doc(docID);
            int id = NumericUtils.prefixCodedToInt(doc.get("id"));
            String title = doc.get("title");
            System.out.println("id is : " + id);
            System.out.println("title is : " + title);
        }

    }

    public static void main(String[] args) throws CorruptIndexException, ParseException, IOException {
        TestPagation t = new TestPagation();
        //每页显示5条记录，显示第三页的记录
        t.paginationQuery("RUNNING",5,3);
    }

}