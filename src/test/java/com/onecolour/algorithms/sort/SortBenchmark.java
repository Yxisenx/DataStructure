package com.onecolour.algorithms.sort;

import cn.onecolour.algorithms.sort.*;
import com.onecolour.util.RandomUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

/**
 * @author yang
 * @date 2022/9/22
 * @description 排序基准测试
 */
@SuppressWarnings("unused")
@State(Scope.Thread)
public class SortBenchmark {

    /**
     * 自然排序列表
     */
    private Integer[] NATURAL_ARRAY;
    /**
     * 非自然排序列表
     */
    private Integer[] NON_NATURAL_ARRAY;
    /**
     * 生成的原始数组
     */
    private Integer[] ARRAY;

    private Integer[] COPY_OF_ARRAY;

    private final static Integer MAX_VALUE = 1000;

    private final static Integer NUM = 10000;

    @Setup(Level.Invocation)
    public void init() {
        ARRAY = RandomUtils.randomArr(MAX_VALUE, NUM);
        NATURAL_ARRAY = Arrays.stream(ARRAY).sorted().toArray(Integer[]::new);
        NON_NATURAL_ARRAY = Arrays.stream(ARRAY).sorted(Comparator.reverseOrder()).toArray(Integer[]::new);
        COPY_OF_ARRAY = Arrays.copyOf(ARRAY, ARRAY.length);
    }


    @Test
    void runBenchmarks() throws Exception {
        Options options = new OptionsBuilder()
                .include(this.getClass().getName() + ".bubble*")
                .mode(Mode.AverageTime) // 平均响应时间
                .warmupTime(TimeValue.seconds(1)) // 预热时间
                .warmupIterations(5) // 基准测试前进行5次预热
                .threads(Runtime.getRuntime().availableProcessors() * 2) // 每个进程中的测试线程，这个非常好理解，根据具体情况选择，一般为 cpu 乘以 2。
                .measurementIterations(5) // 基准测试次数
                .forks(2) // 进行 fork 的次数。如果 fork 数是 2 的话，则 JMH 会 fork 出两个进程来进行测试。
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(options).run();
    }
    private void  naturalSortAndChecked(Sort<Integer> sort) {
        sort.natureSort(COPY_OF_ARRAY);
        Assertions.assertArrayEquals(COPY_OF_ARRAY, NATURAL_ARRAY);
    }
    private void  nonNaturalSortAndChecked(Sort<Integer> sort) {
        sort.nonNatureSort(COPY_OF_ARRAY);
        Assertions.assertArrayEquals(COPY_OF_ARRAY, NON_NATURAL_ARRAY);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void bubbleSortNaturalTest() {
        naturalSortAndChecked(new BubbleSort<>());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void bubbleSortNonNaturalTest() {
        nonNaturalSortAndChecked(new BubbleSort<>());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void insertionSortNaturalTest() {
        naturalSortAndChecked(new InsertionSort<>());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void insertionSortNonNaturalTest() {
        nonNaturalSortAndChecked(new InsertionSort<>());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void mergeSortNaturalTest() {
        naturalSortAndChecked(new MergeSort<>());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void mergeSortNonNaturalTest() {
        nonNaturalSortAndChecked(new MergeSort<>());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void selectionSortNaturalTest() {
        naturalSortAndChecked(new SelectionSort<>());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void selectionSortNonNaturalTest() {
        nonNaturalSortAndChecked(new SelectionSort<>());
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void shellSortNatural_NORMAL_Test() {
        naturalSortAndChecked(new ShellSort<>(ShellSort.Type.NORMAL));
    }
    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void shellSortNonNatural_NORMAL_Test() {
        nonNaturalSortAndChecked(new ShellSort<>(ShellSort.Type.NORMAL));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void shellSortNatural_HIBBARD_Test() {
        naturalSortAndChecked(new ShellSort<>(ShellSort.Type.HIBBARD));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void shellSortNonNatural_HIBBARD_Test() {
        nonNaturalSortAndChecked(new ShellSort<>(ShellSort.Type.HIBBARD));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void shellSortNatural_SEDGEWICK_Test() {
        naturalSortAndChecked(new ShellSort<>(ShellSort.Type.SEDGEWICK));
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    public void shellSortNonNatural_SEDGEWICK_Test() {
        nonNaturalSortAndChecked(new ShellSort<>(ShellSort.Type.SEDGEWICK));
    }


}
