package io.transwarp.pudge.client.pool;

import com.nirvana.common.utils.StringUtils;
import io.transwarp.pudge.client.SimplePudge;
import io.transwarp.pudge.client.PudgeServiceAddress;
import io.transwarp.pudge.core.PudgeException;
import org.apache.commons.pool2.impl.GenericObjectPool;

/**
 * Created by zado on 2017/12/25.
 */
public class SimplePudgePool implements PudgePool<SimplePudge> {

    private GenericObjectPool<SimplePudge> pool;

    private PudgeServiceAddress address;

    private PudgePoolConfig config;

    public SimplePudgePool(PudgeServiceAddress address, PudgePoolConfig config) {
        this.address = address;
        this.config = config;
        this.init();
    }

    private void init() {
        validatePoolConfig();
        createPool();
    }

    private void validatePoolConfig() {
        if (address == null
                || StringUtils.isEmpty(address.getHost())
                || address.getPort() <= 0) {
            throw new IllegalArgumentException("Pudge pool address is not configured");
        }

        if (config == null) {
            throw new IllegalArgumentException("Pudge pool configuration is not configured");
        }
    }

    private void createPool() {
        pool = new GenericObjectPool<>(new SimplePudgePooledObjectFactory(address));

        pool.setMaxTotal(config.getMaxActive());
        pool.setMaxWaitMillis(config.getMaxWait());
    }

    @Override
    public SimplePudge retrieve() {
        try {
            return pool.borrowObject();
        } catch (Exception e) {
            throw new PudgeException("SimplePudge whose address: " + address.getHost() + ":" + address.getPort() + " is not available", e);
        }
    }

    @Override
    public void release(SimplePudge pudge) {
        pool.returnObject(pudge);
    }
}
