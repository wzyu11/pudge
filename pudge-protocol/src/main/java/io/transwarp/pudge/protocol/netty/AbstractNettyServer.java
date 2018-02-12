package io.transwarp.pudge.protocol.netty;

import com.alibaba.fastjson.JSON;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.transwarp.pudge.core.PudgeHook;
import io.transwarp.pudge.core.FreshMeat;
import io.transwarp.pudge.core.PudgeServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Nirvana on 2017/12/14.
 */
public abstract class AbstractNettyServer implements PudgeServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PudgeServer.class);

    private static final int DEFAULT_PORT = 32222;

    private int port;

    private int threadPoolSize;

    private Channel channel;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    private ThreadPoolExecutor executor;

    public AbstractNettyServer() {
        this(DEFAULT_PORT);
    }

    public AbstractNettyServer(int port) {
        this(port, Runtime.getRuntime().availableProcessors() * 2);
    }

    public AbstractNettyServer(int port, int threadPoolSize) {
        this.port = port;
        this.threadPoolSize = threadPoolSize;
    }

    protected abstract FreshMeat doServe(PudgeHook hook);

    @Override
    public void start() {
        executor = (ThreadPoolExecutor) PudgeThreadPool.getExecutor(threadPoolSize, -1);
        LOGGER.info("Generate Pudge netty server worker with thread pool size: {}", threadPoolSize);

        bossGroup = new NioEventLoopGroup();
        // one worker more executor
        workerGroup = new NioEventLoopGroup(1, new PudgeThreadFactory(), SelectorProvider.provider());

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                // 解码器
                pipeline.addLast("framer", new ProtobufVarint32FrameDecoder());
                pipeline.addLast("decoder", new RequestDecoder());
                pipeline.addLast("prepender", new ProtobufVarint32LengthFieldPrepender());
                pipeline.addLast("encoder", new ResultEncoder());
                pipeline.addLast("handler", new SimpleChannelInboundHandler<PudgeHook>() {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, PudgeHook msg) {
                        executor.submit(() -> {
                            LOGGER.debug("received request: {}", JSON.toJSON(msg));
                            FreshMeat result = doServe(msg);
                            LOGGER.debug("send result: {}", JSON.toJSON(result));
                            ctx.channel().writeAndFlush(result);
                        });
                    }
                });
            }
        });

        try {
            ChannelFuture f = b.bind(port).sync();
            channel = f.channel();
            LOGGER.info("netty server started: " + port);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void stop() {
        if (channel != null) {
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
