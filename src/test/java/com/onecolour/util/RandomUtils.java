package com.onecolour.util;

import java.util.Random;

/**
 * @author yang
 * @date 2022/10/8
 * @description
 */
public class RandomUtils {
    public static Integer[] randomArr(int maxValue, int num) {
        Random random = new Random();
        Integer[] nums = new Integer[num];
        for (int i = 0; i < num; i++) {
            nums[i] = random.nextInt(maxValue);
        }
        return nums;
    }

    public static Integer randomInteger(int ... values) {
        Random random = new Random();
        if (values == null || values.length == 0) {
            return random.nextInt();
        } else if (values.length == 1) {
            return random.nextInt(values[0]);
        } else {
            return random.nextInt(values[0] - values[1]) + values[1];
        }
    }
}
