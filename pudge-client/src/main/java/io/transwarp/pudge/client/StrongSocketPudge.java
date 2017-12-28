package io.transwarp.pudge.client;

/**
 * 持久一些的pudge, 会保持连接
 * Created by zado on 2017/12/25.
 */
public class StrongSocketPudge extends AbstractSocketPudge {

    public StrongSocketPudge(PudgeServiceAddress serviceAddress) {
        super(serviceAddress);
    }

    @Override
    protected void afterProperties() {
        super.createSocket();
    }

    @Override
    protected void prepareSocket() {
        if (socket == null) {
            super.createSocket();
        }
    }

    @Override
    protected void handleSocketException() {
        super.suicide();
    }

    @Override
    protected void finalizeSocket() {
        // 保持连接的话, 不需要做什么
    }
}
