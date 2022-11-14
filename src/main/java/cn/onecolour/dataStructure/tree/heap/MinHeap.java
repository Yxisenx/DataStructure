package cn.onecolour.dataStructure.tree.heap;

import javax.annotation.Nonnull;

/**
 * @author yang
 * @date 2022/11/14
 * @description
 */
public class MinHeap<T extends Comparable<T>> extends BinaryHeap<T>{
    public MinHeap(@Nonnull T[] elements) {
        int arrLen = elements.length;
        this.elements = new Object[0];
        checkCapacity(elements.length);
        size = 0;
        if (arrLen > 0) {
            // copy array
            for (T element : elements) {
                insert(element);
            }
        }
    }

    public MinHeap(int initCapacity) {
        elements = new Object[initCapacity];
        capacity = initCapacity;
    }

    public MinHeap() {
        this(DEFAULT_CAPACITY);
    }

    @Override
    public int size() {
        return size;
    }


    private void heapify(int pos) {
        if (isLeaf(pos, elements.length)) {
            return;
        }
        int l = leftChild(pos); // left child index
        int r = rightChild(pos); // right child index

        // leftChild < parent || right < parent, swap
        if ((l < size && compare(elements, pos, l) > 0) || (r < size && compare(elements, pos, r) > 0)) {
            if (compare(elements, pos, l) > 0) {
                swap(elements, pos, l);
                heapify(l);
            }
            if (r < size && compare(elements, pos, r) > 0) {
                swap(elements, pos, r);
                heapify(r);
            }
        }
    }


    @Override
    public void insert(T element) {
        checkCapacity(size + 1);
        elements[size] = element;
        int temp = size;
        while (compare(elements, temp, parent(temp)) < 0) {
            int parent = parent(temp);
            swap(elements, temp, parent);
            temp = parent;
        }
        size++;
    }

    @Override
    public T extract() {
        //noinspection unchecked
        T ele = (T) elements[0];
        elements[0] = elements[--size];
        elements[size] = null;
        heapify(0);

        return ele;
    }
}
