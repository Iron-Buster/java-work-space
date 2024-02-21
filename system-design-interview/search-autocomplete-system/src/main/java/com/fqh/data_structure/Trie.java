package com.fqh.data_structure;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/2/21 16:28
 **/

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Trie模板
 */
public class Trie {
    private static final int MAX_KTH = Integer.MAX_VALUE / 2;
    private final Node root;

    public Trie() {
        this.root = new Node();
    }

    /**
     * 初始化Trie
     * @param wordMap 单词表
     */
    public void initTrie(Map<String, Integer> wordMap) {
        System.out.println("------------------ 初始化Trie ------------------");
        wordMap.forEach(this::insert);
        System.out.println("------------------ 初始化完成 ------------------");
    }

    /**
     * 通过传入的前缀，返回前k个高频匹配词
     * @param prefix 前缀
     * @param k      ktH
     * @return
     */
    public List<String> findTopKthWords(String prefix, int k) {
        if (k > MAX_KTH) {
            throw new IllegalArgumentException("k must less than Integer.MAX_VALUE / 2");
        }
        if (k == -1) {

        }

        return null;
    }

    /**
     * 插入一个单词
     * @param word  单词
     * @param freq  词频
     */
    private void insert(String word, int freq) {
        Node cur = root;
        int n = word.length();
        for (int i = 0; i < n; i++) {
            int idx = word.charAt(i) - 'a';
            if (cur.son[idx] == null) {
                cur.son[idx] = new Node();
            }
            cur = cur.son[idx];
        }
        cur.endWord = word;
        cur.isEnd = true;
        cur.freq = freq;
    }



    private Node search(String prefix) {
        Node cur = root;
        int n = prefix.length();
        for (int i = 0; i < n; i++) {
            int idx = prefix.charAt(i) - 'a';
            if (cur.son[idx] == null) {
                return null;
            }
            cur = cur.son[idx];
        }
        return cur;
    }


    static class Node {
        /**
         * 是否标记结束
         */
        private boolean isEnd;
        /**
         * 子节点
         */
        private Node[] son;
        /**
         * 词频
         */
        private int freq;
        /**
         * 结束词
         */
        private String endWord;

        /**
         * 优先队列维护前k个最常使用的查询
         */
        private PriorityQueue<Node> kthMostNodeQueue;

        public Node() {
            this.son = new Node[26];
            this.isEnd = false;
            this.freq = 0;
            this.endWord = "";
            this.kthMostNodeQueue = new PriorityQueue<>(((o1, o2) -> o2.freq - o1.freq));
        }

        public boolean isEnd() {
            return isEnd;
        }

        public void setEnd(boolean end) {
            isEnd = end;
        }

        public Node[] getSon() {
            return son;
        }

        public void setSon(Node[] son) {
            this.son = son;
        }

        public int getFreq() {
            return freq;
        }

        public void setFreq(int freq) {
            this.freq = freq;
        }

        public String getEndWord() {
            return endWord;
        }

        public void setEndWord(String endWord) {
            this.endWord = endWord;
        }

        public PriorityQueue<Node> getKthMostNodeQueue() {
            return kthMostNodeQueue;
        }

        public void setKthMostNodeQueue(PriorityQueue<Node> kthMostNodeQueue) {
            this.kthMostNodeQueue = kthMostNodeQueue;
        }
    }
}
