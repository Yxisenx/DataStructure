package cn.onecolour.dataStructure.tree.binary_tree;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author yang
 * @date 2022/11/18
 * @description
 */
public class BSTTest {

    private final static List<Integer> integers = Arrays.asList(4, 2, 6, 1, 3, 5, 7);

    private static BinarySearchTree<Integer> bst;
    private static BinarySearchTree<Integer> bst2;

    @BeforeAll
    static void before() {
        init();
    }

    private static void init() {
        bst = new BinarySearchTree<>();
        bst2 = new BinarySearchTree<>();

        for (Integer integer : integers) {
            bst.insert(integer);
            bst2.insert(integer);
        }
    }

    @Test
    void searchTest() {
        System.out.println(bst.search(-1));
        System.out.println(bst.search(1));
        System.out.println(bst.search(7, BinarySearchTree.SearchType.BFS));
    }

    /**
     * 前序遍历
     */
    @Test
    void preorderRecursiveTest() {
        // 前序递归
        bst.preorderTraverseByRecursive();
        // 前序非递归
        bst.preorderTraverseByLoop();
    }

    /**
     * 中序遍历
     */
    @Test
    void inorderRecursiveTest() {
        // 中序递归
        bst.inorderTraverseByRecursive();
        // 中序非递归
        bst.inorderTraverseByLoop();
    }

    /**
     * 后续遍历
     */
    @Test
    void postorderRecursiveTest() {
        // 后续递归
        bst.postorderTraverseByRecursive();
        // 后续非递归
        bst.postorderTraverseByLoop();
    }

    @Test
    void deleteFromBstTest() {
        Random random = new Random();
        for (int j = 0; j < 10000; j++) {
            int deleteNum = random.nextInt(7) + 1;
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < deleteNum;) {
                Integer integer = integers.get(random.nextInt(7));
                if (!list.contains(integer)) {
                    list.add(integer);
                    i++;
                }
            }
//            System.out.println(list);
            init();
            for (Integer integer : list) {
                bst.delete(integer, BinarySearchTree.DeleteType.V1);
                bst2.delete(integer, BinarySearchTree.DeleteType.V2);
            }
            List<Integer> list1 = bst.inorderList();
            List<Integer> list2 = bst2.inorderList();
            assertAll("remove_result",
//                    () -> System.out.printf("%s\n%s\n==================\n", list1, list2),
                    () -> assertTrue(list1.containsAll(list2)),
                    () -> assertTrue(list2.containsAll(list1)));
        }

    }

    @Test
    void deleteV2Test() {
        for (Integer integer : integers) {
            bst2.delete(integer, BinarySearchTree.DeleteType.V2);
        }
        System.out.println(bst2.inorderList());
    }

    @Test
    void deleteV1Test() {
        List<Integer> integers = Arrays.asList(1, 3, 2, 4, 5, 7);
        for (Integer integer : integers) {
            bst.delete(integer, BinarySearchTree.DeleteType.V1);
        }
        System.out.println(bst.inorderList());
    }
}
