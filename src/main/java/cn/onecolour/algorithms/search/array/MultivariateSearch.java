package cn.onecolour.algorithms.search.array;

/**
 * 多元搜索
 * @see BinarySearch 二元搜索
 * @see TernarySearch 三元搜索
 *
 * @author yang
 * @date 2023/2/9
 * @description
 */
public class MultivariateSearch implements ArraySearch {

    // 默认三元搜索
    private final static int DEFAULT_DIVIDE_TIMES = 2;

    private int divideTimes;

    public void setDivideTimes(int divideTimes) {
        this.divideTimes = divideTimes;
    }

    public MultivariateSearch() {
        divideTimes = DEFAULT_DIVIDE_TIMES;
    }

    public MultivariateSearch(int divideTimes) {
        this.divideTimes = divideTimes;
    }

    @Override
    public int getIndex(int[] array, int x) {
        return indexOf(array, x, divideTimes);
    }

    public static int indexOf(int[] array, int x, int divideTimes) {
        if (divideTimes < 1) {
            throw new IllegalArgumentException();
        }
        if (array != null) {
            int len = array.length;
            switch (len) {
                case 0:
                    return -1;
                case 1:
                    return array[0] == x ? 0 : -1;
                default:
                    return indexOf(array, x, divideTimes, 0, array.length - 1);
            }
        }
        return -1;
    }

    private static int indexOf(int[] array, int x, int divideTimes, int left, int right) {
        int preLeft = left;
        if (right >= left) {
            for (int i = 0; i <= divideTimes; i++) {
                // divide position index
                int divideIndex = left + (i + 1) * (right - left) / (divideTimes + 1);
                int dividePosVal = array[divideIndex];
                if (dividePosVal == x) {
                    return divideIndex;
                }
                if (dividePosVal > x) {
                    return indexOf(array, x, divideTimes, preLeft, divideIndex);
                } else {
                    preLeft = divideIndex;
                }
            }
        }
        return -1;
    }
}
