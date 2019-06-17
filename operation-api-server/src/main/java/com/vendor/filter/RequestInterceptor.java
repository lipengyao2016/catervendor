package com.vendor.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.http.HttpServletRequestWrapper;
import com.netflix.zuul.http.ServletInputStreamWrapper;
import com.vendor.config.entity.ConfigPlan;
import com.vendor.queryvo.user.UserLoginInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class RequestInterceptor {

    private static Logger log = LoggerFactory.getLogger(RequestInterceptor.class);

    @Autowired
    private InterceptorConfig interceptorConfig;



    public boolean intercept(RequestContext ctx,UserLoginInfo userLoginInfo )
    {
        try {
            HttpServletRequest request  =ctx.getRequest();
            String method = request.getMethod();
            String requestUrl = request.getRequestURL().toString();//得到请求的URL地址
            String requestUri = request.getRequestURI();//得到请求的资源
            String queryString = request.getQueryString();//得到请求的URL地址中附带的参数


            List<ConfigPlan> serverPlans = interceptorConfig.getServerRules(requestUri);
            if(serverPlans.size() <= 0)
            {
                return  true;
            }

            if(method.equalsIgnoreCase("GET"))
            {
                return this.getIntercept(ctx,userLoginInfo,serverPlans);
            }
            else
            {
                return this.postIntercept(ctx,userLoginInfo,serverPlans);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return  true;


    }

    public boolean getIntercept(RequestContext ctx,UserLoginInfo userLoginInfo,List<ConfigPlan> serverPlans )
    {
        HttpServletRequest request  =ctx.getRequest();
        request.getParameterMap();
        Map<String, List<String>> requestQueryParams = ctx.getRequestQueryParams();
        if (requestQueryParams == null) {
            requestQueryParams = new HashMap<>();
        }
/*        List<String> arrayList = new ArrayList<>();

        arrayList.add("testData");
        requestQueryParams.put("decodename", arrayList);*/

        this.interceptorConfig.addGetParams(requestQueryParams,userLoginInfo,request.getMethod(),serverPlans);

        ctx.setRequestQueryParams(requestQueryParams);

        return  false;
    }





    public boolean postIntercept(RequestContext ctx,UserLoginInfo userLoginInfo,List<ConfigPlan> serverPlans )
    {
        boolean bRet = false;
        try {
            // 编码格式
            String charset = ctx.getRequest().getCharacterEncoding();
            InputStream in = (InputStream) ctx.get("requestEntity");
            if (null == in) {
                in = ctx.getRequest().getInputStream();
            }
            String requestEntityStr = StreamUtils.copyToString(in, Charset.forName(charset));
            requestEntityStr = URLDecoder.decode(requestEntityStr, charset);
            JSONObject requestEntityJson = JSONObject.parseObject(requestEntityStr);


            // 新增参数
       /*     requestEntityJson.put("createdBy", userLoginInfo.getUserId());
            requestEntityJson.put("updatedBy", userLoginInfo.getUserId());*/

            interceptorConfig.addParams(requestEntityJson,userLoginInfo,ctx.getRequest().getMethod(),serverPlans);


            byte[] requestEntityBytes = requestEntityJson.toJSONString().getBytes(charset);

            ctx.setRequest(new HttpServletRequestWrapper(ctx.getRequest()) {
                @Override
                public ServletInputStream getInputStream() throws IOException {
                    return new ServletInputStreamWrapper(requestEntityBytes);
                }

                @Override
                public int getContentLength() {
                    return requestEntityBytes.length;
                }

                @Override
                public long getContentLengthLong() {
                    return requestEntityBytes.length;
                }
            });

            bRet = true;

        } catch (Exception e) {

            //ctx.setResponseBody(String.format(SessionContants.ERROR_RESPONSE_BODY, "修改请求体出错"));

        }
        return bRet;
    }

}
