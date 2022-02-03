package cn.onecolour.algorithms.sort;

import org.jetbrains.annotations.NotNull;

/**
 * @author yang
 * @date 2022/1/28
 * @description
 */
@SuppressWarnings("unchecked")
public class SelectionSort<T extends Comparable<T>> implements Sort<T>{
    @Override
    public void sort(@NotNull T... arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            int j = i + 1;
            int index = i; // 最小值序号
            for (; j < len; j++) {
                if (arr[index].compareTo(arr[j]) > 0) {
                    index = j;
                }
            }
            swap(i, index, arr);
        }
    }

    @Override
    public void nonNatureSort(@NotNull T... arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int len = arr.length;
        for (int i = 0; i < len - 1; i++) {
            int j = i + 1;
            int index = i; // 最大值序号
            for (; j < len; j++) {
                if (arr[index].compareTo(arr[j]) < 0) {
                    index = j;
                }
            }
            swap(i, index, arr);
        }
    }
}
