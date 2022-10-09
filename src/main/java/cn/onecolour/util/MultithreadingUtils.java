package cn.onecolour.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yang
 * @date 2022/10/9
 * @description
 */
public class MultithreadingUtils {

    public static ExecutorService newExecutorService() {
        return new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                ioIntensivePoolSize(),
                100,
                TimeUnit.MILLISECONDS,
                //阻塞队列超过核心线程数后会compare 判断优先级
                new PriorityBlockingQueue<>(10, (o1, o2) -> 0));
    }


    /**
     * Each tasks blocks 90% of the time, and works only 10% of its
     *    lifetime. That is, I/O intensive pool
     * @return io intensive Thread pool size
     */
    public static int ioIntensivePoolSize() {
        double blockingCoefficient = 0.9;
        return poolSize(blockingCoefficient);
    }

    /**
     *
     * Number of threads = Number of Available Cores / (1 - Blocking
     * Coefficient) where the blocking coefficient is between 0 and 1.
     *
     * A computation-intensive task has a blocking coefficient of 0, whereas an
     * IO-intensive task has a value close to 1,
     * so we don't have to worry about the value reaching 1.
     *  @param blockingCoefficient the coefficient
     *  @return Thread pool size
     */
    public static int poolSize(double blockingCoefficient) {
        int numberOfCores = Runtime.getRuntime().availableProcessors();
        return (int) (numberOfCores / (1 - blockingCoefficient));
    }
}
