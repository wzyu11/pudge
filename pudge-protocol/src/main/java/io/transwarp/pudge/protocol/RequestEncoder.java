package io.transwarp.pudge.protocol;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import io.transwarp.core.message.PudgeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class RequestEncoder extends MessageToByteEncoder<PudgeRequest> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestEncoder.class);

    private static final Schema<PudgeRequest> SCHEMA = RuntimeSchema.getSchema(PudgeRequest.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, PudgeRequest msg, ByteBuf out) {
        LOGGER.info("encoding: {}", JSON.toJSON(msg));
        byte[] bytes = ProtostuffUtils.serializer(msg, SCHEMA);
        out.writeBytes(bytes);
    }
}
