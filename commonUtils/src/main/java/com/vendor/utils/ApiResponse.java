package com.vendor.utils;

public class ApiResponse {

    private static String ERRMSG_SUC = "success";
    private static Integer ERRCODE_SUC = 0;

    private static ApiResponse sucApiResonse = new ApiResponse(ERRCODE_SUC,ERRMSG_SUC);

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    private Integer errCode;

    private String errMessage;

    public ApiResponse(Integer errCode,String errMessage)
    {
        this.errCode = errCode;
        this.errMessage = errMessage;
    }



    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public static ApiResponse getSucedResponse()
    {
        return ApiResponse.sucApiResonse;
    }


}
