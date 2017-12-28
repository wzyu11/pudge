package io.transwarp.pudge.server;

import io.transwarp.pudge.core.PudgeException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Nirvana on 2017/12/14.
 */
public class DefaultPudgeMethod implements PudgeMethod {

    private Method method;

    private Object invoker;

    public DefaultPudgeMethod(Method method, Object invoker) {
        this.method = method;
        this.invoker = invoker;
    }

    @Override
    public Object serve(Object[] params) throws Throwable {
        try {
            return method.invoke(invoker, params);
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (IllegalAccessException e) {
            throw new PudgeException("method " + method.getName() + " can not access.");
        }
    }
}
