package io.transwarp.pudge.client;

import io.transwarp.pudge.core.FreshMeat;
import io.transwarp.pudge.core.PudgeHook;

/**
 * Created by Nirvana on 2017/12/16.
 * 封装真正的传输：nio/oio/连接池/传输协议
 */
public interface Pudge {

    FreshMeat hook(PudgeHook hook);

}
