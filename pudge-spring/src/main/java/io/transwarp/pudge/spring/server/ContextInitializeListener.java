package io.transwarp.pudge.spring.server;

import io.transwarp.pudge.server.AbleMeatProvider;
import io.transwarp.pudge.core.PudgeServer;
import io.transwarp.pudge.server.PudgeMethodSource;
import io.transwarp.pudge.server.MeatProvider;
import io.transwarp.pudge.server.PudgeNettyServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Nirvana on 2017/12/14.
 */
public class ContextInitializeListener implements ApplicationListener<ApplicationContextEvent> {

    private Set<PudgeServer> pudgeServers = new HashSet<>();

    private Set<ServerServiceProperties> providerServices = new HashSet<>();

    public ContextInitializeListener(PudgeServerProperties properties) {
        if (properties != null && properties.getServices() != null) {
            providerServices.addAll(Arrays.asList(properties.getServices()));
        }
    }

    @Override
    public void onApplicationEvent(ApplicationContextEvent event) {
        if (event instanceof ContextRefreshedEvent) {
            if (providerServices == null) {
                return;
            }
            for (ServerServiceProperties service : providerServices) {
                PudgeMethodSource pudgeMethodSource = new SpringPudgeMethodSource(service.getName(), event.getApplicationContext());
                if (pudgeMethodSource.size() > 0) {
                    MeatProvider meatProvider = new AbleMeatProvider(pudgeMethodSource);
                    PudgeServer pudgeServer = new PudgeNettyServer(service.getPort(), meatProvider);
                    pudgeServers.add(pudgeServer);
                    pudgeServer.start();
                }
            }

        } else if (event instanceof ContextClosedEvent) {
            for (PudgeServer server : pudgeServers) {
                if (server != null) {
                    server.stop();
                }
            }

        }
    }

}
