package io.transwarp.pudge.server;

import io.transwarp.pudge.core.PudgeServer;

public class StartServer {

    public static void main(String[] args) {
        final PudgeServer server = new PudgeNettyServer(new SimpleMeatProvider());
        server.start();
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
    }
}
