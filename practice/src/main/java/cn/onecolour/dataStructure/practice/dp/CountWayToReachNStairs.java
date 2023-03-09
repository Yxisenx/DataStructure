package cn.onecolour.dataStructure.practice.dp;

import java.util.Arrays;

/**
 * @author yang
 * @date 2023/3/9
 * @description
 */
public class CountWayToReachNStairs {

    private final static int NIL = -1;
    private int[] lookup;

    public static int countWay(int n) {
        int[] lookup = InstanceHolder.instance().lookup;
        if (n >= lookup.length) {
            int[] arr = new int[(int) Math.max(n, lookup.length * 1.25)];
            System.arraycopy(lookup, 0, arr, 0, lookup.length);
            for (int i = lookup.length; i < arr.length; i++) {
                arr[i] = NIL;
            }
            InstanceHolder.instance().lookup = arr;
        }
        return count(n);
    }

    private static int count(int n) {
        int[] lookup = InstanceHolder.instance().lookup;
        if (lookup[n] == NIL) {
            lookup[n] = n <= 1 ? n : count(n - 1) + count(n - 2);
        }
        return lookup[n];
    }

    private static class InstanceHolder {
        private static final CountWayToReachNStairs instance = new CountWayToReachNStairs();
        private static boolean initFlag = false;

        public static CountWayToReachNStairs instance() {
            if (!initFlag) {
                synchronized (InstanceHolder.class) {
                    if (!initFlag) {
                        instance.lookup = new int[10];
                        Arrays.fill(instance.lookup, NIL);
                        instance.lookup[0] = 0;
                        instance.lookup[1] = 1;
                        initFlag = true;
                    }
                }
            }
            return instance;
        }
    }

    public static void main(String[] args) {
        for (int i = 1; i < 20; i++) {
            System.out.printf("stairs: %3d, result: %d\n", i, countWay(i));
        }
    }
}
