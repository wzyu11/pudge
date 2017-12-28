package io.transwarp.pudge.spring.client;

import java.util.Objects;

/**
 * Created by Nirvana on 2017/12/15.
 */
public class ClientRpcServiceProperties {

    private String serviceName;

    private String url;

    public ClientRpcServiceProperties() {
    }

    public ClientRpcServiceProperties(String serviceName, String url) {
        this.serviceName = serviceName;
        this.url = url;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientRpcServiceProperties that = (ClientRpcServiceProperties) o;
        return Objects.equals(serviceName, that.serviceName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(serviceName);
    }

    @Override
    public String toString() {
        return "ClientRpcServiceProperties{" +
                "serviceName='" + serviceName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
