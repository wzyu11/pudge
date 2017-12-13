package io.transwarp.pudge.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.transwarp.pudge.protocol.PudgeFrameDecoder;
import io.transwarp.pudge.protocol.RequestDecoder;
import io.transwarp.pudge.protocol.ResultEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class PudgeServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(PudgeServer.class);

    private int port = 32222;

    private Channel channel;

    private EventLoopGroup bossGroup;

    private EventLoopGroup workerGroup;

    PudgeServer() {
    }

    public PudgeServer(int port) {
        this.port = port;
    }

    ChannelFuture startServer() {

        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                // 解码器
                pipeline.addLast("framer", new PudgeFrameDecoder());
                pipeline.addLast("decoder", new RequestDecoder());
                pipeline.addLast("prepender", new ProtobufVarint32LengthFieldPrepender());
                pipeline.addLast("encoder", new ResultEncoder());
                pipeline.addLast("handler", new SimpleHandler());
            }
        });

        try {
            // 服务器绑定端口监听
            ChannelFuture f = b.bind(port).sync();
            channel = f.channel();
            LOGGER.info("服务器已启动，端口:" + port);
            return f;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    void destroy() {
        if (channel != null) {
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
