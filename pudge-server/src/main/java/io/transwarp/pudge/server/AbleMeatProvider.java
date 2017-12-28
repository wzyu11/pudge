package io.transwarp.pudge.server;

import io.transwarp.pudge.core.PudgeException;
import io.transwarp.pudge.core.PudgeHook;
import io.transwarp.pudge.core.FreshMeat;
import io.transwarp.pudge.core.MeatType;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class AbleMeatProvider implements MeatProvider {

    private PudgeMethodSource pudgeMethodSource;

    public AbleMeatProvider(PudgeMethodSource pudgeMethodSource) {
        this.pudgeMethodSource = pudgeMethodSource;
    }

    @Override
    public FreshMeat provide(PudgeHook request) {
        PudgeMethod ability = pudgeMethodSource.getMethod(request.getTarget());
        if (ability == null) {
            return new FreshMeat(request.getHookId(), MeatType.EXCEPTION, new PudgeException("method " + request.getTarget() + " not found."));
        }
        try {
            Object result = ability.serve(request.getParams());
            return new FreshMeat(request.getHookId(), MeatType.NORMAL, result);
        } catch (Throwable throwable) {
            return new FreshMeat(request.getHookId(), MeatType.EXCEPTION, throwable);
        }
    }

}
