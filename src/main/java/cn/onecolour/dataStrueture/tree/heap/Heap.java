package cn.onecolour.dataStrueture.tree.heap;

/**
 * @author yang
 */
public interface Heap<T extends Comparable<T>> {

    /**
     * return true if heap is empty else false
     */
    default boolean isEmpty() {
        return size() == 0;
    }

    /**
     * elements num
     */
    int size();

    /**
     * init heap from array
     */
    void heapify(int pos);

    /**
     * add element to heap if not exist
     */
    void insert(T element);

    /**
     * delete element from heap if exist
     */
    void delete(T element);
}
