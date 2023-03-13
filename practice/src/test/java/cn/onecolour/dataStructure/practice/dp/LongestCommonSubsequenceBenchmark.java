package cn.onecolour.dataStructure.practice.dp;

import cn.onecolour.dataStructure.practice.utils.RandomUtils;
import org.apache.commons.math3.util.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * @author yang
 * @date 2023/3/13
 * @description
 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class LongestCommonSubsequenceBenchmark {

    private final static Map<Pair<String, String>, TestResult> map = new TreeMap<>(Comparator.comparingInt(o -> o.getKey().length()));

    private final static class TestResult {
        private String resA;
        private LongestCommonSubsequence.LcsResult resB;
    }

    @Test
    void benchmark() throws RunnerException {
        Options options = new OptionsBuilder()
                .include(LongestCommonSubsequenceBenchmark.class.getName() + ".test*")
                .mode(Mode.AverageTime) // 平均响应时间
                .warmupTime(TimeValue.seconds(1)) // 预热时间
                .warmupIterations(5) // 基准测试前进行5次预热
                .threads(8) // 每个进程中的测试线程，这个非常好理解，根据具体情况选择，一般为 cpu 乘以 2。
                .measurementIterations(5) // 基准测试次数
                .forks(1) // 进行 fork 的次数。如果 fork 数是 2 的话，则 JMH 会 fork 出两个进程来进行测试。
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .resultFormat(ResultFormatType.JSON)
                .result("jmh-result-lcs.json")
                .build();
        new Runner(options).run();
    }

    @BeforeAll
    static void beforeAll() {
        char[] chars = new char[]{'A', 'B', 'C'};
        for (int i = 5; i <= 25; i++) {
            Pair<String, String> pair = new Pair<>(RandomUtils.randomStr(chars, i), RandomUtils.randomStr(chars, i));
            map.put(pair, new TestResult());
        }
    }

    private <T> void execute(BiFunction<String, String, T> biFunction, BiConsumer<T, TestResult> biConsumer) {
        Set<Map.Entry<Pair<String, String>, TestResult>> entries = map.entrySet();
        for (Map.Entry<Pair<String, String>, TestResult> entry : entries) {
            Pair<String, String> key = entry.getKey();
            TestResult result = entry.getValue();
            T tRes = biFunction.apply(key.getKey(), key.getValue());
            biConsumer.accept(tRes, result);
        }
    }

    @Test
    @Benchmark
    public void testViolent() {
        execute(LongestCommonSubsequence::lcsViolentSolution, (common, testResult) -> testResult.resA = common);
    }

    @Test
    @Benchmark
    public void testNormal() {
        execute(LongestCommonSubsequence::lcs, (lcsResult, testResult) -> testResult.resB = lcsResult);
    }

    //    @AfterAll
    static void afterAll() {
        Set<Map.Entry<Pair<String, String>, TestResult>> entries = map.entrySet();
        for (Map.Entry<Pair<String, String>, TestResult> entry : entries) {
            Pair<String, String> key = entry.getKey();
            TestResult result = entry.getValue();
            System.out.printf("s1: %s\ns2: %s\nLCS-LENGTH: %s\nCommonA: %s\nCommonB: %s\n%s\n", key.getKey(), key.getValue(),
                    result.resB.getLength(),
                    result.resA,
                    result.resB.getCommon(),
                    LongestCommonSubsequence.printLcsResult(result.resB));
            System.out.println("\n==================================\n");
        }
    }
}
