package cn.onecolour.dataStrueture.tree.heap;

/**
 * @author yang
 * @date 2022/11/9
 * @description
 */
public abstract class BinaryHeap<T extends Comparable<T>> implements Heap<T> {

    protected int parent(int pos) {
        return (pos - 1) / 2;
    }

    protected int leftChild(int pos) {
        return (2 * pos) + 1;
    }

    protected int rightChild(int pos) {
        return (2 * pos) + 2;
    }

    protected boolean isLeaf(int pos, int size) {
        return pos > (size / 2) && pos <= size;
    }

    protected int compare(Object[] arr, int fIndex, int sIndex) {
        //noinspection unchecked
        return ((T)arr[fIndex]).compareTo((T)arr[sIndex]);
    }

    protected void swap(Object[] arr, int fIndex, int sIndex) {
        Object temp = arr[fIndex];
        arr[fIndex] = arr[sIndex];
        arr[sIndex] = temp;
    }
}
