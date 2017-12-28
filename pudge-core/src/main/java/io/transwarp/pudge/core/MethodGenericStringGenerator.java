package io.transwarp.pudge.core;

import java.lang.reflect.Method;

/**
 * Created by Nirvana on 2017/12/21.
 */
public class MethodGenericStringGenerator implements MethodKeyGenerator {

    @Override
    public String generate(Class<?> clazz, Object invokeObject, Method method) {
        return method.toGenericString();
    }
}
