package com.manonline.examples.search.sample2;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Created by davidqi on 3/2/17.
 */
public class Searcher {

    public static void main(String[] args) throws IllegalArgumentException, IOException, ParseException {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: java " + Searcher.class.getName() + " <index dir> <query>");
        }

        String indexDir = args[0];
        String q = args[1];

        // 在索引库中查找制定字符串
        search(indexDir, q);
    }

    public static void search(String indexDir, String q) throws IOException, ParseException {

        // 指定索引库的位置
        try (Directory dir = FSDirectory.open(new File(indexDir))) {

            // 用索引库的位置初始化Searcher
            IndexSearcher is = new IndexSearcher(dir);

            // 构建查询解释器
            QueryParser parser = new QueryParser(Version.LATEST, "contents", new StandardAnalyzer(Version.LATEST));
            // 构建查询字符串
            Query query = parser.parse(q); // 4

            long start = System.currentTimeMillis();
            // 执行查询，并获取结果集 － 一个Document的列表
            TopDocs hits = is.search(query, 10); // 5
            long end = System.currentTimeMillis();

            System.err.println("Found " + hits.totalHits + // 6
                    " document(s) (in " + (end - start) + // 6
                    " milliseconds) that matched query '" + // 6
                    q + "':"); // 6

            // 遍历Document列表
            for (ScoreDoc scoreDoc : hits.scoreDocs) {
                Document doc = is.doc(scoreDoc.doc); // 7
                System.out.println(doc.get("fullpath")); // 8
            }

            dir.close();
        }
    }
}