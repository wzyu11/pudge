package io.transwarp.pudge.protocol.netty;

import java.util.concurrent.*;

/**
 * 18-1-24 created by zado
 */
public class PudgeThreadPool {

    public static Executor getExecutor(int poolSize, int queueCap) {
        return new ThreadPoolExecutor(poolSize, poolSize, 0, TimeUnit.MILLISECONDS,
            getQueue(queueCap), new PudgeThreadFactory(true), new AbortPolicyWithReport());
    }

    private static BlockingQueue<Runnable> getQueue(int queueCap) {
        if (queueCap == 0) {
            return new SynchronousQueue<>();
        } else if (queueCap < 0) {
            return new LinkedBlockingDeque<>();
        } else {
            return new LinkedBlockingDeque<>(queueCap);
        }
    }
}
