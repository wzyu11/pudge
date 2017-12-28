package io.transwarp.pudge.spring.client;

import com.nirvana.common.utils.StringUtils;
import io.transwarp.pudge.client.PudgeServiceManager;
import io.transwarp.pudge.client.proxy.PudgeConfig;
import io.transwarp.pudge.client.proxy.JdkPudgeProxyFactory;
import io.transwarp.pudge.spring.PudgeServiceScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;

import java.util.*;

/**
 * Created by Nirvana on 2017/12/14.
 */
@Configuration
@EnableConfigurationProperties(PudgeClientProperties.class)
public class PudgeClientAutoConfiguration {


    @Bean
    public BeanDefinitionRegistryPostProcessor beanDefinitionRegistryPostProcessor(Environment environment) {
        final PudgeClientProperties properties = pudgeClientProperties(environment);

        return new BeanDefinitionRegistryPostProcessor() {
            @Override
            public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {

                PudgeConfig clientConfig = new PudgeConfig();
                clientConfig.setPoolEnabled(properties.isPoolEnabled());
                clientConfig.setMaxActive(properties.getPoolMaxActive());
                clientConfig.setMaxWait(properties.getPoolMaxWait());
                clientConfig.setTimeout(properties.getPoolConnectionTimeout());

                GenericBeanDefinition factoryDefinition = new GenericBeanDefinition();
                factoryDefinition.setBeanClass(JdkPudgeProxyFactory.class);
                factoryDefinition.getConstructorArgumentValues().addIndexedArgumentValue(0, clientConfig);
//                factoryDefinition.getPropertyValues().add("clientConfig", clientConfig);
                registry.registerBeanDefinition("pudgeProxyFactory", factoryDefinition);

                for (Class<?> proxyInterface : registerPudgeServices(properties)) {
                    GenericBeanDefinition definition = new GenericBeanDefinition();
                    definition.getConstructorArgumentValues().addGenericArgumentValue(proxyInterface);
                    definition.setBeanClass(PudgeProxyFactoryBean.class);
                    definition.getPropertyValues().addPropertyValue("pudgeProxyFactory", new RuntimeBeanReference("pudgeProxyFactory"));
                    registry.registerBeanDefinition(proxyInterface.getSimpleName(), definition);
                }
            }

            @Override
            public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

            }
        };
    }

    private PudgeClientProperties pudgeClientProperties(Environment environment) {
        PudgeClientProperties properties = new PudgeClientProperties();
        if (environment instanceof AbstractEnvironment) {
            AbstractEnvironment environment0 = (AbstractEnvironment) environment;
            for (PropertySource source : environment0.getPropertySources()) {
                if (source.containsProperty("pudge.client.pool-enabled")) {
                    properties.setPoolEnabled((Boolean) source.getProperty("pudge.client.pool-enabled"));
                }
                if (source.containsProperty("pudge.client.service-package")) {
                    properties.setServicePackage((String) source.getProperty("pudge.client.service-package"));
                }
                if (source.containsProperty("pudge.client.pool-max-active")) {
                    properties.setPoolMaxActive((Integer) source.getProperty("pudge.client.pool-max-active"));
                }
                if (source.containsProperty("pudge.client.pool-max-wait")) {
                    properties.setPoolMaxWait((Long) source.getProperty("pudge.client.pool-max-wait"));
                }
                if (source.containsProperty("pudge.client.pool-connection-timeout")) {
                    properties.setPoolConnectionTimeout((Integer) source.getProperty("pudge.client.pool-connection-timeout"));
                }
                List<ClientRpcServiceProperties> propertiesList = new ArrayList<>();
                for (int i = 0; ; i++) {
                    if (source.containsProperty("pudge.client.services[" + i + "].service-name")) {
                        String serviceName = (String) source.getProperty("pudge.client.services[" + i + "].service-name");
                        String url = (String) source.getProperty("pudge.client.services[" + i + "].url");
                        if (StringUtils.isNotBlank(serviceName) && StringUtils.isNotBlank(url)) {
                            propertiesList.add(new ClientRpcServiceProperties(serviceName, url));
                        }
                    } else {
                        break;
                    }
                }
                if (!propertiesList.isEmpty()) {
                    properties.setServices(propertiesList.toArray(new ClientRpcServiceProperties[]{}));
                }
            }
        }
        return properties;
    }

    // todo 以后估计得优化一下
    private Collection<Class<?>> registerPudgeServices(PudgeClientProperties properties) {
        Collection<Class<?>> interfaces = new ArrayList<>();
        if (properties != null) {
            PudgeServiceScanner scanner = new PudgeServiceScanner(properties.getServicePackage());

            if (properties.getServices() != null) {
                for (ClientRpcServiceProperties service : properties.getServices()) {
                    // 注册服务
                    PudgeServiceManager.register(service.getServiceName(), service.getUrl());

                    // 添加所有的pudge service的接口
                    interfaces.addAll(scanner.scan(service.getServiceName()));
                }
            }
        }

        return interfaces;
    }
}
