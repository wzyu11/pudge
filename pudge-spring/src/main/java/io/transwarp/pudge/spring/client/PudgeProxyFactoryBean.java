package io.transwarp.pudge.spring.client;

import io.transwarp.pudge.client.proxy.PudgeProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Created by Nirvana on 2017/12/15.
 */
public class PudgeProxyFactoryBean<T> implements FactoryBean<T>, InitializingBean {

    private Class<T> pudgeInterface;

    private PudgeProxyFactory pudgeProxyFactory;

    public PudgeProxyFactoryBean(Class<T> pudgeInterface) {
        this.pudgeInterface = pudgeInterface;
    }

    @Override
    public T getObject() throws Exception {
        return pudgeProxyFactory.getProxy(pudgeInterface);
    }

    @Override
    public Class<?> getObjectType() {
        return pudgeInterface;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public void setPudgeProxyFactory(PudgeProxyFactory pudgeProxyFactory) {
        this.pudgeProxyFactory = pudgeProxyFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(pudgeProxyFactory, "pudgeProxyFactory must not be null.");
    }
}
