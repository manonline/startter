package com.manonline.examples.spell.autocomplete;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * Created by davidqi on 3/4/17.
 */

public class EffectiveWordMatcher extends AbstractPrefixMatcher {

    public EffectiveWordMatcher() {
        prefixMatchers = buildPrefixMatchers(javaKeywords);
    }

    static class Pair {
        private String key;
        private String value;
        public Pair(String key, String value) {
            this.key = key;
            this.value = value;
        }
        public String getKey() {
            return key;
        }
        public String getValue() {
            return value;
        }
        public String toString() {
            return "<" + key + "," + value + ">";
        }
    }

    private Map<String, Set<String>> buildPrefixMatchers(String[] javakeywords) {
        List<Pair> pairs = strarr2pairs(javakeywords);
        return mergePairs(pairs);
    }

    /*
     * 将 字符串数组转化为前缀匹配对
     * eg. ["ab", "ac"] ===>
     *     [<"a","ab">, <"ab", "ab">, <"a", "ac">, <"ac", "ac">]
     */
    private List<Pair> strarr2pairs(String[] javakeywords) {
        List<Pair> pairs = new ArrayList<Pair>();
        for (String keyword: javakeywords) {
            int wordLen = keyword.length();
            for (int i=1; i < wordLen; i++) {
                String prefix = keyword.substring(0, i);
                Pair pair = new Pair(prefix, keyword);
                pairs.add(pair);
            }
        }
        return pairs;
    }

    /*
     * 将多个 <key,value> 合并为一个映射
     * eg. [<"a", "abstract">, <"b", "boolean">, <"a", "assert">, <"b", "break">, <"c", "continue">] ===>
     *     {"a"=>["abstract", "assert", "b"=>["boolean", "break"], "c"=>["continue"]}
     */
    private static Map<String, Set<String>> mergePairs(List<Pair> pairs) {
        Map<String, Set<String>> result = new HashMap<String, Set<String>>();
        if (pairs != null && pairs.size() > 0) {
            for (Pair pair: pairs) {
                String key = pair.getKey();
                String value = pair.getValue();
                Set<String> matchers = result.get(key);
                if (matchers == null) {
                    matchers = new HashSet<String>();
                }
                matchers.add(value);
                result.put(key, matchers);
            }
        }
        return result;
    }

    @Override
    void dynamicAddNew(String inputText) {
        if (checkValid(inputText)) {
            List<Pair> newpairs = strarr2pairs(new String[] {inputText});
            Map<String, Set<String>> newPreixMatchers = mergePairs(newpairs);
            mergeMap(newPreixMatchers, prefixMatchers);
        }
    }

    private boolean checkValid(String inputText) {
        return false;
    }

    private Map<String, Set<String>> mergeMap(Map<String, Set<String>> src, Map<String, Set<String>> dest) {
        Set<Map.Entry<String, Set<String>>> mapEntries = src.entrySet();
        Iterator<Map.Entry<String, Set<String>>> iter = mapEntries.iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Set<String>> entry = iter.next();
            String key = entry.getKey();
            Set<String> newMatchers = entry.getValue();
            if (dest.containsKey(key)) {
                dest.get(key).addAll(newMatchers);
            }
            else {
                dest.put(key, newMatchers);
            }
        }
        return dest;
    }

    public static void main(String[] args) {
        EffectiveWordMatcher wordMatcher = new EffectiveWordMatcher();
        MapUtil.printMap(wordMatcher.obtainPrefixMatchers());
        String[] prefixes = new String[] {"a", "b", "c", "d", "e", "f", "g", "i",
                "l", "n", "p", "r", "s", "t", "v", "w", "do", "finally"};
        for (String prefix: prefixes) {
            System.out.println(wordMatcher.obtainMatchedWords(prefix));
        }
    }

}
