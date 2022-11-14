package cn.onecolour.dataStructure.tree.heap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author yang
 * @date 2022/11/11
 * @description
 */
public class MaxHeapTest {
    @Test
    void test() {
        Integer[] array = new Integer[]{2, 6, 7, 8, 11, 12, 15, 17, 20, 21, 23, 24, 26, 27, 30, 31, 33, 35, 37, 39, 41, 42, 43, 44, 48, 49, 50, 51, 54, 61, 62, 63, 64, 65, 70, 71, 79, 80, 81, 83, 84, 87, 89, 90, 91, 95, 97, 98, 99, 100};
        MaxHeap<Integer> maxHeap = new MaxHeap<>(array);
        System.out.println(maxHeap);
        Assertions.assertTrue(maxHeap.maxHeapCheck());
        for (int i = 1; i <= 50; i++) {
            Assertions.assertTrue(maxHeap.maxHeapCheck());
            System.out.println(maxHeap.extract());
        }
        System.out.println(maxHeap);
        Assertions.assertTrue(maxHeap.maxHeapCheck());
    }
}
