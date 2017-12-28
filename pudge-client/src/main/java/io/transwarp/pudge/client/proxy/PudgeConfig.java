package io.transwarp.pudge.client.proxy;

/**
 * Created by zado on 2017/12/25.
 */
public class PudgeConfig {

    private boolean poolEnabled;

    private int maxActive;

    private long maxWait;

    private int timeout;

    public boolean isPoolEnabled() {
        return poolEnabled;
    }

    public void setPoolEnabled(boolean poolEnabled) {
        this.poolEnabled = poolEnabled;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public long getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(long maxWait) {
        this.maxWait = maxWait;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }
}
