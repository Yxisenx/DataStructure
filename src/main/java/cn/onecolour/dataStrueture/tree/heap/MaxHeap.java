package cn.onecolour.dataStrueture.tree.heap;

import javax.annotation.Nonnull;

/**
 * @author yang
 * @date 2022/11/9
 * @description 二叉堆
 */
public class MaxHeap<T extends Comparable<T>> extends BinaryHeap<T> {

    private int size;
    private int capacity;
    private Object[] elements;
    private static final int DEFAULT_CAPACITY = 10;


    public MaxHeap(@Nonnull T[] elements) {
        int arrLen = elements.length;
        this.elements = new Object[arrLen];
        capacity = arrLen;
        size = arrLen;
        if (arrLen > 0) {
            // copy array
            System.arraycopy(elements, 0, this.elements, 0, arrLen);
            heapify(0);
        }
    }

    public MaxHeap(int initCapacity) {
        elements = new Object[initCapacity];
        capacity = initCapacity;
    }

    public MaxHeap() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void heapify(int pos) {

    }

    @Override
    public void insert(T element) {

    }

    @Override
    public void delete(T element) {

    }
}
