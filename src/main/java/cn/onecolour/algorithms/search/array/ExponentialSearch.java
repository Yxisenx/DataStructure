package cn.onecolour.algorithms.search.array;

/**
 * @author yang
 * @date 2022/10/10
 * @description 指数搜索
 */
public class ExponentialSearch implements ArraySearch {
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
                    int i = 1;
                    while (i < len) {
                        int num = array[i];
                        if (x == num) {
                            return i;
                        } else if (x < num) {
                            return BinarySearch.binarySearchByIteration(array, x, i >> 1 + 1, i - 1);
                        } else {
                            i <<= 1;
                        }
                    }
                    return BinarySearch.binarySearchByIteration(array, x, i >> 1 + 1, len - 1);
            }
        }
        return -1;
    }
}
