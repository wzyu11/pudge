package io.transwarp.pudge.spring.server;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Nirvana on 2017/12/14.
 */
@Configuration
@EnableConfigurationProperties(PudgeServerProperties.class)
public class PudgeServerAutoConfiguration {

    @Bean
    public ContextInitializeListener contextInitializeListener(PudgeServerProperties properties) {
        return new ContextInitializeListener(properties);
    }

}
