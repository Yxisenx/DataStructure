package cn.onecolour.dataStructure.practice.dp;

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

import java.util.concurrent.TimeUnit;

/**
 * @author yang
 * @date 2023/3/9
 * @description
 */
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class FibonacciBenchmark {

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
                .include(FibonacciBenchmark.class.getName() + ".test*")
                .mode(Mode.AverageTime) // 平均响应时间
                .warmupTime(TimeValue.seconds(1)) // 预热时间
                .warmupIterations(5) // 基准测试前进行5次预热
                .threads(16) // 每个进程中的测试线程，这个非常好理解，根据具体情况选择，一般为 cpu 乘以 2。
                .measurementIterations(5) // 基准测试次数
                .forks(1) // 进行 fork 的次数。如果 fork 数是 2 的话，则 JMH 会 fork 出两个进程来进行测试。
                .shouldFailOnError(true)
                .shouldDoGC(true)
                .resultFormat(ResultFormatType.JSON)
                .output("jmh-result-fibonacci.json" )
                .build();
        new Runner(options).run();
    }

    @Test
    @Benchmark
    public void testViolent() {
            Fibonacci.fibonacci_violent(10);

    }

    @Test
    @Benchmark
    public void test() {
            Fibonacci.fibonacci(10);
    }
}
