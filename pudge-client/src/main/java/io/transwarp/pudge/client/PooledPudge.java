package io.transwarp.pudge.client;

import io.transwarp.pudge.client.pool.PudgePoolConfig;
import io.transwarp.pudge.client.pool.SimplePudgePool;
import io.transwarp.pudge.core.FreshMeat;
import io.transwarp.pudge.core.PudgeHook;

/**
 * Created by zado on 2017/12/27.
 */
public class PooledPudge implements Pudge {

    private PudgePoolConfig poolConfig;

    private PudgeServiceAddress serviceAddress;

    private SimplePudgePool pudgePool;

    public PooledPudge(PudgePoolConfig poolConfig, PudgeServiceAddress serviceAddress) {
        this.poolConfig = poolConfig;
        this.serviceAddress = serviceAddress;
        initPool();
    }

    @Override
    public FreshMeat hook(PudgeHook hook) {
        SimplePudge miniPudge = pudgePool.retrieve();
        FreshMeat freshMeat = miniPudge.hook(hook);
        pudgePool.release(miniPudge);

        return freshMeat;
    }

    private void initPool() {
        pudgePool = new SimplePudgePool(serviceAddress, poolConfig);
    }
}
