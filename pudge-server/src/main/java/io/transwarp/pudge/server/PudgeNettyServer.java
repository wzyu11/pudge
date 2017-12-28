package io.transwarp.pudge.server;

import io.transwarp.pudge.core.FreshMeat;
import io.transwarp.pudge.core.PudgeHook;
import io.transwarp.pudge.protocol.netty.AbstractNettyServer;

/**
 * Created by Nirvana on 2017/12/15.
 */
public class PudgeNettyServer extends AbstractNettyServer {

    private MeatProvider provider;

    public PudgeNettyServer(MeatProvider provider) {
        this.provider = provider;
    }

    public PudgeNettyServer(int port, MeatProvider provider) {
        super(port);
        this.provider = provider;
    }

    @Override
    protected FreshMeat doServe(PudgeHook hook) {
        return provider.provide(hook);
    }
}
