package com.fqh.data_structure;

/**
 * @author ikun
 * @version v1.0.0
 * @since 2024/2/21 16:28
 **/

import java.util.*;

/**
 * Trie模板
 */
public class Trie {
    private static final int MAX_KTH = Integer.MAX_VALUE / 2;
    private static final int K = 5;
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
        initKthMostNodeQueue();
        System.out.println("------------------ 初始化完成 ------------------");
    }

    /**
     * 通过传入的前缀，返回前k个高频匹配词
     * @param prefix 前缀
     * @return
     */
    public String[] findTopKthWords(String prefix) {
        // limit the prefix length
        if (prefix.length() > K) {
            return new String[0];
        }
        Node node = search(prefix);
        if (node == null) {
            return new String[0];
        }
        PriorityQueue<Node> pq = node.kthMostNodeQueue;
        int n = pq.size();
        String[] result = new String[n];
        // reverse fill the result
        for (int i = n - 1; i >= 0; i--) {
            result[i] = pq.poll().endWord;
        }
        return result;
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

    /**
     * 搜索一个前缀
     * @param prefix    前缀
     * @return
     */
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


    /**
     * 初始化每个结点的缓存队列
     */
    private void initKthMostNodeQueue() {
        dfs(root);
    }

    /**
     * 利用后续遍历，初始化每个结点的缓存队列
     * @param root
     * @return
     */
    private PriorityQueue<Node> dfs(Node root) {
        PriorityQueue<Node> pq = new PriorityQueue<>(((o1, o2) -> o1.freq - o2.freq));
        for (Node next : root.son) {
            if (next == null) {
                continue;
            }
            PriorityQueue<Node> subPq = dfs(next);
            // count sub result
            for (Node node : subPq) {
                pq.offer(node);
            }
        }
        if (root.isEnd) {
            pq.offer(root);
        }
        while (pq.size() > K) {
            pq.poll();
        }
        // save current cache
        root.setKthMostNodeQueue(pq);
        return pq;
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
            this.kthMostNodeQueue = new PriorityQueue<>(((o1, o2) -> o1.freq - o2.freq));
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
