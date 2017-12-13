package io.transwarp.pudge.protocol;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import io.transwarp.core.message.PudgeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class RequestDecoder extends MessageToMessageDecoder<ByteBuf> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestDecoder.class);

    private static final Schema<PudgeRequest> SCHEMA = RuntimeSchema.getSchema(PudgeRequest.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        LOGGER.info("decoding: {}", JSON.toJSON(msg));
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        PudgeRequest request = ProtostuffUtils.deserializer(bytes, SCHEMA);
        out.add(request);
    }

}