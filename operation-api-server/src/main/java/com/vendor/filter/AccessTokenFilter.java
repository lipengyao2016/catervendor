package com.vendor.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.vendor.queryvo.user.UserLoginInfo;
import com.vendor.utils.GsonUtils;
import com.vendor.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * token过滤器，校验token必输项方法，token不能为空
 * 
 * @author guooo
 *
 */
@Component
public class AccessTokenFilter extends ZuulFilter {
	
	private static Logger log = LoggerFactory.getLogger(AccessTokenFilter.class);

	@Autowired
	private RequestInterceptor requestInterceptor;


	protected void sendAuthInvalidToken(RequestContext ctx,String retValue)
    {
        ctx.setSendZuulResponse(false);
        ctx.setResponseStatusCode(401);
        try {
            ctx.getResponse().getWriter().write(retValue);
        }catch (Exception e){}
    }

	/*
	 * 过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
	 * 
	 * @see com.netflix.zuul.IZuulFilter#run()
	 */
	@Override
	public Object run() {
		//TODO 可将Front-app服务中的APISecurityCheck中针对accessToken的校验迁移至此，提前验证
		RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        String accessToken = request.getParameter("token");
        if(accessToken == null) {
            log.warn("token is empty");

            this.sendAuthInvalidToken(ctx,"token is empty");

            return null;
        }
        else
		{
		    try
            {
                String decodeToken = JWTUtils.verifyToken(accessToken);
                UserLoginInfo userLoginInfo = (UserLoginInfo) GsonUtils.ToObject(decodeToken,UserLoginInfo.class);

                //request.setAttribute("created_by",userLoginInfo.getUserName());
				requestInterceptor.intercept(ctx,userLoginInfo);
            }
             catch (Exception e) {
                 e.printStackTrace();
                 this.sendAuthInvalidToken(ctx,"token is invalid");
             }
        }


        log.info("ok");
        return null;
	}

	/*
	 * 这里可以写逻辑判断，是否要过滤，本文true,永远过滤。
	 * 
	 * @see com.netflix.zuul.IZuulFilter#shouldFilter()
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	/*
	 * (non-Javadoc) pre：路由之前 routing：路由之时 post： 路由之后 error：发送错误调用
	 * 
	 * @see com.netflix.zuul.ZuulFilter#filterType()
	 */
	@Override
	public String filterType() {
		return "pre";
	}

}
