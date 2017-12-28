package io.transwarp.pudge.server;

import io.transwarp.pudge.core.PudgeHook;
import io.transwarp.pudge.core.FreshMeat;
import io.transwarp.pudge.core.MeatType;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class SimpleMeatProvider implements MeatProvider {

    @Override
    public FreshMeat provide(PudgeHook request) {
        FreshMeat result = new FreshMeat();
        result.setMeat(100);
        result.setMeatId(request.getHookId());
        result.setMeatType(MeatType.NORMAL);
        return result;
    }

}
