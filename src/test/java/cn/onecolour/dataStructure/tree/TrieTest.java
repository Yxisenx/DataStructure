package cn.onecolour.dataStructure.tree;

import cn.onecolour.dataStructure.tree.trie.Trie;
import cn.onecolour.dataStructure.tree.trie.WordSearchTrie;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

/**
 * @author yang
 * @date 2022/11/7
 * @description
 */
public class TrieTest {



    @Test
    void test() {
        String ele = "abcdefg";
        Trie trie = new Trie();
        trie.add(ele);
        Assertions.assertTrue(trie.contain(ele));
        Assertions.assertFalse(trie.contain("abc"));
        System.out.println(trie.size());
        trie.remove(ele);
        Assertions.assertTrue(trie.contain(ele));
        System.out.println(trie.size());
    }

    @Test
    void suggestTest() throws IOException {
        Trie trie = new Trie();
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/word.csv");
        List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
        lines.forEach(line -> {
            String[] split = line.split(",");
            String word = split[0].substring(1, split[0].length() - 1);
            trie.add(word);
        });
        long start = System.currentTimeMillis();
        System.out.println(trie.suggest("a", 5, true));
        System.out.println("Using time: " + (System.currentTimeMillis() - start) + "ms");
    }

    @Test
    void suggestTestV2() throws IOException {
        WordSearchTrie trie = new WordSearchTrie();
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/word.csv");
        List<String> lines = FileUtils.readLines(file, StandardCharsets.UTF_8);
        lines.forEach(line -> {
            String[] split = line.split(",");
            String word = split[0].substring(1, split[0].length() - 1);
            String mean = line.substring(split[0].length() + 2 , line.length() - 1);
            trie.add(word, mean);
        });
        String prefix;
        Scanner sc = new Scanner(System.in);
        //noinspection InfiniteLoopStatement
        while (true) {
            System.out.print("prefix: ");
            prefix = sc.nextLine();
            List<String> words = trie.suggest(prefix, true);
            System.out.println("Word num: " + words.size());
            for (String s : words) {
                System.out.println(s);
            }
            System.out.println("==============================");
        }

    }
}
