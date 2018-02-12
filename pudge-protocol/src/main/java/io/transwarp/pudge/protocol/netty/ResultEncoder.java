package io.transwarp.pudge.protocol.netty;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import io.transwarp.pudge.core.FreshMeat;
import io.transwarp.pudge.protocol.ProtostuffUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class ResultEncoder extends MessageToByteEncoder<FreshMeat> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResultEncoder.class);

    private static final Schema<FreshMeat> SCHEMA = RuntimeSchema.getSchema(FreshMeat.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, FreshMeat msg, ByteBuf out) {
        LOGGER.debug("encoding: {}", JSON.toJSON(msg));
        byte[] bytes = ProtostuffUtils.serializer(msg, SCHEMA);
        out.writeBytes(bytes);
    }
}
