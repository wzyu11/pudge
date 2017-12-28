package io.transwarp.pudge.server;

import io.transwarp.pudge.core.PudgeHook;
import io.transwarp.pudge.core.FreshMeat;

/**
 * Created by Nirvana on 2017/12/13.
 */
public interface MeatProvider {

    FreshMeat provide(PudgeHook request);

}
