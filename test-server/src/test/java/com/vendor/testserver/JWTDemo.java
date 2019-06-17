package com.vendor.testserver;


import com.vendor.queryvo.user.UserLoginInfo;
import com.vendor.utils.GsonUtils;
import com.vendor.utils.JWTUtils;

public class JWTDemo {

    //private  static JWTUtils jwtUtils = new JWTUtils();
    public static void main(String[] args) {

        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setMerchantId(132457422l);
        userLoginInfo.setMerchantName("yuefang");
        userLoginInfo.setUserId(123213513541l);
        userLoginInfo.setUserName("lipy");

        String loginStr = GsonUtils.ToJson(userLoginInfo,UserLoginInfo.class);
        String token = JWTUtils.createTokenWithClaim(loginStr);
         System.out.println("toke:" + token);
        String decodeToken = JWTUtils.verifyToken(token);
        System.out.println("decodeToken:" + decodeToken);
    }
}
