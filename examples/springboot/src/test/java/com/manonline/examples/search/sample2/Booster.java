package com.manonline.examples.search.sample2;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;

/**
 * Created by davidqi on 3/2/17.
 */
public class Booster {
    public void docBoostMethod() throws IOException {

        // Get indexWriter
        Directory dir = new RAMDirectory();
        IndexWriter writer = new IndexWriter(dir, new StandardAnalyzer(Version.LATEST), IndexWriter.MaxFieldLength.UNLIMITED);

        // Get raw data
        String senderEmail = getSenderEmail();
        String senderName = getSenderName();
        String subject = getSubject();
        String body = getBody();

        // Create Document
        Document doc = new Document();

        doc.add(new Field("senderEmail", senderEmail, Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("senderName", senderName, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("subject", subject, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("body", body, Field.Store.NO, Field.Index.ANALYZED));

        // set the score/booster based on specified method
        String lowerDomain = getSenderDomain().toLowerCase();

        if (isImportant(lowerDomain)) {
            doc.setBoost(1.5F);     //1
        } else if (isUnimportant(lowerDomain)) {
            doc.setBoost(0.1F);    //2
        }
        writer.addDocument(doc);
        // END
        writer.close();

    /*
      #1 Good domain boost factor: 1.5
      #2 Bad domain boost factor: 0.1
    */
    }

    public void fieldBoostMethod() throws IOException {

        String senderName = getSenderName();
        String subject = getSubject();

        Field subjectField = new Field("subject", subject, Field.Store.YES, Field.Index.ANALYZED);

        // set the booster on specific field
        subjectField.setBoost(1.2F);

    }
}
