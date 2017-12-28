package io.transwarp.pudge.client;

import io.transwarp.pudge.core.FreshMeat;
import io.transwarp.pudge.core.PudgeHook;
import io.transwarp.pudge.protocol.codec.ProtostuffCodec;
import io.transwarp.pudge.protocol.codec.PudgeCodec;

/**
 * Created by Nirvana on 2017/12/21.
 */
public abstract class SimplePudge implements Pudge {

    private PudgeCodec codec = new ProtostuffCodec();

    @Override
    public FreshMeat hook(PudgeHook hook) {
        byte[] hookData = codec.serialize(hook);
        byte[] meatData = fire(hookData);
        return codec.deserialize(meatData);
    }

    public abstract boolean isAlive();

    public abstract void suicide();

    protected abstract byte[] fire(byte[] hookData);
}
