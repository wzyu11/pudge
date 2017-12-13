package io.transwarp.pudge.protocol;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import io.transwarp.core.message.PudgeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class ResultEncoder extends MessageToByteEncoder<PudgeResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultEncoder.class);

    private static final Schema<PudgeResult> SCHEMA = RuntimeSchema.getSchema(PudgeResult.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, PudgeResult msg, ByteBuf out) {
        LOGGER.info("encoding: {}", JSON.toJSON(msg));
        byte[] bytes = ProtostuffUtils.serializer(msg, SCHEMA);
        out.writeBytes(bytes);
    }
}
