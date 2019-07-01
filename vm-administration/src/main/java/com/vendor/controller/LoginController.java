package com.vendor.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.vendor.admin.User;
import com.vendor.admin.UserLoginVo;
import com.vendor.admin.UserRoleExt;
import com.vendor.common.ECode;
import com.vendor.common.ELoginChannel;
import com.vendor.common.MessageException;
import com.vendor.common.RestfulResponse;
import com.vendor.config.UserLoginLogProperty;
import com.vendor.config.VerifyCodeUtils;
import com.vendor.config.VerifyPicProperty;
import com.vendor.core.RequestHelper;
/*
import com.vendor.model.login.AccountVo;
import com.vendor.model.login.SessionLoginVo;
import com.vendor.model.login.TokenLoginVo;
import com.vendor.model.login.UserLoginLog;
import com.vendor.service.IUserLoginService;
*/
import com.vendor.service.IUserService;
import com.vendor.util.RedisUtils;
import com.vendor.utils.*;
import com.vendor.vo.UserLoginInfoVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;


@Api(tags = "登录管理")
@RequestMapping("/login")
@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private VerifyPicProperty verifyPicProperty;

    @Autowired
    private UserLoginLogProperty userLoginLogProperty;

    @Autowired
    private IUserService userService;

    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "token方式登录验证", notes = "适用于包括iOS、Android、wap、pad和PC端登录")
    @PostMapping("/loginForToken")
    public ApiResponse<UserLoginVo> userLoginForToken(
            HttpServletRequest request,HttpServletResponse response,
             @ApiParam(name = "用户登录参数", required = true)
             @RequestBody User loginVo) {

        UserRoleExt oldUserInfo = null;
        ApiResponse<UserLoginVo> userLoginApiResponse =  ApiResponse.getSucedResponse();
        //ApiResponse<UserLoginVo> userLoginApiResponse = new ApiResponse<UserLoginVo>();
        try {
            oldUserInfo = userService.login(loginVo);
        } catch (MessageException e) {
            logger.error("获取用户信息失败:" + e.getMessage(),e);
            userLoginApiResponse.setErrCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
            userLoginApiResponse.setErrMessage("获取用户信息失败:" + e.getMessage());
            return userLoginApiResponse;
        } catch (Exception e) {
            logger.error("获取用户信息失败:" + e.getMessage(),e);
            userLoginApiResponse.setErrCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
            userLoginApiResponse.setErrMessage("获取用户信息失败！");
            return userLoginApiResponse;
        }
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUserRoleExt(oldUserInfo);

        String fromBy = RequestHelper.getFromBy(request);

        User loginLog = new User();
        loginLog.setId(oldUserInfo.getId());
        loginLog.setLastLoginIp(RequestHelper.getIpAddr(request));
        loginLog.setLastLoginChannel(fromBy);
        loginLog.setLastLoginTime(new Date());
        loginLog.setUserLoginId(loginVo.getUserLoginId());
        boolean bRet = userService.update(loginLog);

        // 清除该用户指定终端的所有缓存
        redisUtils.destoryUserInfoByUserId(fromBy, loginVo.getUserLoginId());

        String token = this.loginSetToken(response, oldUserInfo);
        userLoginVo.setToken(token);

        redisUtils.setToken(fromBy, token, oldUserInfo);
        userLoginApiResponse.setData(userLoginVo);

        return userLoginApiResponse;
    }


    private String loginSetToken(HttpServletResponse response,Object canUserLogin){
        //返回Token
        String tokenWithClaim = JWTUtils.createTokenWithClaim(
                JSONObject.toJSONString(canUserLogin));
        response.setHeader("token",tokenWithClaim);
        return tokenWithClaim;
    }


 /*

    *//**
     * 生成验证码及验证码图片
     *
     * @param request
     *//*
    @ApiOperation(value = "生成验证码", notes = "")
    @GetMapping("/buildRandPic")
    public RestfulResponse<String> buildVerifyPic(HttpServletRequest request) {
        RestfulResponse<String> restful = new RestfulResponse<String>("");
        String sessionId = request.getSession().getId();
        int count = userLoginService.recordLoginCount(sessionId);
        if (count < 3) {
            return restful;
        }
        String verifyCode = VerifyCodeUtils.generateVerifyCode(verifyPicProperty.getLetterQty());
        request.getSession().setAttribute("verifyCode", verifyCode);
        try {
            String imageString = VerifyCodeUtils.Base64Image(verifyPicProperty.getWidth(),
                    verifyPicProperty.getHeight(), verifyCode);
            restful.setData(imageString);
        } catch (MessageException e) {
            logger.error("生成验证码失败{}", e.getMessage());
            restful.setCode(ECode.CICD_BUILD_CREATE_FAILED.getCode());
            restful.setMessage("生成验证码失败" + e.getMessage());
            return restful;
        } catch (Exception e) {
            restful.setCode(ECode.CICD_BUILD_CREATE_FAILED.getCode());
            e.printStackTrace();
            restful.setMessage("生成验证码失败！");
        }

        return restful;
    }

    *//**
     * session方式用户登录
     *
     * @param request
     * @param loginVo
     * @return
     *//*
    @ApiOperation(value = "session方式登录验证(仅适用于浏览器端)", notes = "用户名或密码输入连续错误三次后需用验证码验证")
    @ApiResponses({ @ApiResponse(code = 1000, message = "登录成功"),
            @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对") })
    @PostMapping("/loginForSession")
    public RestfulResponse<UserLoginInfoVo> userLoginForSession(HttpServletRequest request,
                                                                HttpServletResponse response,
                                                                @ApiParam(name = "用户登录参数",
                                                               required = true) @RequestBody SessionLoginVo loginVo) {
        RestfulResponse<UserLoginInfoVo> restful = new RestfulResponse<UserLoginInfoVo>(new UserLoginInfoVo());
        if (loginVo.getUserLoginId() == null || "".equals(loginVo.getUserLoginId())) {
            restful.setCode(ECode.ARG_ERROR.getCode());
            restful.setMessage("用户名为空！");
            return restful;
        }
        for (String a : StringUtil.INVALID_CHARACTER) {
            if (loginVo.getUserLoginId().contains(a)) {
                restful.setCode(ECode.ARG_ERROR.getCode());
                restful.setMessage("用户名含有无效字符！");
                return restful;
            }
        }
        String token = request.getHeader("token");
        if(token != null && token != ""){
            String verifyToken = JWTUtils.verifyToken(token);
            UserLoginInfoVo userLoginInfoVo = JSONObject.parseObject(verifyToken, UserLoginInfoVo.class);
            String userLoginId = userLoginInfoVo.getUserLoginId();
            if (userLoginId.equals(loginVo.getUserLoginId())) { // 如果已登录Session记录仍然存在，则跳过检查，直接登录
                logger.info("session 存在，无需再次登录！" + loginVo.getUserLoginId());
//                restful.setMessage("session 存在，无需再次登录！" + loginVo.getUserLoginId());
                restful.setData(userLoginInfoVo);

                //刷新有效时间
                this.loginSetToken(response,userLoginInfoVo);
                return restful;
            } else if (!"".equals(userLoginId)) {
                response.setHeader("token",null);
            }
        }

        String sessionId = request.getSession().getId();

        int count = userLoginService.recordLoginCount(sessionId);
        if (count >= 3) {
            if (loginVo.getVerifyCode() == null || "".equals(loginVo.getVerifyCode())) {
                restful.setCode(ECode.ARG_ERROR.getCode());
                restful.setMessage("请输入验证码！");
                return restful;
            }

            String sessionCode = DataConvertUtil.getString(request.getSession().getAttribute("verifyCode"));
            if ("".equals(sessionCode)) {
                restful.setCode(ECode.ARG_ERROR.getCode());
                restful.setMessage("验证码已过期，请重新获取！");
                return restful;
            }

            if (!loginVo.getVerifyCode().equalsIgnoreCase(sessionCode)) {
                restful.setCode(ECode.ARG_ERROR.getCode());
                restful.setMessage("验证码错误！");
                return restful;
            }
        }

        String canUserLogin = null;
        try {
            String pwd = DigestUtils.sha256Hex(loginVo.getPassword());
            canUserLogin = userLoginService.canUserLogin(loginVo.getUserLoginId(), pwd);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("登录失败:" + e.getMessage());
            restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());

            count = userLoginService.updateLoginCount(sessionId, count);
            if (count >= 3) {
                // 生成验证码和图片
                RestfulResponse<String> image = this.buildVerifyPic(request);
                if (image.getCode() != ECode.SUCCESS.getCode()) {
                    restful.setMessage("登录失败！获取验证码失败！");
                    return restful;
                }

                restful.getData().setImageString(image.getData());
            }
            restful.setMessage("登录失败！");
            return restful;
        }

        if (canUserLogin != null) {
            count = userLoginService.updateLoginCount(sessionId, count);
            if (count >= 3) {
                // 生成验证码和图片
                RestfulResponse<String> image = this.buildVerifyPic(request);
                if (image.getCode() != ECode.SUCCESS.getCode()) {
                    restful.setMessage(canUserLogin + "!获取验证码失败！");
                    return restful;
                }

                restful.getData().setImageString(image.getData());
            }

            restful.setMessage(canUserLogin);
            return restful;
        }
        userLoginService.deleteLoginCount(sessionId);

        UserLoginInfoVo vo = null;
        try {
            vo = userLoginService.findUserLoginInfo(loginVo.getUserLoginId());
        } catch (MessageException e) {
            logger.error("获取用户信息失败:" + e.getMessage(),e);
            restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
            restful.setMessage(e.getMessage());
            return restful;
        } catch (Exception e) {
            logger.error("获取用户信息失败:" + e.getMessage(),e);
            restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
            restful.setMessage("获取用户信息失败！");
            return restful;
        }

        if (vo == null) {
            restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
            restful.setMessage("获取用户信息失败，请与管理员联系");
            return restful;
        }

        UserLoginLog loginLog = new UserLoginLog();
        String clientId = RequestHelper.getIpAddr(request);
        loginLog.setLastLoginIP(clientId);
        loginLog.setLastLoginChannel(ELoginChannel.PC.name());
        loginLog.setUserLoginId(loginVo.getUserLoginId());

        try {
            userLoginService.updateUserLoginInfo(loginLog);
        } catch (MessageException e) {
            logger.error("记录登录信息失败:" + e.getMessage(),e);
        } catch (Exception e) {
            logger.error("记录登录信息失败:" + e.getMessage(),e);
        }

//        vo.setCurrentPwd(loginVo.getPassword());
        restful.setData(vo);

        // 清除该用户指定终端的所有缓存
        redisUtils.destoryUserInfoByUserId("PC", loginVo.getUserLoginId());
        redisUtils.setToken("PC", token, vo);

        //返回Token
        this.loginSetToken(response,vo);
        return restful;
    }



    *//**
     * session方式退出登录
     *
     * @param request
     * @param response
     * @throws IOException
     *//*
    @ApiOperation(value = "退出登录", notes = "")
    @GetMapping("/loginOutForSession")
    public RestfulResponse<Void> userLoginOutForSession(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession();
        // 清空session缓存
        redisUtils.destoryUserInfoBySession(session.getId());
        response.setHeader("token",null);
        return new RestfulResponse<Void>();
    }

    *//**
     * token方式退出登录
     *
     * @throws IOException
     *//*
    @ApiImplicitParam(paramType = "path", name = "userLoginId", dataType = "String", value = "用户登录账号",
            required = true, defaultValue = "")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"),
            @ApiResponse(code = 1000, message = "成功") })
    @ApiOperation(value = "退出登录", notes = "")
    @GetMapping("/loginOutForToken/{userLoginId}")
    public RestfulResponse<Void> userLoginOutForToken(HttpServletRequest request, HttpServletResponse response,
                                                      @PathVariable String userLoginId)
            throws IOException {
        // 清除该用户的所有缓存
        redisUtils.destoryUserInfoByUserId(RequestHelper.getFromBy(request), userLoginId);
        response.setHeader("token",null);
        return new RestfulResponse<Void>();
    }

    @ApiOperation(value = "修改用户密码")
    @PutMapping("/userPass")
    public RestfulResponse<Void> updateUserPass(HttpServletRequest request, @RequestBody AccountVo vo)
            throws Exception {
        String pwd = null;
        RestfulResponse<Void> restful = new RestfulResponse<Void>();
        checkPassword(vo.getNewPwd(), restful);
        if (restful.getCode() != ECode.SUCCESS.getCode()) {
            return restful;
        }

        UserLoginInfoVo userVo = userLoginService.findUserLoginInfo(vo.getUserLoginId());

        boolean flag = true;
        // 管理员可直接修改密码
        UserLoginInfoVo loginVo = (UserLoginInfoVo) request.getAttribute("loginUserInfo");
        if (loginVo == null || !"Y".equals(loginVo.getIfAdmin())) {
            flag = false;
            // 验证账号密码
            String canUserLogin = null;
            try {
                if (vo.getOldPwd() == null || "".equals(vo.getOldPwd())) {
                    // 未提供旧密码则根据手机验证码来判断
                    if (vo.getVerifyCode() == null || "".equals(vo.getVerifyCode())) {
                        restful.setMessage("请提供原密码或手机验证码！");
                        restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
                        return restful;
                    }

                    String code = redisUtils.getUserVerifyCode(vo.getUserLoginId());
                    if (code == null || "".equals(code)) {
                        restful.setCode(ECode.CICD_BUILD_QUERY_DETAILS_FAILED.getCode());
                        restful.setMessage("验证码已过期！");
                        return restful;
                    } else if (!code.equals(vo.getVerifyCode())) {
                        restful.setCode(ECode.CICD_BUILD_QUERY_DETAILS_FAILED.getCode());
                        restful.setMessage("验证码有误！");
                        return restful;
                    }

                    pwd = userVo.getShaHexPwd();
                } else {
                    pwd = DigestUtils.sha256Hex(vo.getOldPwd());
                }

                // 考虑到前端可能传的值是登录账号或手机号
                canUserLogin = userLoginService.canUserLogin(userVo.getUserLoginId(), pwd);
                if (canUserLogin != null && !"".equals(canUserLogin)) {
                    restful.setMessage("原密码错误！");
                    restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
                    return restful;
                }
            } catch (MessageException e) {
                logger.error("用户查询失败:" + e.getMessage());
                restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
                restful.setMessage("用户查询失败:" + e.getMessage());
                return restful;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("用户查询失败:" + e.getMessage());
                restful.setMessage("用户查询失败!");
                restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
                return restful;
            }
        }

        try {
            // 考虑到前端可能传的值是登录账号或手机号
            vo.setUserLoginId(userVo.getUserLoginId());
            userLoginService.updateUserPwd(vo);
        } catch (MessageException e) {
            logger.error("修改密码失败:" + e.getMessage());
            restful.setCode(ECode.UPDATE_ERROR.getCode());
            restful.setMessage("修改密码失败:" + e.getMessage());
            return restful;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("修改密码失败:" + e.getMessage());
            restful.setMessage("修改密码失败!");
            restful.setCode(ECode.UPDATE_ERROR.getCode());
            return restful;
        }

        // 修改密码成功，清除登陆信息
        String fromBy = RequestHelper.getFromBy(request);
        if (ELoginChannel.PC.name().equals(fromBy)) {
            if (!flag) {
                HttpSession session = request.getSession();
                session.removeAttribute("userLoginId");
                session.removeAttribute("loginUserInfo");
            }
        } else {
            redisUtils.destoryUserInfoByUserId(RequestHelper.getFromBy(request), vo.getUserLoginId());
        }
        return restful;
    }

    @ApiOperation(value = "获取手机验证码", notes = "返回加密后的密码，在修改密码时用户不用输入旧密码，前端程序直接将这个值传回给后台")
    @ApiResponses({ @ApiResponse(code = 400, message = "请求参数没填好"),
            @ApiResponse(code = 100, message = "请求路径没有或页面跳转路径不对"), @ApiResponse(code = 1000, message = "获取验证码成功") })
    @ApiImplicitParam(paramType = "path", name = "userLoginId", dataType = "String", value = "用户登录账号", required = true, defaultValue = "")
    @GetMapping("/verifyCodes/{userLoginId}")
    public RestfulResponse<String> getVerifyCode(@PathVariable("userLoginId") String userLoginId) {
        RestfulResponse<String> restful = new RestfulResponse<String>();
        try {
            String verifyCode = userLoginService.getVerifyCode(userLoginId);
            restful.setData("已发送" + verifyCode.length() + "位数长度的验证码到你手机,请于五分钟之内输入！");

            // 将验证码放入缓存，保存五分钟
            redisUtils.setUserVerifyCode(userLoginId, verifyCode, 5);
        } catch (MessageException e) {
            logger.error(e.getMessage());
            restful.setCode(ECode.CICD_BUILD_QUERY_DETAILS_FAILED.getCode());
            restful.setMessage(e.getMessage());
            return restful;
        } catch (Exception e) {
            logger.error(e.getMessage());
            restful.setCode(ECode.CICD_BUILD_QUERY_DETAILS_FAILED.getCode());
            restful.setMessage(e.getMessage());
        }
        return restful;
    }
//
//    @NoPermissionAuth
//    @ApiOperation(value = "根据userLoginId或token换取登录用户信息", hidden = true)
//    @GetMapping("/userLoginInfo")
//    public RestfulResponse<String> userLoginInfo(HttpServletRequest request, @ApiParam("token") String token)
//            throws Exception {
//        RestfulResponse<String> restful = new RestfulResponse<String>("");
//
//        String fromBy = RequestHelper.getFromBy(request);
//
//        String result = "";
//        try {
//            // 获取登录用户信息
//            if (StringUtils.isNotBlank(token)) {
//                UserLoginInfoVo vo = redisUtils.getUserInfoByToken(fromBy, token);
//                if (vo == null) {
//                    restful.setCode(1430);
//                    restful.setMessage("用户已下线或未登录！");
//                    return restful;
//                }
//                result = JSONObject.toJSONString(vo);
//            } else {
//                System.out.println("内部请求后的sessionId：" + request.getSession().getId());
//                com.by.crm.vo.UserLoginInfoVo vo = (com.by.crm.vo.UserLoginInfoVo) request
//                        .getAttribute("loginUserInfo");
//                if (vo == null) {
//                    restful.setCode(1430);
//                    restful.setMessage("用户已下线或未登录！");
//                    return restful;
//                }
//                result = JSONObject.toJSONString(vo);
//            }
//
//            System.out.println("用户信息：" + JSONObject.toJSONString(result));
//            restful.setData(result);
//        } catch (MessageException e) {
//            restful.setCode(ECode.ARG_ERROR.getCode());
//            if (e.getCode() != null) {
//                restful.setCode(e.getCode().getCode());
//            }
//            restful.setMessage(e.getMessage());
//            return restful;
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getMessage());
//            restful.setCode(ECode.ARG_ERROR.getCode());
//            restful.setMessage(e.getMessage());
//            return restful;
//        }
//        return restful;
//    }

    private void checkPassword(String password, RestfulResponse<Void> restful) {
        if (password.length() < 6 || password.length() > 16) {
            restful.setMessage("密码长度需在6位至16位之间！");
            restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
            return;
        }

        int len = password.length();
        int n = 0;
        int l = 0;
        int b = 0;
        for (char c : password.toCharArray()) {
            if (c >= '0' && c <= '9') {
                n++;
            } else if (c >= 'a' && c <= 'z') {
                l++;
            } else if (c >= 'A' && c <= 'Z') {
                b++;
            }
        }

        if (l + b == len) {
            restful.setMessage("密码太过简单，必须同时包含数字和字母！");
            restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
            return;
        }
    }
*/
}