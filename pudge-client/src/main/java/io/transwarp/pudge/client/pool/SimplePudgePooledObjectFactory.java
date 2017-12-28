package io.transwarp.pudge.client.pool;

import io.transwarp.pudge.client.SimplePudge;
import io.transwarp.pudge.client.StrongSocketPudge;
import io.transwarp.pudge.client.PudgeServiceAddress;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zado on 2017/12/27.
 */
public class SimplePudgePooledObjectFactory implements PooledObjectFactory<SimplePudge> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimplePudgePooledObjectFactory.class);

    private PudgeServiceAddress serviceAddress;

    public SimplePudgePooledObjectFactory(PudgeServiceAddress serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    @Override
    public PooledObject<SimplePudge> makeObject() {
        SimplePudge strongPudge = new StrongSocketPudge(serviceAddress);
        LOGGER.info("Create a strong pudge, then put into the pool");

        return new DefaultPooledObject<>(strongPudge);
    }

    @Override
    public void destroyObject(PooledObject<SimplePudge> pudgeObject) {
        SimplePudge simplePudge = pudgeObject.getObject();
        if (simplePudge != null) {
            simplePudge.suicide();
        }
    }

    @Override
    public boolean validateObject(PooledObject<SimplePudge> pudgeObject) {
        return pudgeObject.getObject() != null && pudgeObject.getObject().isAlive();
    }

    @Override
    public void activateObject(PooledObject<SimplePudge> pudgeObject) {
    }

    @Override
    public void passivateObject(PooledObject<SimplePudge> pudgeObject) {
    }
}
