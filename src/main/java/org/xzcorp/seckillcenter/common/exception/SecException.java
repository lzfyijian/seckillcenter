package org.xzcorp.seckillcenter.common.exception;

public class SecException extends RuntimeException{

    private ErrorCode errorCode;

    public SecException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public SecException( ErrorCode errorCode,String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
