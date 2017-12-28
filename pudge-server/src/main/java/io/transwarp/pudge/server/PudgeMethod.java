package io.transwarp.pudge.server;

/**
 * Created by Nirvana on 2017/12/14.
 */
public interface PudgeMethod {

    Object serve(Object[] params) throws Throwable;

}