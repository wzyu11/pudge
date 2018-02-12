package io.transwarp.pudge.protocol.netty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import io.transwarp.pudge.core.FreshMeat;
import io.transwarp.pudge.protocol.ProtostuffUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class ResultDecoder extends MessageToMessageDecoder<ByteBuf> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultDecoder.class);

    private static final Schema<FreshMeat> SCHEMA = RuntimeSchema.getSchema(FreshMeat.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        LOGGER.debug("decoding: {}", JSON.toJSON(msg));
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        FreshMeat result = ProtostuffUtils.deserializer(bytes, SCHEMA);
        out.add(result);
    }
}
