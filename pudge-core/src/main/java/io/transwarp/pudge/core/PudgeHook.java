package io.transwarp.pudge.core;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class PudgeHook {

    private long hookId;

    private String target;

    private Object[] params;

    public long getHookId() {
        return hookId;
    }

    public void setHookId(long hookId) {
        this.hookId = hookId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
