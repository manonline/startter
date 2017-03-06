package com.manonline.examples.spell.correction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by davidqi on 3/3/17.
 * http://www.cnblogs.com/luxiaoxun/p/4099567.html
 * http://norvig.com/spell-correct.html
 * ===========================================================
 * 给定一个单词，选择和它最相似的拼写正确的单词，需要使用概率论，而不是基于规则的判断。给定一个词 w，在所有正确的拼写词中，我们想要找一个正
 * 确的词 c, 使得对于 w 的条件概率最大, 也就是说：argmaxc P(c|w)
 *
 * 按照 贝叶斯理论 上面的式子等价于: argmaxc P(w|c) P(c) / P(w) 因为用户可以输错任何词, 因此对于任何 c 来讲, 出现 w 的概率 P(w) 都是
 * 一样的, 从而我们在上式中忽略它, 写成: argmaxc P(w|c) P(c)
 *
 * 这个式子有三个部分, 从右到左, 分别是:
 * 1. P(c), 文章中出现一个正确拼写词 c 的概率, 也就是说, 在英语文章中, c 出现的概率有多大呢? 因为这个概率完全由英语这种语言决定, 我们称
 *    之为做语言模型. 好比说, 英语中出现 the 的概率  P('the') 就相对高, 而出现  P('zxzxzxzyy') 的概率接近0(假设后者也是一个词的话).
 * 2. P(w|c), 在用户想键入 c 的情况下敲成 w 的概率. 因为这个是代表用户会以多大的概率把 c 敲错成 w, 因此这个被称为误差模型.
 * 3. argmaxc, 用来枚举所有可能的 c 并且选取概率最大的, 因为我们有理由相信, 一个(正确的)单词出现的频率高, 用户又容易把它敲成另一个错误的
 *    单词, 那么, 那个敲错的单词应该被更正为这个正确的.
 * ===========================================================
 * 最终计算的就是argmaxc P(w|c) P(c)，在原文中，以“编辑距离”的大小来确定求最大概率的优先级： 编辑距离为1的正确单词比编辑距离为2的优先级
 * 高, 而编辑距离为0的正确单词优先级比编辑距离为1的高。
 *
 * 可以理解为 ：
 * P(c) = # of Occurance(c) / total # of words; 因为，total # of words是常量(对应一个确定的库)，所以可以不用计算；
 * P(w|c) : 此例中为"编辑距离"，所有编辑距离相同的字符串组合具有相同的 P(w|c), 不同的编辑距离，优先级则不一样；
 * 此算法中，只有会在P(w|c)，相同的情况下才会用P(c)，并没有考虑，小的P(w|c) 乘以 大的P(c) 可能会比 大的P(w|c) 乘以 小的P(c) 大；所以，
 * 此处实际上面并没有做相乘操作。
 *
 * max(P(w|c) * P(c)) :
 * P(c) : language model : 语言模型，单词在字典库的出现概率 = 单词个数 ／ 字典中总的单词数量；
 * P(w|c) : error model : 错误模型，用户想输入单词c, 却输入了单词d的概率 = ???；
 * Candidate Model : 候选模型，对多少正确单词进行评估；
 * max : 选择机制，此处为选择最大可能性；
 *
 */
public class SpellCorrect {

    private final HashMap<String, Integer> nWords = new HashMap<String, Integer>();

    public static void main(String args[]) throws IOException {
        SpellCorrect spellCorrect = new SpellCorrect("big.txt");
        System.out.println(spellCorrect.correct("spel"));
        System.out.println(spellCorrect.correct("speling"));
    }

    /**
     * 加载资料库 : 从硬盘library到内存nWords
     */
    public SpellCorrect(String library) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(library));
        // 单词匹配器
        Pattern p = Pattern.compile("\\w+");
        // 对资料库进行行扫描
        for (String temp = ""; temp != null; temp = in.readLine()) {
            // 获得行上面的所有单词
            Matcher m = p.matcher(temp.toLowerCase());
            while (m.find())
                // 如果不存在，单词计数为一，如果存在，单词技术加一；
                nWords.put((temp = m.group()), nWords.containsKey(temp) ? nWords.get(temp) + 1 : 1);
        }
        in.close();
    }

    /**
     * 对输入的单词进行纠正
     */
    public final String correct(String word) {
        // 如果在语料库中，直接返回
        if (nWords.containsKey(word))
            return word;

        HashSet<String> possibleCombinations = edits(word);
        HashMap<Integer, String> candidates = new HashMap<Integer, String>();

        // 如果没找到 ： 遍历所有的编辑距离为一的字符组合
        for (String s : possibleCombinations)
            // 如果字典中包含字符组合，则加入结果集 <字典中的权重，单词>
            if (nWords.containsKey(s))
                candidates.put(nWords.get(s), s);
        if (candidates.size() > 0)
            // 返回权重最大的单词；
            return candidates.get(Collections.max(candidates.keySet()));

        // 如果没找到 ： 遍历所有的编辑距离为二的字符组合
        for (String s : possibleCombinations)
            for (String w : edits(s))
                if (nWords.containsKey(w))
                    candidates.put(nWords.get(w), w);
        if (candidates.size() > 0) {
            return candidates.get(Collections.max(candidates.keySet()))
        }

        // 如果没找到 ：返回源字符串
        return word;
    }

    /**
     * 获得和输入单词，编辑距离为一的单词集合
     */
    private final HashSet<String> edits(String word) {
        HashSet<String> result = new HashSet<>();

        // 给定"单词"(字符序列)，删除一个字母后的"单词"(字符序列)集合；
        for (int i = 0; i < word.length(); ++i)
            result.add(word.substring(0, i) + word.substring(i + 1));

        // 给定"单词"(字符序列)，交换一个字母后的"单词"(字符序列)集合；
        for (int i = 0; i < word.length() - 1; ++i) //交换
            result.add(word.substring(0, i) + word.substring(i + 1, i + 2) + word.substring(i, i + 1) + word.substring(i + 2));

        // 给定"单词"(字符序列)，替换一个字母后的"单词"(字符序列)集合；
        for (int i = 0; i < word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c)
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i + 1));
        }

        // 给定"单词"(字符序列)，插入一个字母后的"单词"(字符序列)集合；
        for (int i = 0; i <= word.length(); ++i) {
            for (char c = 'a'; c <= 'z'; ++c)
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
        }

        return result;
    }



}