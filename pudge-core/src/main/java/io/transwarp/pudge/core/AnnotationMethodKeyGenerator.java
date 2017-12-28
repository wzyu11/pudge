package io.transwarp.pudge.core;

import io.transwarp.pudge.core.annotation.PudgeMethod;
import io.transwarp.pudge.core.annotation.PudgeService;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Nirvana on 2017/12/21.
 */
public class AnnotationMethodKeyGenerator implements MethodKeyGenerator {

    private static final Map<Method, String> METHOD_KEYS = new ConcurrentHashMap<>();

    @Override
    public String generate(Class<?> clazz, Object invokeObject, Method method) {
        String key = METHOD_KEYS.get(method);
        if (key == null) {
            key = doGenerate(clazz, invokeObject, method);
            METHOD_KEYS.put(method, key);
        }
        return key;
    }

    private String doGenerate(Class<?> clazz, Object invokeObject, Method method) {
        if (!clazz.isAnnotationPresent(PudgeService.class)) {
            throw new PudgeException("PudgeService annotation is not found.(should never happen)");
        }

        String clazzName = clazz.getAnnotation(PudgeService.class).name();

        String methodName;
        if (method.isAnnotationPresent(PudgeMethod.class)) {
            methodName = method.getAnnotation(PudgeMethod.class).name();
        } else {
            methodName = method.getName();
        }

        return clazzName + "." + methodName;
    }
}
