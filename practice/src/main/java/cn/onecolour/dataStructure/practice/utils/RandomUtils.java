package cn.onecolour.dataStructure.practice.utils;

import java.util.Objects;
import java.util.Random;

/**
 * @author yang
 * @date 2023/3/13
 * @description
 */
public class RandomUtils {
    private final static Random random = new Random();
    public static String randomStr(Random random, char[] chars, int min, int max, double stopFactor) {
        if (min > max || min < 0) {
            throw new IllegalArgumentException();
        }
        if (max == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        int maxCharIndex = Objects.requireNonNull(chars).length - 1;
        for (int i = 0; i < max; i++) {
            int index = random.nextInt(maxCharIndex + 1);
            sb.append(chars[index]);
            if (sb.length() >= min && stop(random, max, stopFactor)) {
                break;
            }
        }
        return sb.toString();
    }

    public static String randomStr(Random random, char[] chars, int len) {
        return randomStr(random, chars, len, len, 0);
    }

    public static String randomStr(char[] chars, int len) {
        return randomStr(random, chars, len);
    }

    private static boolean stop(Random random, int max, double stopFactor) {
        return random.nextInt(max) / (max * 1.0d) > stopFactor;
    }
}
