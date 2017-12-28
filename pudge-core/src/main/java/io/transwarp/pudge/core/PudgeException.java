package io.transwarp.pudge.core;

/**
 * Created by Nirvana on 2017/12/13.
 */
public class PudgeException extends RuntimeException {

    public PudgeException() {
    }

    public PudgeException(String message) {
        super(message);
    }

    public PudgeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PudgeException(Throwable cause) {
        super(cause);
    }

    public PudgeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
