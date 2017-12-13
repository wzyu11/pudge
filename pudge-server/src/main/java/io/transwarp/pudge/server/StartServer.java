package io.transwarp.pudge.server;

import io.netty.channel.ChannelFuture;
import io.transwarp.mangix.utils.PackageHelper;

import java.util.List;

public class StartServer {

    static {
        PackageHelper helper = new PackageHelper("");
        try {
            List<Class<?>> classes = helper.getClasses();
            for (Class<?> clazz : classes) {
                System.out.println(clazz.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        final PudgeServer server = new PudgeServer();
        ChannelFuture future = server.startServer();
        Runtime.getRuntime().addShutdownHook(new Thread(server::destroy));
        future.channel().closeFuture().syncUninterruptibly();
    }
}
