package io.transwarp.pudge.client;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.transwarp.core.message.PudgeResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class SimpleHandler extends SimpleChannelInboundHandler<PudgeResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PudgeResult msg) {
        LOGGER.info("received result: {}", JSON.toJSON(msg));
    }
}
