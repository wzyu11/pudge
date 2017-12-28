package io.transwarp.pudge.client.factory;

import io.transwarp.pudge.client.Pudge;
import io.transwarp.pudge.client.PudgeServiceAddress;

/**
 * Created by zado on 2017/12/24.
 */
public interface PudgeFactory {

    Pudge createPudge(PudgeServiceAddress serviceAddress);
}
