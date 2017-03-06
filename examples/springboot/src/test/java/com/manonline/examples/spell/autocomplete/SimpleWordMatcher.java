package com.manonline.examples.spell.autocomplete;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by davidqi on 3/4/17.
 * http://www.cnblogs.com/lovesqcc/p/4037708.html
 */

public class SimpleWordMatcher extends AbstractPrefixMatcher {

    public SimpleWordMatcher() {
        prefixMatchers = buildPrefixMatchers(javaKeywords);
    }

    /**
     * 将输入的单词组转化为前缀匹配的映射
     * {"abc", "acd", "bcd"} ===>
     * {
     *  "a": ["abc", "acd"], "ab": ["abc"], "abc": ["abc"],
     *  "ac": ["acd"], "acd": ["acd"],
     *  "b": ["bcd"], "bc": ["bcd"], "bcd": ["bcd"]
     * }
     */
    public Map<String, Set<String>> buildPrefixMatchers(String[] keywords) {

        HashMap<String, Set<String>> prefixMatchers = new HashMap<String, Set<String>>();
        // 遍历所有的单词
        for (String keyword : keywords) {
            for (int i = 1; i < keyword.length(); i++) {
                // 截取单词的前缀
                String prefix = keyword.substring(0, i);
                // 再次遍历所有的单词
                for (String keyword2 : javaKeywords) {
                    // 如果某个单词是以当前前缀开始
                    if (keyword2.startsWith(prefix)) {
                        // 如果当前前缀还未被添加到字典中,则添加当前前缀
                        Set<String> matchers = prefixMatchers.get(prefix);
                        if (matchers == null) {
                            matchers = new HashSet<String>();
                        }
                        // 将Keywords当前前缀集合中
                        matchers.add(keyword2);
                        prefixMatchers.put(prefix, matchers);
                    }
                }
            }
        }

        return prefixMatchers;
    }

    public static void main(String[] args) {
        SimpleWordMatcher wordMatcher = new SimpleWordMatcher();
        MapUtil.printMap(wordMatcher.obtainPrefixMatchers());
        String[] prefixes = new String[]{"a", "b", "c", "d", "e", "f", "g", "i",
                "l", "n", "p", "r", "s", "t", "v", "w", "do", "finally"};
        for (String prefix : prefixes) {
            System.out.println(wordMatcher.obtainMatchedWords(prefix));
        }
    }

    @Override
    void dynamicAddNew(String inputText) {
    }

}


