package cn.onecolour.algorithms.search.array;

import java.util.Objects;

/**
 * @author yang
 * @date 2022/10/9
 * @description 二分搜索, 对排序好的数组进行搜索.
 */
public class BinarySearch implements ArraySearch {

    private BinarySearchType binarySearchType;

    public enum BinarySearchType {
        iteration, recursive
    }

    public void setBinarySearchType(BinarySearchType binarySearchType) {
        this.binarySearchType = binarySearchType;
    }


    public BinarySearch() {
        this(BinarySearchType.iteration);
    }

    public BinarySearch(BinarySearchType binarySearchType) {
        this.binarySearchType = binarySearchType;
    }


    @Override
    public int getIndex(int[] array, int x) {
        if (array != null) {
            int len = array.length;
            switch (len) {
                case 0:
                    return -1;
                case 1:
                    return array[0] == x ? 0 : -1;
                default:
                    switch (binarySearchType) {
                        case iteration:
                            return binarySearchByIteration(array, x);
                        case recursive:
                            return binarySearchByRecursive(array, x, 0, len - 1);
                    }
            }
        }
        return -1;
    }

    /**
     * 二分搜索(递归)
     *
     * @param arr 数组
     * @param x   搜索元素
     * @param l   左index
     * @param r   右 index
     * @return 搜索元素 index
     */
    public static int binarySearchByRecursive(int[] arr, int x, int l, int r) {
        if (r >= l) {
            int mid = l + (r - l) / 2;
            if (arr[mid] == x) {
                return mid;
            }
            if (arr[mid] > x) {
                return binarySearchByRecursive(arr, x, l, mid - 1);
            }
            return binarySearchByRecursive(arr, x, mid + 1, r);
        }

        return -1;
    }


    /**
     * 二分搜索, 迭代
     * @param arr 数组
     * @param x 待查找元素
     * @return 找到元素返回index, 否, 则返回-1
     */
    public static int binarySearchByIteration(int[] arr, int x, int ... leftAndRight) {
        int left;
        int right;
        if (Objects.nonNull(leftAndRight )&& leftAndRight.length == 2) {
            left = leftAndRight[0];
            right = leftAndRight[1];
        } else {
            left = 0;
            right = arr.length - 1;
        }
        while (right >= left) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == x) {
                return mid;
            }
            if (arr[mid] > x) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return -1;
    }

}
