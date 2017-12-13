package io.transwarp.pudge.server;

import io.transwarp.core.message.PudgeRequest;
import io.transwarp.core.message.PudgeResult;
import io.transwarp.core.message.ResultType;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class SimpleInvoker implements Invoker {

    @Override
    public PudgeResult invoke(PudgeRequest request) {
        PudgeResult result = new PudgeResult();
        result.setResult(100);
        result.setResultId(request.getRequestId());
        result.setResultType(ResultType.NORMAL);
        return result;
    }

}
