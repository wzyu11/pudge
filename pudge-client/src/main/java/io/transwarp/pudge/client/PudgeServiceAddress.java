package io.transwarp.pudge.client;

import java.util.Objects;

/**
 * Created by zado on 2017/12/24.
 */
public class PudgeServiceAddress {

    private String host;

    private int port;

    public PudgeServiceAddress() {
    }

    public PudgeServiceAddress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PudgeServiceAddress that = (PudgeServiceAddress) o;
        return port == that.port &&
                Objects.equals(host, that.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }
}
