package com.vendor.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="API接口返回对象")
public class ApiResponse<T> {

    private static String ERRMSG_SUC = "success";
    private static Integer ERRCODE_SUC = 0;

    private static ApiResponse sucApiResonse = new ApiResponse(ERRCODE_SUC,ERRMSG_SUC);

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }


    @ApiModelProperty(value = "错误码,0为成功，其它为失败。")
    private Integer errCode;

    @ApiModelProperty(value = "错误消息,success为成功，其它为失败。")
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @ApiModelProperty(value = "返回的具体业务数据。")
    private T data;


}
