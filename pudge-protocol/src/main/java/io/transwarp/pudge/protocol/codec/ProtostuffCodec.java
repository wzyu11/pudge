package io.transwarp.pudge.protocol.codec;

import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import io.transwarp.pudge.core.FreshMeat;
import io.transwarp.pudge.core.PudgeHook;
import io.transwarp.pudge.protocol.ProtostuffUtils;

/**
 * Created by Nirvana on 2017/12/21.
 */
public class ProtostuffCodec implements PudgeCodec {

    private static final Schema<PudgeHook> HOOK_SCHEMA = RuntimeSchema.getSchema(PudgeHook.class);
    private static final Schema<FreshMeat> MEAT_SCHEMA = RuntimeSchema.getSchema(FreshMeat.class);

    @Override
    public byte[] serialize(PudgeHook hook) {
        return ProtostuffUtils.serializer(hook, HOOK_SCHEMA);
    }

    @Override
    public FreshMeat deserialize(byte[] bytes) {
        return ProtostuffUtils.deserializer(bytes, MEAT_SCHEMA);
    }
}
