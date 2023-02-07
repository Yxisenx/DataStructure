package cn.onecolour.dataStructure.tree.binary_tree;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author yang
 * @date 2022/11/28
 * @description Balanced binary tree tests
 */
public class AVLTest {

    private final static List<Integer> integers = Arrays.asList(100, 90, 80, 70, 60, 50, 55, 65, 75, 85, 95);
    private static BalancedBinarySearchTree<Integer> avl;

    private static void init() {
        avl = new BalancedBinarySearchTree<>(integers);
    }

    @BeforeAll
    static void setUp() {
        init();
    }

    @Test
    void deleteTest() {
        List<Integer> indexes = new ArrayList<>();
        int size = integers.size();
        List<Integer> restIndex = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            restIndex.add(i);
        }
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int randomInt = random.nextInt(restIndex.size());
            indexes.add(restIndex.remove(randomInt));
        }
        System.out.println(indexes);
        for (Integer index : indexes) {
            avl.delete(integers.get(index));
        }


    }
}
