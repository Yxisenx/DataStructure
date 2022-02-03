package com.onecolour.algorithms.sort;

import cn.onecolour.algorithms.sort.*;
import org.junit.Test;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/**
 * @author yang
 * @date 2022/1/28
 * @description 排序单元测试
 */
public class SortTest {

    /**
     * KEY: 数字个数 value:自然排序列表
     */
    private final static Map<Integer, List<Integer>> NATURE_LIST_MAP = new HashMap<>();
    /**
     * value: 非自然排序列表
     */
    private final static Map<Integer, List<Integer>> NON_NATURE_LIST_MAP = new HashMap<>();
    /**
     * 生成的原始数组
     */
    private final static Map<Integer, Integer[]> ARRAYS = new HashMap<>();

    private final static Integer MAX_VALUE = 1000;

    private final static Integer[] NUMS = {0, 1, 10, 100, 1000, 10000, 100000};

    static {
        for (Integer integer : NUMS) {
            ARRAYS.put(integer, getRandomArr(MAX_VALUE, integer));
        }
        final BiConsumer<Integer, Integer[]> consumer = ((num, arr) -> {
            NATURE_LIST_MAP.put(num, Arrays.stream(arr).sorted().collect(Collectors.toList()));
            NON_NATURE_LIST_MAP.put(num, Arrays.stream(arr).sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
        });
        ARRAYS.forEach(consumer);
    }

    @SuppressWarnings("SameParameterValue")
    private static Integer[] getRandomArr(int maxValue, int num) {
        Random random = new Random();
        Integer[] nums = new Integer[num];
        for (int i = 0; i < num; i++) {
            nums[i] = random.nextInt(maxValue);
        }
        return nums;
    }

    @Test
    public void testSelectionSort() {
        test(new SelectionSort<>());
    }

    @Test
    public void testInsertionSort() {
        test(new InsertionSort<>());
    }

    @Test
    public void testBubbleSort() {
        test(new BubbleSort<>());
    }

    @Test
    public void testShellSort() {
        @SuppressWarnings("unchecked")
        ShellSort<Integer>[] sorts = new ShellSort[] {
                new ShellSort<>(ShellSort.Type.NORMAL),
                new ShellSort<>(ShellSort.Type.HIBBARD),
                new ShellSort<>(ShellSort.Type.SEDGEWICK)
        };
        for (ShellSort<Integer> sort : sorts) {
            System.out.println(sort.getType());
            test(sort);
        }

    }

    private void test(Sort<Integer> sort) {
        String sortName = getName(sort);
        System.out.println(sortName);
        // 正序排列
        System.out.println("===NATURAL SORT===");
        for (Integer num : NUMS) {
            Integer[] arr = ARRAYS.get(num);
            Integer[] nums = new Integer[arr.length];
            System.arraycopy(arr, 0, nums, 0, arr.length);
            long start = System.nanoTime();
            sort.sort(nums);
            System.out.printf("sort %s num used: %s. result: %s%n", num, System.nanoTime() - start,
                    checkResult(nums, NATURE_LIST_MAP.get(num)));
        }
        // 倒序排列
        System.out.println("===NON NATURAL SORT===");
        for (Integer num : NUMS) {
            Integer[] arr = ARRAYS.get(num);
            Integer[] nums = new Integer[arr.length];
            System.arraycopy(arr, 0, nums, 0, arr.length);
            long start = System.nanoTime();
            sort.nonNatureSort(nums);
            System.out.printf("sort %s num used: %s. result: %s%n", num, System.nanoTime() - start,
                    checkResult(nums, NON_NATURE_LIST_MAP.get(num)));
        }
        System.out.println();
    }


    private String getName(Sort<Integer> sort) {
        String simpleClassName = sort.getClass().getSimpleName();
        int i = 32 - simpleClassName.length();
        StringBuilder sb = new StringBuilder();
        for (int j = 0; j < i; j++) {
            sb.append("=");
        }
        sb.append(simpleClassName);
        for (int j = 0; j < i; j++) {
            sb.append("=");
        }
        return sb.toString();
    }

    private boolean checkResult(Integer[] arr, List<Integer> list) {
        for (int i = 0; i < arr.length; i++) {
            if (!Objects.equals(list.get(i), arr[i])) {
                return Boolean.FALSE;
            }
        }
        return Boolean.TRUE;
    }
}
