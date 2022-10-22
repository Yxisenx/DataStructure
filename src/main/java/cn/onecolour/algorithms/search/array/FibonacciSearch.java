package cn.onecolour.algorithms.search.array;

/**
 * @author yang
 * @date 2022/10/22
 * @description https://www.baeldung.com/cs/fibonacci-search
 */
public class FibonacciSearch implements ArraySearch {
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
                    int fk2 = 0; // F(k-2)
                    int fk1 = 1; // F(k-1)
                    int fk = fk1 + fk2; // Fk
                    // Initialize fk make fk <= len
                    while (fk <= len) {
                        fk2 = fk1;
                        fk1 = fk;
                        fk = fk1 + fk2;
                    }
                    int offset = -1;
                    while (fk2 >= 0) {
                        int index = Math.min(offset + fk2, len - 1);
                        if (array[index] < x) {
                            fk = fk1;
                            fk1 = fk2;
                            fk2 = fk - fk1;
                            offset = index;
                        } else if (array[index] > x) {
                            fk = fk2;
                            fk1 = fk1 - fk2;
                            fk2 = fk - fk1;
                        } else {
                            return index;
                        }
                    }
                    if ((fk1 & array[offset + 1]) == fk) {
                        return offset + 1;
                    }
            }
        }
        return -1;
    }
}
