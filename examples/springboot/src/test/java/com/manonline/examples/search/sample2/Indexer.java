package com.manonline.examples.search.sample2;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by davidqi on 3/2/17.
 * http://www.cnblogs.com/skyme/archive/2012/07/30/2615054.html
 */
public class Indexer {

    private IndexWriter writer;

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            throw new IllegalArgumentException("Usage: java "
                    + Indexer.class.getName() + " <index dir> <data dir>");
        }

        String indexDir = args[0];
        String dataDir = args[1];

        long start = System.currentTimeMillis();

        Indexer indexer = new Indexer(indexDir);
        int numIndexed;

        try {
            numIndexed = indexer.index(dataDir, new TextFilesFilter());
        } finally {
            indexer.close();
        }

        long end = System.currentTimeMillis();

        System.out.println("Indexing " + numIndexed + " files took "
                + (end - start) + " milliseconds");
    }

    // create one document based on the input file
    protected Document getDocument(File f) throws Exception {
        Document doc = new Document();
        doc.add(new Field("contents", new FileReader(f)));
        doc.add(new Field("filename", f.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("fullpath", f.getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        return doc;
    }

    // create indexWriter
    public Indexer(String indexDir) throws IOException {
        try (Directory dir = FSDirectory.open(new File(indexDir))) {
            writer = new IndexWriter(dir, new StandardAnalyzer(Version.LATEST),
                                     true, IndexWriter.MAX_TERM_LENGTH);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    // index the document (add document to index lib) using the existing indexWriter
    public int index(String dataDir, FileFilter filter) throws Exception {

        File[] files = new File(dataDir).listFiles();

        for (File f : files) {
            if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead()
                    && (filter == null || filter.accept(f))) {
                indexFile(f);
            }
        }

        return writer.numDocs(); // 5
    }

    private void indexFile(File f) throws Exception {
        System.out.println("Indexing " + f.getCanonicalPath());
        Document doc = getDocument(f);
        writer.addDocument(doc); // 10
    }


    public void close() throws IOException {
        writer.close(); // 4
    }

    private static class TextFilesFilter implements FileFilter {
        public boolean accept(File path) {
            return path.getName().toLowerCase().endsWith(".txt"); // 6
        }
    }

}

# of boys wearing pants = U * P(Boy) * P(Pants|Boy)
# of girls wearing pants = U * P(Girl) * P(Pants|Girl)
# of people wearing pants = [U * P(Boy) * P(Pants|Boy) + U * P(Girl) * P(Pants|Girl)]

        P(Girl|Pants)      = P(Girl) * P(Pants|Girl) / [P(Boy) * P(Pants|Boy) + P(Girl) * P(Pants|Girl)]
P(Girl|Pants) = P(Girl) * P(Pants|Girl) / P(Pants)
