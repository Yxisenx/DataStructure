package cn.onecolour.dataStructure.tree.heap;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * @author yang
 * @date 2022/11/15
 * @description
 */
public class PriorityQueueTest {
    @Test
    void maxHeapTest() {
        Integer[] array = new Integer[]{100, 95, 99, 79, 91, 98, 54, 63, 71, 83, 90, 97, 44, 49, 51, 33, 62, 64, 70, 41, 81, 84, 89, 48, 61, 12, 43, 11, 26, 24, 50, 2, 21, 15, 42, 8, 31, 30, 65, 7, 35, 20, 80, 17, 39, 37, 87, 6, 27, 23};
        PriorityQueue<Integer> maxHeap = Arrays.stream(array).collect(Collectors.toCollection(() -> new PriorityQueue<>((o1, o2) -> Integer.compare(o2, o1))));
        int size = maxHeap.size();
        for (int i = 0; i < size; i++) {
            System.out.println(maxHeap.poll());
        }
    }
}
