package cn.onecolour.algorithms.search;

import cn.onecolour.algorithms.search.array.ArraySearch;
import cn.onecolour.algorithms.search.array.BinarySearch;
import cn.onecolour.algorithms.search.array.MultivariateSearch;
import cn.onecolour.algorithms.search.array.TernarySearch;
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
                ARRAY = Arrays.stream(s.split("(,\r\n)|(,\n)|(,\r)")).mapToInt(Integer::parseInt).toArray();
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
        String currentClassName = this.getClass().getName();
        Options options = new OptionsBuilder()
                .include(currentClassName + ".multivariate*")
//                .include(currentClassName + ".binary*")
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
            Assertions.assertEquals(i, index);
        }
    }

    private void searchAndCheck(ArraySearch search) {
        for (Integer i : INDEXES) {
            int index = search.getIndex(ARRAY, ARRAY[i]);
            try {
                Assertions.assertEquals(i, index);
            } catch (AssertionError e) {
                e.printStackTrace();
                System.out.println(i);
            }
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
        searchAndCheck(binarySearch);
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

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Test
    public void fibonacciSearchTest() {
        searchAndCheck(FibonacciSearch);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Test
    public void ternarySearchIterationTest() {
        searchAndCheck(TernarySearch);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Test
    public void multivariateOneSearchTest() {
        ArraySearch multivariateSearch = new MultivariateSearch(1);
        searchAndCheck(multivariateSearch);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Test
    public void multivariateTwoSearchTest() {
        ArraySearch multivariateSearch = new MultivariateSearch(2);
        searchAndCheck(multivariateSearch);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Test
    public void multivariateThreeSearchTest() {
        ArraySearch multivariateSearch = new MultivariateSearch(3);
        searchAndCheck(multivariateSearch);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Test
    public void multivariateFourSearchTest() {
        ArraySearch multivariateSearch = new MultivariateSearch(4);
        searchAndCheck(multivariateSearch);
    }
    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @Test
    public void multivariateTenSearchTest() {
        ArraySearch multivariateSearch = new MultivariateSearch(10);
//        multivariateSearch.getIndex(ARRAY, 27);
        searchAndCheck(multivariateSearch);
    }



    @Test
    @Benchmark
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void ternarySearchRecursiveTest() {
        ArraySearch ternarySearch = new TernarySearch(cn.onecolour.algorithms.search.array.TernarySearch.TernarySearchType.recursive);
        searchAndCheck(ternarySearch);
    }
}
