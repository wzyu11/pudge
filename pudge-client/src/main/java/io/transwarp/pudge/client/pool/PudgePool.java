package io.transwarp.pudge.client.pool;

import io.transwarp.pudge.client.Pudge;

/**
 * Created by zado on 2017/12/28.
 */
public interface PudgePool<T extends Pudge> {

    T retrieve();

    void release(T pudge);
}
