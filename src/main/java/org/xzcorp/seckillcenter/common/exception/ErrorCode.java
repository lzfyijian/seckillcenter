package org.xzcorp.seckillcenter.common.exception;

public enum ErrorCode {

    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT","参数非法",ErrorType.BIZ),

    ORDER_NOT_BUSINESS_EQUAL("ORDER_NOT_BUSINESS_EQUAL","订单不幂等",ErrorType.BIZ),
    STOCK_EXCEED("STOCK_EXCEED","库存超卖",ErrorType.BIZ),

    ACTIVITY_EXIST("ACTIVITY_EXIST","活动已存在",ErrorType.BIZ),
    SYSTEM_ERROR("SYSTEM_ERROR","系统异常",ErrorType.SYS),

    ;

    private String code;

    private String name;

    private ErrorType errorType;

    ErrorCode(String code, String name,ErrorType errorType) {
        this.code = code;
        this.name = name;
        this.errorType = errorType;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ErrorCode getByCode(String errorCode) {
        if (errorCode == null) {
            return null;
        }
        for (ErrorCode error : values()) {
            if (error.getCode().equals(errorCode)) {
                return error;
            }
        }
        return null;
    }


    private enum ErrorType{
        BIZ("业务类型"),
        SYS("系统类型"),
        ;
        String typeName;

        ErrorType(String typeName){
            this.typeName=typeName;
        }

    }
}
