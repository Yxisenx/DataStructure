package cn.onecolour.algorithms.sort;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author yang
 * @date 2022/2/3
 * @description 普通希尔排序 DM=[N/2],Dk=[D(k+1)/2]
 * Hibbard增量序列 Dk=2^(k−1): {1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191...}
 */
public class ShellSort<T extends Comparable<T>> implements Sort<T> {

    private Type type = Type.HIBBARD;

    public void setType(Type type) {
        this.type = type;
    }

    public String getType() {
        return type.name();
    }

    protected ShellSort() {
    }

    public ShellSort(Type type) {
        this.type = type;
    }

    public enum Type {
        NORMAL,
        HIBBARD,
        SEDGEWICK
    }

    private static Set<Integer> getGaps(Type type, int len) {
        Set<Integer> gaps = new TreeSet<>(Comparator.reverseOrder());
        int gap;
        int halfLen = len / 2;
        switch (type) {
            case NORMAL:
                gap = halfLen;
                gaps.add(gap);
                while (gap > 0) {
                    gaps.add(gap);
                    gap /= 2;

                }
                break;
            case HIBBARD:
                int temp = 1;
                gap = 1;
                while (gap < len) {
                    gaps.add(gap);
                    gap = (1 << temp) - 1;
                    temp++;
                }
                break;
            case SEDGEWICK:
                temp = 0;
                boolean flag = Boolean.TRUE;
                while (flag) {
                    // 9*4^i - 9*2^i + 1
                    int gap1 = 9 * ((1 << 2 * temp) - (1 << temp)) + 1;
                    // 4^i - 3*2^i + 1
                    int gap2 = (1 << 2 * temp) - 3 * (1 << temp) + 1;

                    if (gap1 > 0) {
                        if (gap1 < halfLen) {
                            gaps.add(gap1);
                        } else {
                            flag = Boolean.FALSE;
                        }
                    }
                    if (gap2 > 0) {
                        if (gap2 < halfLen) {
                            gaps.add(gap2);
                        } else {
                            flag = Boolean.FALSE;
                        }
                    }

                    temp++;
                }
                break;
        }
        return gaps;
    }

    @Override
    public void natureSort(@NotNull T[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int len = arr.length;
        Set<Integer> gaps = getGaps(type, len);
        for (Integer gap : gaps) {
            for (int i = gap; i < len; i++) {
                T tmp = arr[i];
                int j;
                for (j = i; j >= gap && tmp.compareTo(arr[j - gap]) < 0; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                if (j != i) {
                    arr[j] = tmp;
                }
            }
        }
    }

    @Override
    public void nonNatureSort(@NotNull T[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int len = arr.length;
        Set<Integer> gaps = getGaps(type, len);
        for (Integer gap : gaps) {
            for (int i = gap; i < len; i++) {
                T tmp = arr[i];
                int j;
                for (j = i; j >= gap && tmp.compareTo(arr[j - gap]) > 0; j -= gap) {
                    arr[j] = arr[j - gap];
                }
                if (j != i) {
                    arr[j] = tmp;
                }
            }
        }
    }
}
