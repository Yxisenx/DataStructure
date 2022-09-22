package cn.onecolour.algorithms.sort;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

/**
 * @author yang
 * @date 2022/1/28
 * @description
 */
@SuppressWarnings("unchecked")
public interface Sort<T extends Comparable<T>> {

    void natureSort(@NotNull T... arr);

    void nonNatureSort(@NotNull T ... arr);

    default void swap(int i, int j, @NotNull T[] arr) {
        if (i == j) {
            return;
        }
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    default T[] createArray(Class<T> componentType,int length) {
        //noinspection unchecked
        return (T[]) Array.newInstance(componentType, length);
    }
}
