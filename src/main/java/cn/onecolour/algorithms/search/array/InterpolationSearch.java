package cn.onecolour.algorithms.search.array;

/**
 * @author yang
 * @date 2022/10/10
 * @description 插值搜索, 二分搜索改良
 */
public class InterpolationSearch implements ArraySearch {

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
                    int left = 0;
                    int right = array.length - 1;
                    int i;
                    while (right > left) {
                        i = left + (right - left) * (x - array[left]) / (array[right] - array[left]);
                        i = Math.max(left, Math.min(i, right));//不能超过数组的右边界
                        if (array[i] == x) {
                            return i;
                        } else if (array[i] > x) {
                            right = i - 1;
                        } else {
                            left = i + 1;
                        }
                    }
            }
        }
        return -1;
    }
}
