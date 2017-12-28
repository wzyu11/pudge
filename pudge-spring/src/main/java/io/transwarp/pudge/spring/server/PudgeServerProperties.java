package io.transwarp.pudge.spring.server;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Nirvana on 2017/12/14.
 */
@ConfigurationProperties(prefix = "pudge.server")
public class PudgeServerProperties {

    private String protocol;

    private ServerServiceProperties[] services;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public ServerServiceProperties[] getServices() {
        return services;
    }

    public void setServices(ServerServiceProperties[] services) {
        this.services = services;
    }

}
