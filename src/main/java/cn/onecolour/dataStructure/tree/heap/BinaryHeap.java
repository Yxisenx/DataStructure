package cn.onecolour.dataStructure.tree.heap;

/**
 * @author yang
 * @date 2022/11/9
 * @description
 */
public abstract class BinaryHeap<T extends Comparable<T>> implements Heap<T> {
    protected int size;
    protected int capacity;
    protected Object[] elements;
    protected static final int DEFAULT_CAPACITY = 10;

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
        return pos >= (size / 2) && pos <= size;
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

    protected void checkCapacity(int size) {
        if (size > capacity * 0.75) {
            Object[] objs = new Object[(int) (size / 0.75)];
            System.arraycopy(elements, 0, objs, 0, size());
            capacity = objs.length;
            elements = objs;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int iMax = size - 1;
        if (iMax == -1)
            return "[]";
        sb.append('[');
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i == iMax)
                return sb.append(']').toString();
            sb.append(", ");
        }
        return sb.toString();
    }
}
