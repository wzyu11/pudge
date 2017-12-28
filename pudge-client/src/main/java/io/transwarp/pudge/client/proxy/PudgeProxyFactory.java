package io.transwarp.pudge.client.proxy;

/**
 * Created by Nirvana on 2017/12/14.
 */
public interface PudgeProxyFactory {

    <T> T getProxy(Class<T> pudgeInterface);

}
