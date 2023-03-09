package cn.onecolour.dataStructure.practice.dp;

import java.util.Arrays;

/**
 * 计算斐波拉契数列和
 *
 * @author yang
 * @date 2023/3/9
 * @description
 */
public class Fibonacci {

    // Violent solution
    public static int fibonacci_violent(int n) {
        if (n <= 1)
            return n;
        return fibonacci_violent(n - 1) + fibonacci_violent(n - 2);
    }


    private final int MAX = 100;
    private final int NIL = -1;
    private final int[] lookup = new int[MAX];

    // Dynamic programing
    // dp solution
    private int _fibonacci(int n) {
        if (lookup[n] == NIL) {
            lookup[n] = n <= 1 ? n : _fibonacci(n - 1) + _fibonacci(n - 2);
        }
        return lookup[n];
    }

    public static int fibonacci(int n) {
        return InstanceHolder.instance._fibonacci(n);
    }

    private static final class InstanceHolder {
        static final Fibonacci instance = new Fibonacci();
        static {
            Arrays.fill(instance.lookup, instance.NIL);
        }
    }


}
