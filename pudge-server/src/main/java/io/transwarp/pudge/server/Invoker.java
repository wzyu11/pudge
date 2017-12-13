package io.transwarp.pudge.server;

import io.transwarp.core.message.PudgeRequest;
import io.transwarp.core.message.PudgeResult;

/**
 * Created by Nirvana on 2017/12/13.
 */
public interface Invoker {

    PudgeResult invoke(PudgeRequest request);

}
