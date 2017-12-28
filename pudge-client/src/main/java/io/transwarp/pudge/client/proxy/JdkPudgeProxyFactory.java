package io.transwarp.pudge.client.proxy;

import java.lang.reflect.Proxy;

/**
 * Created by zado on 2017/12/16.
 */
public class JdkPudgeProxyFactory implements PudgeProxyFactory {

    private PudgeHandler pudgeHandler;

    public JdkPudgeProxyFactory(PudgeConfig pudgeConfig) {
        this.pudgeHandler = new PudgeHandler(pudgeConfig);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getProxy(Class<T> pudgeInterface) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(),
                                          new Class<?>[] {pudgeInterface},
                                          pudgeHandler);
    }
}
