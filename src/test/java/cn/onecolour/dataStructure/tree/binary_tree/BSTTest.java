package cn.onecolour.dataStructure.tree.binary_tree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

/**
 * @author yang
 * @date 2022/11/18
 * @description
 */
public class BSTTest {

    private final static List<Integer> integers = Arrays.asList(4,2,6,1,3,5,7);

    private static BinarySearchTree<Integer> bst;

    @BeforeAll
    static void before() {
        bst = new BinarySearchTree<>();
        for (Integer integer : integers) {
            bst.insert(integer);
        }
    }

    @Test
    void preorderRecursiveTest() {
        bst.preorderTraverseByRecursive();
        bst.preorderTraverseByLoop();
    }
    @Test
    void inorderRecursiveTest() {
        bst.inorderTraverseByRecursive();
        bst.inorderTraverseByLoop();
    }
    @Test
    void postorderRecursiveTest() {
        bst.postorderTraverseByRecursive();
    }
}
