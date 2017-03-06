package com.manonline.examples.spell.autocomplete;

import java.util.Set;
/**
 * Created by davidqi on 3/4/17.
 */

public interface PrefixMatcher {
    Set<String> obtainMatchedWords(String inputText);
}