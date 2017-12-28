package io.transwarp.pudge.spring.server;

import java.util.Objects;

/**
 * Created by Nirvana on 2017/12/15.
 */
public class ServerServiceProperties {

    private String name;

    private int port;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerServiceProperties that = (ServerServiceProperties) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name);
    }

}
