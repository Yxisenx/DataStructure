package cn.onecolour.algorithms.sort;

import org.jetbrains.annotations.NotNull;

/**
 * @author yang
 * @date 2022/1/28
 * @description
 */
public interface Sort<T extends Comparable<T>> {

    void sort(@NotNull T... arr);

    void nonNatureSort(@NotNull T ... arr);

    default void swap(int i, int j, @NotNull T... arr) {
        if (i == j) {
            return;
        }
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
