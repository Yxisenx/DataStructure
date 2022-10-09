package cn.onecolour.algorithms.search;

/**
 * @author yang
 * @date 2022/10/9
 * @description 二分搜索, 对排序好的数组(不能为null)进行搜索.
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
    public int binarySearchByRecursive(int[] arr, int x, int l, int r) {
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


    public int binarySearchByIteration(int[] arr, int x) {
        int left = 0;
        int right = arr.length - 1;
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
