package cn.onecolour.algorithms.sort;

import org.jetbrains.annotations.NotNull;

/**
 * @author yang
 * @date 2022/2/3
 * @description 冒泡排序
 */
@SuppressWarnings("unchecked")
public class BubbleSort<T extends Comparable<T>> implements Sort<T> {
    @Override
    public void sort(@NotNull T... arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int len = arr.length;

        for (int j = len - 1; j >= 0; j--) {
            for (int i = 0; i < j; i++) {
                // 比较 i 和 i+1 如果i>i+1交换位置
                if (arr[i].compareTo(arr[i + 1]) > 0) {
                    swap(i, i + 1, arr);
                }
            }
        }
    }

    @Override
    public void nonNatureSort(@NotNull T... arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int len = arr.length;

        for (int j = len - 1; j >= 0; j--) {
            for (int i = 0; i < j; i++) {
                // 比较 i 和 i+1 如果i>i+1交换位置
                if (arr[i].compareTo(arr[i + 1]) < 0) {
                    swap(i, i + 1, arr);
                }
            }
        }
    }
}
