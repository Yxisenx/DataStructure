package cn.onecolour.dataStructure.tree.heap;

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
     * add element to heap if not exist
     */
    void insert(T element);

    T extract();
}
