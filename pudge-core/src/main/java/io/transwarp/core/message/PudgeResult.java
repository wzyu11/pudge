package io.transwarp.core.message;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class PudgeResult {

    private long resultId;

    private ResultType resultType;

    private Object result;

    public long getResultId() {
        return resultId;
    }

    public void setResultId(long resultId) {
        this.resultId = resultId;
    }

    public ResultType getResultType() {
        return resultType;
    }

    public void setResultType(ResultType resultType) {
        this.resultType = resultType;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
