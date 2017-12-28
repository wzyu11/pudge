package io.transwarp.pudge.client.pool;

/**
 * Created by zado on 2017/12/24.
 */
public class PudgePoolConfig {

    private int maxActive;  // 连接池大小

    private long maxWait;   // 获取一条连接的的等待时间

    private int timeout;   // 池中一条连接的执行超时

    public PudgePoolConfig() {
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

    public static class Builder {
        private int maxActive;
        private long maxWait;
        private int timeout;

        public Builder maxActive(int maxActive) {
            this.maxActive = maxActive;
            return this;
        }

        public Builder maxWait(long maxWait) {
            this.maxWait = maxWait;
            return this;
        }

        public Builder timeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        public PudgePoolConfig build() {
            PudgePoolConfig config = new PudgePoolConfig();

            // 默认池active为-1是无限大小
            // 等待时间为10s
            config.setMaxActive(maxActive > 0 ? maxActive : -1);
            config.setMaxWait(maxWait > 0 ? maxWait : 10000);
            // 默认的连接超时时间为5s
            config.setTimeout(timeout > 0 ? timeout : 5000);

            return config;
        }
    }
}
