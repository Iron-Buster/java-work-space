package com.fqh;

import com.fqh.data_structure.Trie;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/2/21 16:52
 **/
public class Test {

    public static void main(String[] args) {
        Trie trie = new Trie();
        Map<String, Integer> map = new HashMap<>();
        trie.initTrie(map);
    }
}
