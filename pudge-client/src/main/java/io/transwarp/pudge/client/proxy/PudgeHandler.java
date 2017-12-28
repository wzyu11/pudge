package io.transwarp.pudge.client.proxy;

import com.nirvana.common.sequence.Sequence;
import io.transwarp.pudge.client.*;
import io.transwarp.pudge.client.PudgeServiceManager;
import io.transwarp.pudge.client.factory.PooledPudgeFactory;
import io.transwarp.pudge.client.factory.PudgeFactory;
import io.transwarp.pudge.client.factory.SimplePudgeFactory;
import io.transwarp.pudge.client.pool.PudgePoolConfig;
import io.transwarp.pudge.core.*;
import io.transwarp.pudge.core.annotation.PudgeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by zado on 2017/12/22.
 */
public class PudgeHandler implements InvocationHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(PudgeHandler.class);

    private PudgeConfig pudgeConfig;

    private Map<Method, Pudge> methodPudges = new ConcurrentHashMap<>(); // method和pudge的映射

    private MethodKeyGenerator keyGenerator = DEFAULT_KEY_GENERATOR;

    private PudgeFactory pudgeFactory;

    private static final MethodKeyGenerator DEFAULT_KEY_GENERATOR = new AnnotationMethodKeyGenerator();

    private static final Sequence UID_GENERATOR = new Sequence(1, 1);

    public PudgeHandler(PudgeConfig pudgeConfig) {
        this.pudgeConfig = pudgeConfig;
        initFactory();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        PudgeHook pudgeHook = new PudgeHook();

        pudgeHook.setHookId(UID_GENERATOR.nextId());
        pudgeHook.setTarget(keyGenerator.generate(method.getDeclaringClass(), proxy, method));
        pudgeHook.setParams(args);

        FreshMeat meat = getMethodPudge(method).hook(pudgeHook);

        return dismember(meat);
    }

    private Object dismember(FreshMeat freshMeat) throws Throwable {
        if (freshMeat == null) {
            // 该死的小兵！
            throw new PudgeException("Failed to hook the meat.");
        }

        Object result;

        switch (freshMeat.getMeatType()) {
            case NORMAL:
                result = freshMeat.getMeat();
                break;
            case EXCEPTION:
                throw (Throwable) freshMeat.getMeat();
            default:
                throw new PudgeException("Pudge doesn't like this meat type.");
        }

        return result;
    }

    private Pudge getMethodPudge(Method method) {
        Pudge pudge = methodPudges.get(method);
        if (pudge == null) {
            Class<?> pudgeInterface = method.getDeclaringClass();
            if (!pudgeInterface.isAnnotationPresent(PudgeService.class)) {
                throw new PudgeException("Method's interface is not annotated by PudgeService.(should never happen)");
            }

            String serviceName = pudgeInterface.getAnnotation(PudgeService.class).name();
            pudge = pudgeFactory.createPudge(PudgeServiceManager.getServiceSource(serviceName));

            methodPudges.put(method, pudge);
        }

        return pudge;
    }

    private void initFactory() {
        if (pudgeConfig.isPoolEnabled()) {
            PudgePoolConfig pudgePoolConfig = new PudgePoolConfig.Builder()
                    .maxActive(pudgeConfig.getMaxActive())
                    .maxWait(pudgeConfig.getMaxWait())
                    .timeout(pudgeConfig.getTimeout())
                    .build();

            LOGGER.info("Pooled Pudge will hook the meat");
            pudgeFactory = new PooledPudgeFactory(pudgePoolConfig);
        } else {

            LOGGER.info("Simple Pudge will hook the meat");
            pudgeFactory = new SimplePudgeFactory();
        }
    }
}
