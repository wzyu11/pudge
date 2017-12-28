package io.transwarp.pudge.client.factory;

import io.transwarp.pudge.client.Pudge;
import io.transwarp.pudge.client.PudgeServiceAddress;
import io.transwarp.pudge.client.WeakSocketPudge;

/**
 * Created by zado on 2017/12/28.
 */
public class SimplePudgeFactory implements PudgeFactory {

    @Override
    public Pudge createPudge(PudgeServiceAddress serviceAddress) {
        return new WeakSocketPudge(serviceAddress);
    }
}
