package io.transwarp.core.message;

import java.util.List;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class PudgeRequest {

    private long requestId;

    private String method;

    private List<Object> params;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public List<Object> getParams() {
        return params;
    }

    public void setParams(List<Object> params) {
        this.params = params;
    }
}
