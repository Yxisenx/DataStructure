package cn.onecolour.dataStructure.tree.trie;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @author yang
 * @date 2022/11/11
 * @description
 */
public class WordSearchTrie {

    private final Node root;
    private int count = 0;
    private final static Integer DEFAULT_NUM = 10;

    /**
     * <p>又称字典树、单词查找树或键树，是一种树形结构，是一种哈希树的变种。</p>
     * <p>典型应用是用于统计，排序和保存大量的字符串（但不仅限于字符串），</p>
     * <p>所以经常被搜索引擎系统用于文本词频统计。</p>
     * <p>它的优点是：利用字符串的公共前缀来减少查询时间，最大限度地减少无谓的字符串比较，查询效率比哈希树高。</p>
     */
    public WordSearchTrie() {
        root = new Node(false, '\'', null);
    }

    /**
     * Add element to
     */
    public void add(String ele, String mean) {

        if (Objects.isNull(ele) || ele.length() == 0) {
            return;
        }
        char[] chars = ele.toCharArray();
        Node temp = root;
        for (char character : chars) {
            Node next = temp.getNext(character);
            if (next == null) {
                next = temp.addNext(character, mean);
            }
            temp = next;
        }
        if (!temp.isEnding()) {
            temp.ending = true;
            count++;
        }

    }

    /**
     * Remove element if it exists in
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

    public List<String> suggest(String prefix) {
        return suggest(prefix, Integer.MAX_VALUE, false);
    }

    public List<String> suggest(String prefix, boolean containItSelf) {
        return suggest(prefix, Integer.MAX_VALUE, containItSelf);
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
        List<String> suggestions = new ArrayList<>(num > 10 ? DEFAULT_NUM : num);
        if (containItSelf && node.isEnding()) {
            suggestions.add(": " + node.getMean());
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
        Set<Map.Entry<Character, Node>> entrySet = next.entrySet();
        for (Map.Entry<Character, Node> entry : entrySet) {
            if (suggestions.size() >= num) {
                return;
            }
            Node son = entry.getValue();
            if (son.isEnding()) {
                suggestions.add(prefix + son.val + ": " + son.mean);
            } else {
                traverseHelper(prefix + son.val, son, suggestions, num);
            }

        }
    }

    public int size() {
        return count;
    }

    /**
     * Search element which is in*
     *
     * @return exists true, otherwise false
     */
    public boolean contain(String ele) {
        if (Objects.isNull(ele) || ele.length() == 0) {
            return false;
        }
        char[] chars = ele.toCharArray();
        Node temp = root;
        // traverse
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
        private final String mean;
        private ConcurrentHashMap<Character, Node> next;

        public Node(boolean ending, char val, String mean) {
            this.ending = ending;
            this.val = val;
            this.mean = mean;
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

        private Node addNext(Character key, @SuppressWarnings("SameParameterValue") boolean ending, String mean) {
            if (Objects.isNull(next)) {
                next = new ConcurrentHashMap<>();
            }

            Node node;
            if (!next.containsKey(key)) {
                synchronized (this) {
                    if (!next.containsKey(key)) {
                        node = new Node(ending, key, mean);
                        next.put(key, node);
                    }
                }
            }
            Node nextNode = next.get(key);
            nextNode.ending = ending;
            return nextNode;
        }

        private Node addNext(Character key, String mean) {
            return addNext(key, false, mean);
        }

        private void removeNext(Character key) {
            Map<Character, Node> map = Optional.<Map<Character, Node>>ofNullable(next).orElse(Collections.emptyMap());
            Node kNode = map.get(key);
            if (Objects.nonNull(kNode)) {
                if (Objects.nonNull(kNode.next) && kNode.next.size() > 0) {
                    // current has children, change flag
                    kNode.ending = false;
                } else {
                    // current has no child, directly remove it
                    //noinspection ConstantConditions
                    map.remove(key);
                }
            }
        }

        public char getVal() {
            return val;
        }

        public String getMean() {
            return mean;
        }
    }

}
