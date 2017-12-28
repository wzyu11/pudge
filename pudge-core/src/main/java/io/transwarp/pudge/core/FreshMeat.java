package io.transwarp.pudge.core;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class FreshMeat {

    private long meatId;

    private MeatType meatType;

    private Object meat;

    public FreshMeat() {
    }

    public FreshMeat(long meatId, MeatType meatType, Object meat) {
        this.meatId = meatId;
        this.meatType = meatType;
        this.meat = meat;
    }

    public long getMeatId() {
        return meatId;
    }

    public void setMeatId(long meatId) {
        this.meatId = meatId;
    }

    public MeatType getMeatType() {
        return meatType;
    }

    public void setMeatType(MeatType meatType) {
        this.meatType = meatType;
    }

    public Object getMeat() {
        return meat;
    }

    public void setMeat(Object meat) {
        this.meat = meat;
    }
}
