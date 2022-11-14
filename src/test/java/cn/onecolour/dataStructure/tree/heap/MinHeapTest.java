package cn.onecolour.dataStructure.tree.heap;

import org.junit.jupiter.api.Test;

/**
 * @author yang
 * @date 2022/11/14
 * @description
 */
public class MinHeapTest {
    @Test
    void test() {
        Integer[] array = new Integer[]{100, 95, 99, 79, 91, 98, 54, 63, 71, 83, 90, 97, 44, 49, 51, 33, 62, 64, 70, 41, 81, 84, 89, 48, 61, 12, 43, 11, 26, 24, 50, 2, 21, 15, 42, 8, 31, 30, 65, 7, 35, 20, 80, 17, 39, 37, 87, 6, 27, 23};
        MinHeap<Integer> minHeap = new MinHeap<>(array);
        System.out.println(minHeap);
        for (int i = 1; i <= 50; i++) {
            System.out.println(minHeap.extract());
        }
        System.out.println(minHeap);
    }
}
