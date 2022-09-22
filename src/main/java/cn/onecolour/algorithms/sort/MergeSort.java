package cn.onecolour.algorithms.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * @author yang
 * @date 2022/9/21
 * @description 归并排序
 */
public class MergeSort<T extends Comparable<T>> implements Sort<T> {

    private volatile Class<T> clazz;

    private void  checkClazz (T[] arr) {
        if (clazz == null) {
            synchronized (MergeSort.class) {
                if (clazz == null) {
                    //noinspection unchecked
                    clazz = (Class<T>) arr[0].getClass();
                }
            }
        }
        if (!Objects.equals(clazz, arr[0].getClass())) {
            throw new IllegalArgumentException("Component's type of array is not correct.");
        }
    }

    @Override
    public void natureSort(@NotNull T[] arr) {
        if (arr == null || arr.length <= 2) {
            return;
        }
        checkClazz(arr);
        T[] sortedArr = sort(arr, (left, right) -> left[0].compareTo(right[0]) < 0);
        System.arraycopy(sortedArr, 0, arr, 0, arr.length);
    }

    private T[] sort(T[] arr, BiFunction<T[], T[], Boolean> biFunction) {
        if (arr.length < 2) {
            return arr;
        }
        int middle = (int) Math.floor(arr.length / 2.0);
        T[] left = Arrays.copyOfRange(arr, 0, middle);
        T[] right = Arrays.copyOfRange(arr, middle, arr.length);
        return merge(sort(left, biFunction), sort(right, biFunction), biFunction);
    }

    protected T[] merge(T[] left, T[] right, BiFunction<T[], T[], Boolean> biFunction) {
        T[] result = createArray(clazz, left.length + right.length);
        int i = 0;
        while (left.length > 0 && right.length > 0) {
            if (biFunction.apply(left, right)) {
                result[i++] = left[0];
                left = Arrays.copyOfRange(left, 1, left.length);
            } else {
                result[i++] = right[0];
                right = Arrays.copyOfRange(right, 1, right.length);
            }
        }

        while (left.length > 0) {
            result[i++] = left[0];
            left = Arrays.copyOfRange(left, 1, left.length);
        }

        while (right.length > 0) {
            result[i++] = right[0];
            right = Arrays.copyOfRange(right, 1, right.length);
        }

        return result;
    }


    @Override
    public void nonNatureSort(@NotNull T[] arr) {
        if (arr == null || arr.length <= 2) {
            return;
        }
        checkClazz(arr);
        System.arraycopy(sort(arr, (left, right) -> right[0].compareTo(left[0]) < 0), 0, arr, 0, arr.length);
    }
}
