package cn.onecolour.dataStructure.linear;

import java.util.Iterator;

/**
 * @author yang
 * @date 2023/2/14
 * @description
 */
public interface Stack<T> {

    void push(T ele);

    T pop();

    T peek();

    int size();

    boolean isEmpty();

    Iterator<T> iterator();
}
