package io.transwarp.pudge.spring.server;

import io.transwarp.pudge.client.proxy.PudgeHandler;
import io.transwarp.pudge.core.AnnotationMethodKeyGenerator;
import io.transwarp.pudge.core.PudgeException;
import io.transwarp.pudge.server.DefaultPudgeMethod;
import io.transwarp.pudge.core.MethodKeyGenerator;
import io.transwarp.pudge.server.PudgeMethod;
import io.transwarp.pudge.server.PudgeMethodSource;
import io.transwarp.pudge.spring.PudgeServiceScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class SpringPudgeMethodSource implements PudgeMethodSource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringPudgeMethodSource.class);

    private Map<String, PudgeMethod> methodCache = new ConcurrentHashMap<>();

    private ApplicationContext applicationContext;

    private static final PudgeServiceScanner scanner = new PudgeServiceScanner();

    private String serviceName;

    private Collection<Class<?>> pudgeInterfaces;

    private static final MethodKeyGenerator DEFAULT_KEY_GENERATOR = new AnnotationMethodKeyGenerator();

    private MethodKeyGenerator keyGenerator = DEFAULT_KEY_GENERATOR;

    public SpringPudgeMethodSource(String serviceName, ApplicationContext applicationContext) {
        this.serviceName = serviceName;
        pudgeInterfaces = scanner.scan(serviceName);
        this.applicationContext = applicationContext;
        build();
    }

    private void build() {
        Collection<Class<?>> interfaces = pudgeInterfaces;
        for (Class<?> clazz : interfaces) {
            Map<String, ?> map = applicationContext.getBeansOfType(clazz);
            if (map.size() == 0) {
                throw new RuntimeException("The implement of MeatProvider Service: " + clazz.getName() + " not found.");
            }
            if (map.size() > 1) {
                throw new RuntimeException("More than one implements of MeatProvider Service: " + clazz.getName() + " found.");
            }
            Object invokeObject = map.values().iterator().next();
            if (Proxy.isProxyClass(invokeObject.getClass())) {
                if (Proxy.getInvocationHandler(invokeObject) instanceof PudgeHandler) {
                    throw new PudgeException("The implement of MeatProvider Service " + clazz.getName() + " can not be the PudgeHandler.");
                }
            }
            for (Method method : clazz.getMethods()) {
                PudgeMethod ability = new DefaultPudgeMethod(method, invokeObject);
                String methodKey = keyGenerator.generate(clazz, invokeObject, method);
                LOGGER.debug("find method: {}", methodKey);
                methodCache.put(methodKey, ability);
            }
        }
    }

    @Override
    public PudgeMethod getMethod(String methodName) {
        return methodCache.get(methodName);
    }

    @Override
    public int size() {
        return methodCache.size();
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setKeyGenerator(MethodKeyGenerator keyGenerator) {
        this.keyGenerator = keyGenerator;
    }
}
