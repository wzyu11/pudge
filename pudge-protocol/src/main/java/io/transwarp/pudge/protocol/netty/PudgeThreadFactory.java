package io.transwarp.pudge.protocol.netty;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 18-1-24 created by zado
 */
public class PudgeThreadFactory implements ThreadFactory {

    private final static AtomicInteger THREAD_ID = new AtomicInteger(1);

    private final AtomicInteger mThreadNum = new AtomicInteger(1);

    private final String prefix;

    private final boolean daemon;

    private final ThreadGroup threadGroup;

    public PudgeThreadFactory() {
        this("pudge-server-pool" + THREAD_ID.getAndIncrement(), false);
    }

    public PudgeThreadFactory(boolean daemon) {
        this("pudge-server-pool", daemon);
    }

    public PudgeThreadFactory(String prefix) {
        this(prefix, false);
    }

    public PudgeThreadFactory(String prefix, boolean daemon) {
        this.prefix = prefix + "-thread-";
        this.daemon = daemon;
        SecurityManager sm  = System.getSecurityManager();
        threadGroup = sm == null ? Thread.currentThread().getThreadGroup() : sm.getThreadGroup();
    }

    @Override
    public Thread newThread(Runnable r) {
        String threadName = prefix + mThreadNum.getAndIncrement();
        Thread thread = new Thread(threadGroup, r, threadName, 0);
        thread.setDaemon(daemon);

        return thread;
    }

    public ThreadGroup getThreadGroup() {
        return threadGroup;
    }
}
