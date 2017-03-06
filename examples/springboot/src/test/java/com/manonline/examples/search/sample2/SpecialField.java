package com.manonline.examples.search.sample2;

import org.apache.lucene.document.Document;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by davidqi on 3/2/17.
 * 你要搜索的是价格信息，需要的是一个精度的搜索，有时候你要搜索一个长度的范围或者接收信息的日期等信息，这些信息通常都是默认被索引成数字，
 * 也就是说你可能不能找到你想要匹配的结果，这时候就需要做一些单独的的处理，在我们加入Field的时候。
 */
public class SpecialField {
    public void numberField() {
        Document doc = new Document();
        doc.add(new NumericField("price").setDoubleValue(19.99));
    }

    public void numberTimestamp() {
        Document doc = new Document();

        doc.add(new NumericField("timestamp").setLongValue(new Date().getTime()));

        doc.add(new NumericField("day").setIntValue((int) (new Date().getTime()/24/3600)));

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        doc.add(new NumericField("dayOfMonth").setIntValue(cal.get(Calendar.DAY_OF_MONTH)));

    }

}
