package io.transwarp.pudge.spring.server;

import java.util.Objects;

/**
 * Created by Nirvana on 2017/12/15.
 */
public class ServerServiceProperties {

    private String name;

    private int port;

    private int threadPoolSize = 0;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }

    public void setThreadPoolSize(int threadPoolSize) {
        this.threadPoolSize = threadPoolSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerServiceProperties that = (ServerServiceProperties) o;
        return port == that.port &&
            threadPoolSize == that.threadPoolSize &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, port, threadPoolSize);
    }
}
