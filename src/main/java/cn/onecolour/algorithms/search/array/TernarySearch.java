package cn.onecolour.algorithms.search.array;

/**
 * @author yang
 * @date 2022/10/24
 * @description 三元搜索 like  {@link BinarySearch}
 */
public class TernarySearch implements ArraySearch {

    private final TernarySearchType type;

    public enum TernarySearchType {
        iteration, recursive
    }

    public TernarySearch() {
        type = TernarySearchType.iteration;
    }

    public TernarySearch(TernarySearchType type) {
        this.type = type;
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
                    switch (type) {
                        case iteration:
                            return ternarySearchByIteration(array, x, len);
                        case recursive:
                            return ternarySearchByRecursive(array, x, 0, len);
                    }
            }
        }
        return -1;
    }

    /**
     * 三元搜索, 递归
     *
     * @param array 数组
     * @param x     查找key
     * @param l     左index
     * @param r     右index
     * @return index of x or -1
     */
    public static int ternarySearchByRecursive(int[] array, int x, int l, int r) {
        if (r >= l) {
            // ------l---l1---r1---r--------
            // check left
            int l1 = r / 3 + 2 * l / 3;
            if (array[l1] == x) {
                return l1;
            } else if (array[l1] > x) {
                return ternarySearchByRecursive(array, x, l, l1);
            }
            // check right
            int r1 = 2 * r / 3 + l / 3;
            if (array[r1] == x) {
                return r1;
            } else if (array[r1] < x) {
                return ternarySearchByRecursive(array, x, r1, r);
            }

            return ternarySearchByRecursive(array, x, l1, r1);
        }
        return -1;
    }

    public static int ternarySearchByIteration(int[] array, int x, int len) {
        int l = 0;
        int r = len;

        while (r >= l) {
            int l1 = r / 3 + 2 * l / 3;
            int r1 = 2 * r / 3 + l / 3;
            if (array[l1] == x) {
                return l1;
            } else if (array[l1] > x) {
                r = l1;
            } else if (array[r1] == x) {
                return r1;
            } else if (array[r1] < x) {
                l = r1;
            } else {
                l = l1;
                r = r1;
            }

        }

        return -1;
    }
}
