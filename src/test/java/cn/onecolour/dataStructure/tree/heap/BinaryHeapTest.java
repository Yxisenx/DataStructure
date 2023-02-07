package cn.onecolour.dataStructure.tree.heap;

import cn.onecolour.dataStructure.tree.heap.Heap;
import cn.onecolour.dataStructure.tree.heap.MaxHeap;
import org.junit.jupiter.api.Test;

/**
 * @author yang
 * @date 2022/11/9
 * @description
 */
public class BinaryHeapTest {
    @Test
    void testMaxHeap() {
        Heap<Integer> heap = new MaxHeap<>(new Integer[]{1, 2, 3, 4});
        heap.insert(5);
    }
}