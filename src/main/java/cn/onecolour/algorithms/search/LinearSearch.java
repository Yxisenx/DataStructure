package cn.onecolour.algorithms.search;

/**
 * 线性搜索(遍历)
 */
public class LinearSearch implements ArraySearch {

    @Override
    public  int getIndex(int[] array, int x) {
        checkArr(array);
        for (int i = 0; i < array.length; i++) {
            if (array[i] == x) {
                return i;
            }
        }
        return -1;
    }
}
