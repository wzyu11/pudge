package io.transwarp.pudge.server;

/**
 * Created by Nirvana on 2017/12/13.
 */
public interface PudgeMethodSource {

    PudgeMethod getMethod(String methodName);

    int size();

}
