package io.transwarp.pudge.server;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.transwarp.core.message.PudgeRequest;
import io.transwarp.core.message.PudgeResult;
import io.transwarp.core.message.ResultType;
import io.transwarp.mangix.utils.PackageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class SimpleHandler extends SimpleChannelInboundHandler<PudgeRequest> {

    private Invoker invoker = new SimpleInvoker();

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, PudgeRequest msg) {
        LOGGER.info("received request: {}", JSON.toJSON(msg));
        PudgeResult result = invoker.invoke(msg);
        LOGGER.info("send result: {}", JSON.toJSON(result));
        ctx.channel().writeAndFlush(result);
    }

}
