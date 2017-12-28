package io.transwarp.pudge.core;

import java.lang.reflect.Method;

/**
 * Created by Nirvana on 2017/12/21.
 */
public interface MethodKeyGenerator {

    String generate(Class<?> clazz, Object invokeObject, Method method);

}
