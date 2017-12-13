package io.transwarp.pudge.client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.transwarp.core.message.PudgeRequest;
import io.transwarp.pudge.protocol.PudgeFrameDecoder;
import io.transwarp.pudge.protocol.RequestEncoder;
import io.transwarp.pudge.protocol.ResultDecoder;

import java.util.Arrays;

public class PushClient {


    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                                 @Override
                                 protected void initChannel(SocketChannel ch) {
                                     ChannelPipeline pipeline = ch.pipeline();
                                     // 解码器
                                     pipeline.addLast("framer", new PudgeFrameDecoder());
                                     pipeline.addLast("decoder", new ResultDecoder());
                                     pipeline.addLast("handler", new SimpleHandler());
                                     pipeline.addLast("prepender", new ProtobufVarint32LengthFieldPrepender());
                                     pipeline.addLast("encoder", new RequestEncoder());
                                 }
                             }
                    );

            String host = "127.0.0.1";
            int port = 32222;
            Channel ch = b.connect(host, port).sync().channel();
            PudgeRequest request = new PudgeRequest();
            request.setMethod("fuck");
            request.setRequestId(12314566);
            request.setParams(Arrays.asList(13, 45, 234));
            ch.writeAndFlush(request);
            while (true) {
            }
        } finally {
            group.shutdownGracefully();
        }
    }

}
