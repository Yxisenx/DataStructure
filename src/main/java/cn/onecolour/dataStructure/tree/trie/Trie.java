package cn.onecolour.dataStructure.tree.trie;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author yang
 * @date 2022/10/24
 * @description prefix tree
 */
public class Trie {

    private final Node root;
    private int count = 0;

    /**
     * <p>Trie，又称字典树、单词查找树或键树，是一种树形结构，是一种哈希树的变种。</p>
     * <p>典型应用是用于统计，排序和保存大量的字符串（但不仅限于字符串），</p>
     * <p>所以经常被搜索引擎系统用于文本词频统计。</p>
     * <p>它的优点是：利用字符串的公共前缀来减少查询时间，最大限度地减少无谓的字符串比较，查询效率比哈希树高。</p>
     */
    public Trie() {
        root = new Node(false, '\'');
    }

    /**
     * Add element to trie.
     */
    public void add(String ele) {

        if (Objects.isNull(ele) || ele.length() == 0) {
            return;
        }
        char[] chars = ele.toCharArray();
        Node temp = root;
        for (char character : chars) {
            Node next = temp.getNext(character);
            if (next == null) {
                next = temp.addNext(character);
            }
            temp = next;
        }
        if (!temp.isEnding()) {
            temp.ending = true;
            count++;
        }

    }

    /**
     * Remove element if it exists in trie.
     */
    public void remove(String ele) {

        if (Objects.isNull(ele) || ele.length() == 0) {
            return;
        }
        char[] chars = ele.toCharArray();
        Node temp = root;
        Node previous = null;
        for (int i = 0; i < chars.length - 1; i++) {
            if (!temp.hasNext(chars[i])) {
                return;
            }
            previous = temp;
            temp = temp.getNext(chars[i]);
        }

        if (!temp.isEnding()) {
            temp.ending = false;
            count--;
        }

        if (previous != null && (temp.next == null || temp.next.size() == 0)) {
            previous.removeNext(temp.getVal());
        }

    }

    public List<String> suggest(String prefix, int num) {
        return suggest(prefix, num, false);
    }

    /**
     * Search by prefix
     *
     * @param prefix Prefix to search
     * @param num    Maximum suggested quantity
     */
    public List<String> suggest(String prefix, int num, boolean containItSelf) {
        if (num <= 0) {
            throw new IllegalArgumentException("Num should be greater than 0.");
        }
        if (Objects.isNull(prefix) || prefix.isEmpty()) {
            return suggestHelper(root, num, containItSelf);
        }
        char[] chars = prefix.toCharArray();
        Node temp = root;
        for (char character : chars) {
            temp = temp.getNext(character);
            if (Objects.isNull(temp)) {
                return new ArrayList<>(0);
            }
        }
        return suggestHelper(temp, num, containItSelf).stream().map(s -> prefix + s).collect(Collectors.toList());

    }

    /**
     * Suggest helper
     */
    private List<String> suggestHelper(Node node, int num, boolean containItSelf) {
        if (node.next == null || node.next.size() == 0) {
            return new ArrayList<>(0);
        }
        List<String> suggestions = new ArrayList<>(num);
        if (containItSelf && node.isEnding()) {
            suggestions.add("");
        }
        traverseHelper("", node, suggestions, num);

        return suggestions;
    }

    /**
     *
     */
    private void traverseHelper(String prefix, Node node, List<String> suggestions, int num) {
        ConcurrentHashMap<Character, Node> next = node.next;
        if (Objects.isNull(next) || next.size() == 0) {
            return;
        }
        Set<Map.Entry<Character, Node>> entries = next.entrySet();
        for (Map.Entry<Character, Node> entry : entries) {
            if (suggestions.size() >= num) {
                return;
            }
            Node son = entry.getValue();
            if (son.isEnding()) {
                suggestions.add(prefix + son.val);
            } else {
                traverseHelper(prefix + son.val, son, suggestions, num);
            }

        }
    }

    public int size() {
        return count;
    }

    /**
     * Search element which is in trie
     *
     * @return exists true, otherwise false
     */
    public boolean contain(String ele) {
        if (Objects.isNull(ele) || ele.length() == 0) {
            return false;
        }
        char[] chars = ele.toCharArray();
        Node temp = root;
        for (char aChar : chars) {
            temp = temp.getNext(aChar);
            if (Objects.isNull(temp)) {
                return false;
            }

        }
        return temp.isEnding();


    }

    public static class Node {
        private boolean ending;
        private final char val;
        private ConcurrentHashMap<Character, Node> next;

        public Node(boolean ending, char val) {
            this.ending = ending;
            this.val = val;
        }

        /**
         * 从root到该节点是否是一个完整元素
         */
        public boolean isEnding() {
            return ending;
        }

        public Node getNext(Character key) {
            return Objects.isNull(next) ? null : next.get(key);
        }

        private boolean hasNext(Character key) {
            return Optional.<Map<Character, Node>>ofNullable(next).orElse(Collections.emptyMap()).containsKey(key);
        }

        private Node addNext(Character key, @SuppressWarnings("SameParameterValue") boolean ending) {
            if (Objects.isNull(next)) {
                next = new ConcurrentHashMap<>();
            }

            Node node;
            if (!next.containsKey(key)) {
                synchronized (this) {
                    if (!next.containsKey(key)) {
                        node = new Node(ending, key);
                        next.put(key, node);
                    }
                }
            }
            Node nextNode = next.get(key);
            nextNode.ending = ending;
            return nextNode;
        }

        private Node addNext(Character key) {
            return addNext(key, false);
        }

        private void removeNext(Character key) {
            Map<Character, Node> map = Optional.<Map<Character, Node>>ofNullable(next).orElse(Collections.emptyMap());
            Node kNode = map.get(key);
            if (Objects.nonNull(kNode)) {
                if (Objects.nonNull(kNode.next) && kNode.next.size() > 0) {
                    // 该节点下还有节点, 修改标志值
                    kNode.ending = false;
                } else {
                    // 该节点下没有节点, 直接去除该节点
                    //noinspection ConstantConditions
                    map.remove(key);
                }
            }
        }

        public char getVal() {
            return val;
        }
    }


}
