package com.onecolour.algorithms.search;

import cn.onecolour.algorithms.search.array.ArraySearch;
import cn.onecolour.algorithms.search.array.BinarySearch;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static cn.onecolour.algorithms.search.array.ArraySearch.SearchType.*;

/**
 * @author yang
 * @date 2022/10/8
 * @description
 */
@State(Scope.Benchmark)
public class ArraySearchBenchmarks {

    /**
     * 随机生成的排序后数组
     */
    private int[] ARRAY;

    {
        try {
            File file = new File(System.getProperty("user.dir") + "/src/test/resources/sortArray.txt");
            if (file.exists()) {
                String s = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
                ARRAY = Arrays.stream(s.split(",\n")).mapToInt(Integer::parseInt).toArray();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private final static Integer MAX_VALUE = 1000000;

    private final static Integer NUM = 100000;

    private final static Integer[] INDEXES = {1, 10, 100, 1000, 10000, 20000, 30000, 40000, 50000, 60000, 70000, 80000, 90000};

    @Test
    public void generateArrayFile() throws IOException {
        List<Integer> nums = new LinkedList<>();
        List<Integer> results = new ArrayList<>(NUM);
        for (int i = 1; i < MAX_VALUE; i++) {
            nums.add(i);
        }
        Random random = new Random();
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/sortArray.txt");
        //noinspection ResultOfMethodCallIgnored
        file.delete();
        for (int i = 0; i < NUM - 1; i++) {
            Integer integer = nums.get(random.nextInt(nums.size()));
            nums.remove(integer);
            results.add(integer);
        }
        FileUtils.writeStringToFile(file, results.stream().sorted().map(Object::toString).collect(Collectors.joining(",\n")), StandardCharsets.UTF_8, true);
    }

    @Test
    void runBenchmarks() throws Exception {
        Options options = new OptionsBuilder()
                .include(this.getClass().getName() + ".exponential*")
                .mode(Mode.AverageTime) // 平均响应时间
                .warmupTime(TimeValue.seconds(1)) // 预热时间
                .warmupIterations(3) // 基准测试前进行5次预热
                .threads(Runtime.getRuntime().availableProcessors() * 2) // 每个进程中的测试线程，这个非常好理解，根据具体情况选择，一般为 cpu 乘以 2。
                .measurementIterations(5) // 基准测试次数
                .forks(1)
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(options).run();
    }

    public void searchAndCheck(ArraySearch.SearchType type) {
        for (Integer i : INDEXES) {
            int index = ArraySearch.getIndex(ARRAY, ARRAY[i], type);
            Assertions.assertEquals(index, i);
        }

    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Test
    public void linearSearchTest() {
        searchAndCheck(LinearSearch);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Test
    public void binarySearchIterationTest() {
        searchAndCheck(BinarySearch);
    }

    @Test
    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void binarySearchRecursiveTest() {
        ArraySearch binarySearch = new BinarySearch(cn.onecolour.algorithms.search.array.BinarySearch.BinarySearchType.recursive);
        for (Integer i : INDEXES) {
            int index = binarySearch.getIndex(ARRAY, ARRAY[i]);
            Assertions.assertEquals(index, i);
        }
    }


    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Test
    public void jumpSearchTest() {
        searchAndCheck(JumpSearch);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Test
    public void interpolationSearchTest() {
        searchAndCheck(InterpolationSearch);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Test
    public void exponentialSearchTest() {
        searchAndCheck(ExponentialSearch);
    }
}