package io.transwarp.pudge.spring.client;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;

/**
 * Created by Nirvana on 2017/12/14.
 */
@ConfigurationProperties(prefix = "pudge.client")
public class PudgeClientProperties {

    private boolean poolEnabled = true;

    private int poolMaxActive = 5;

    private long poolMaxWait = 10000;

    private int poolConnectionTimeout = 5000;

    private String servicePackage;

    private ClientRpcServiceProperties[] services;

    public boolean isPoolEnabled() {
        return poolEnabled;
    }

    public void setPoolEnabled(boolean poolEnabled) {
        this.poolEnabled = poolEnabled;
    }

    public int getPoolMaxActive() {
        return poolMaxActive;
    }

    public void setPoolMaxActive(int poolMaxActive) {
        this.poolMaxActive = poolMaxActive;
    }

    public long getPoolMaxWait() {
        return poolMaxWait;
    }

    public void setPoolMaxWait(long poolMaxWait) {
        this.poolMaxWait = poolMaxWait;
    }

    public int getPoolConnectionTimeout() {
        return poolConnectionTimeout;
    }

    public void setPoolConnectionTimeout(int poolConnectionTimeout) {
        this.poolConnectionTimeout = poolConnectionTimeout;
    }

    public String getServicePackage() {
        return servicePackage;
    }

    public void setServicePackage(String servicePackage) {
        this.servicePackage = servicePackage;
    }

    public ClientRpcServiceProperties[] getServices() {
        return services;
    }

    public void setServices(ClientRpcServiceProperties[] services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return "PudgeClientProperties{" +
            "poolEnabled=" + poolEnabled +
            ", maxActive=" + poolMaxActive +
            ", maxWait=" + poolMaxWait +
            ", timeout=" + poolConnectionTimeout +
            ", servicePackage='" + servicePackage + '\'' +
            ", services=" + Arrays.toString(services) +
            '}';
    }
}
