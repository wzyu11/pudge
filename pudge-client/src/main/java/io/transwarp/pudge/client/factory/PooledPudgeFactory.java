package io.transwarp.pudge.client.factory;

import io.transwarp.pudge.client.PooledPudge;
import io.transwarp.pudge.client.Pudge;
import io.transwarp.pudge.client.PudgeServiceAddress;
import io.transwarp.pudge.client.pool.PudgePoolConfig;

/**
 * Created by zado on 2017/12/28.
 */
public class PooledPudgeFactory implements PudgeFactory {

    private PudgePoolConfig poolConfig;

    public PooledPudgeFactory(PudgePoolConfig poolConfig) {
        this.poolConfig = poolConfig;
    }

    @Override
    public Pudge createPudge(PudgeServiceAddress serviceSource) {
        return new PooledPudge(poolConfig, serviceSource);
    }
}
