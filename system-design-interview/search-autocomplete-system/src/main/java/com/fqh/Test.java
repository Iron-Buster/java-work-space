package com.fqh;

import com.fqh.data_structure.Trie;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
        map.put("be", 15);
        map.put("bet", 29);
        map.put("bee", 20);
        map.put("beer", 10);
        map.put("best", 35);
        map.put("buy", 14);
        trie.initTrie(map);
        String[] res1 = trie.findTopKthWords("b");
        String[] res2 = trie.findTopKthWords("be");
        String[] res3 = trie.findTopKthWords("bee");
        String[] res4 = trie.findTopKthWords("bu");
        System.out.println(Arrays.toString(res1));
        System.out.println(Arrays.toString(res2));
        System.out.println(Arrays.toString(res3));
        System.out.println(Arrays.toString(res4));
    }
}
