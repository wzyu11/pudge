package io.transwarp.pudge.protocol.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 18-1-24 created by zado
 */
public class AbortPolicyWithReport extends ThreadPoolExecutor.AbortPolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbortPolicyWithReport.class);

    private final String threadName;

    public AbortPolicyWithReport() {
        this("pudge-server-pool");
    }

    public AbortPolicyWithReport(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        LOGGER.error("Executor [threadName={}] rejected execution", threadName);
        super.rejectedExecution(r, e);
    }
}
