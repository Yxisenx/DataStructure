package cn.onecolour.algorithms.search.array;

/**
 * @author yang
 * @date 2022/10/9
 * @description 跳跃搜索
 */
public class JumpSearch implements ArraySearch {
    @Override
    public int getIndex(int[] array, int x) {
        checkArr(array);
        int len = array.length;
        if (len <= 4) {
            return ArraySearch.getIndex(array, x, SearchType.LinearSearch);
        } else {
            int oldStep = (int) Math.floor(Math.sqrt(len));
            int step = oldStep;
            int prev = 0;
            while (array[Math.min(step, len) - 1] < x) {
                prev = step;
                step += oldStep;
                if (prev >= len)
                    return -1;
            }
            while (array[prev] < x) {
                prev++;

                if (prev == Math.min(oldStep, len))
                    return -1;
            }
            if (array[prev] == x)
                return prev;

            return -1;
        }
    }
}
