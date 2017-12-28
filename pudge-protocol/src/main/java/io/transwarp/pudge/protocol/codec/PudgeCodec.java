package io.transwarp.pudge.protocol.codec;

import io.transwarp.pudge.core.FreshMeat;
import io.transwarp.pudge.core.PudgeHook;

/**
 * Created by Nirvana on 2017/12/21.
 */
public interface PudgeCodec {

    byte[] serialize(PudgeHook hook);

    FreshMeat deserialize(byte[] bytes);

}
