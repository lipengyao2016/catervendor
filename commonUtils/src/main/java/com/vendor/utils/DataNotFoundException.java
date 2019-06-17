package com.vendor.utils;

public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 6958499248468627021L;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    /** 错误码 */
    private Integer errorCode;


    public DataNotFoundException(Integer errorCode, String errorMsg){
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public DataNotFoundException(Integer errorCode, String errorMsg,Throwable throwable){
        super(errorMsg,throwable);
        this.errorCode = errorCode;
    }






}
