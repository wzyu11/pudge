package io.transwarp.pudge.client;

/**
 * 每次都是新建一个socket, 进行请求
 * Created by zado on 2017/12/28.
 */
public class WeakSocketPudge extends AbstractSocketPudge {

    public WeakSocketPudge(PudgeServiceAddress serviceAddress) {
        super(serviceAddress);
    }

    @Override
    protected void afterProperties() {
        // 由于每次都是新的socket, 所以创建的时候不用初始化
    }

    @Override
    protected void prepareSocket() {
        super.createSocket();
    }

    @Override
    protected void handleSocketException() {
        // 一次性的, 出错了就没了
    }

    @Override
    protected void finalizeSocket() {
        super.suicide();
    }
}
