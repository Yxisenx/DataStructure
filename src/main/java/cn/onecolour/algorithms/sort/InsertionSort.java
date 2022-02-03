package cn.onecolour.algorithms.sort;

import org.jetbrains.annotations.NotNull;

/**
 * @author yang
 * @date 2022/1/28
 * @description 插入排序
 */
@SuppressWarnings("unchecked")
public class InsertionSort<T extends Comparable<T>> implements Sort<T> {


    @Override
    public final void sort(@NotNull T... arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int len = arr.length;
        for (int i = 1; i < len; i++) {
            T num = arr[i];
            int j = i - 1;
            for (; j >= 0 && num.compareTo(arr[j]) < 0; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = num;
        }
    }

    @Override
    public final void nonNatureSort(@NotNull T... arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int len = arr.length;
        for (int i = 1; i < len; i++) {
            T num = arr[i];
            int j = i - 1;
            for (; j >= 0 && num.compareTo(arr[j]) > 0; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = num;
        }
    }
}
