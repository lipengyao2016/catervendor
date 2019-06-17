package com.vendor.interceptor;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vendor.annotation.NoPermissionAuth;
import com.vendor.annotation.PermissionAuth;
import com.vendor.common.ECode;
import com.vendor.common.ELoginChannel;
import com.vendor.common.RestfulResponse;
import com.vendor.core.*;
import com.vendor.dto.PermissionDto;
import com.vendor.feign.administration.AdministrationFeignCommon;
import com.vendor.vo.UserLoginInfoVo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;

/**
 * 用户请求访问权限菜单验证拦截器
 *
 */
@Component
public class CheckPermissionInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(CheckPermissionInterceptor.class);

	private static final Map<String, String> operationMap = new HashMap<String, String>();
	static {
		operationMap.put("POST", "保存");
		operationMap.put("PUT", "修改");
		operationMap.put("DELETE", "删除");
		operationMap.put("GET", "查询");
	}

	@Autowired
	private AdministrationFeignCommon feignClient;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handle) throws Exception {
		final HandlerMethod handlerMethod = (HandlerMethod) handle;
		final Method method = handlerMethod.getMethod();
		final Class<?> clazz = method.getDeclaringClass();

		request.setAttribute("loginUserInfo", null);
		request.setAttribute("openId", null);
		request.setAttribute("originalAppId", null);
		request.setAttribute("scode", null);
		String sessionId = request.getSession().getId();
		if (sessionId == null || "".equals(sessionId)) {
			sessionId = (String) request.getSession().getAttribute("sessionId");
		}

		UserLoginInfoVo loginVo = null;
		String token = request.getHeader("token");
		String loginUserInfo = request.getHeader("loginUserInfo");

		response.setContentType("application/json; charset=UTF-8");
		RestfulResponse<Void> restful = new RestfulResponse<Void>();
		if (loginUserInfo != null && !"".equals(loginUserInfo)) {
			try {
				loginUserInfo = URLDecoder.decode(loginUserInfo, "UTF-8");
				JSONObject loginUserJson = JSONObject.parseObject(loginUserInfo);
				request.setAttribute("isSystemCall", loginUserJson.getString("isSystemCall"));
				loginVo = JSONObject.toJavaObject(loginUserJson, UserLoginInfoVo.class);
				LOGGER.info("登录用户信息：" + loginUserInfo);
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				restful.setCode(ECode.NOT_LOGGED_IN.getCode());
				restful.setMessage("登录用户信息编码格式转换出错！");
				response.getWriter().print(JSONObject.toJSONString(restful));
				response.getWriter().close();
				return false;
			}
		}

		// 微商城
		String originalAppId = request.getHeader("originalAppId");
		String openId = request.getHeader("openId");
		request.setAttribute("openId", openId);
		request.setAttribute("originalAppId", originalAppId);

		// 外部经销商代码
		String scode = request.getHeader("scode");
		request.setAttribute("scode", scode);
		if (loginVo != null) {
			request.setAttribute("loginUserInfo", loginVo);
		}

		String currentRequestUrl = request.getRequestURL().toString();
		System.out.println("当前请求url:" + currentRequestUrl);
//		if (openId != null && !"".equals(openId) && originalAppId != null && !"".equals(originalAppId)) {
//			// 获取本系统工厂信息
//			if (!currentRequestUrl.contains("/factoryId/")) {
//				String relevanceFactoryId = feignClient.getFactoryId(originalAppId);
//				String factoryId = feignClient.findFactoryByRelevanceFactoryId(relevanceFactoryId,
//						loginVo.getAgencyId());
//				if (factoryId == null || "".equals(factoryId)) {
//					throw new MessageException("未获取到厂家经销商信息！");
//				}
//
//				FactoryVo f = feignClient.findFactoryDetail(factoryId);
//				loginVo.setFactoryId(factoryId);
//				loginVo.setAgencyId(f.getAgencyId());
//				loginVo.setAgencyName(f.getAgencyName());
//				loginVo.setIfAgencyUser("N");
//				request.setAttribute("loginUserInfo", loginVo);
//				return true;
//			}
//		}
//
//		if (scode != null && !"".equals(scode)) {
//			if (!currentRequestUrl.contains("/agencyInfo/agency/customId/")) {
//				String agencyId = feignClient.findAgencyIdBycustomId(scode);
//				if (agencyId == null || "".equals(agencyId)) {
//					throw new MessageException("未匹配到系统经销商!");
//				}
//
//				loginVo = new UserLoginInfoVo();
//				loginVo.setAgencyId(agencyId);
//				loginVo.setIfAgencyUser("N");
//				request.setAttribute("loginUserInfo", loginVo);
//				return true;
//			}
//		}

		// if ("Y".equals(json.getString("ifAgencyUser"))) {
		// String agencyId = json.getString("agencyId");
		// if (agencyId != null && !"".equals(agencyId)) {
		// String status = feignClient.findAgencyStatus(agencyId);
		// if (!"Y".equals(status)) {
		// restful.setCode(ECode.NOT_LOGGED_IN.getCode());
		// restful.setMessage("你所在的经销商已被禁用！");
		// response.getWriter().print(JSONObject.toJSONString(restful));
		// response.getWriter().close();
		// return false;
		// }
		// }
		// }

		if (!handle.getClass().isAssignableFrom(HandlerMethod.class)) {
			return true;
		}

		// 针对添加了@NoPermissionAuth注解的请求不做拦截处理(如不需要权限验证的请求或微服务之间调用的请求)
		if (clazz.isAnnotationPresent(NoPermissionAuth.class) || method.isAnnotationPresent(NoPermissionAuth.class)) {
			return true;
		}

		// 针对添加了@Permission注解的请求做拦截处理
		if (clazz.isAnnotationPresent(PermissionAuth.class) || method.isAnnotationPresent(PermissionAuth.class)) {
			String fromBy = RequestHelper.getFromBy(request);
			if (!ELoginChannel.PC.name().equals(fromBy)) {
				LOGGER.info("终端类型：" + fromBy + "请求的token值为：" + token);
				if (!ifUserLogin(loginVo)) {
					restful.setCode(ECode.NOT_LOGGED_IN.getCode());
					restful.setMessage("因长时间未操作，本app和服务器的连接已断开，请重新登陆！");
					response.getWriter().print(JSONObject.toJSONString(restful));
					response.getWriter().close();
					return false;
				}

			} else if (StringUtils.isNotBlank(sessionId)) {
				// 登录用户修改自身密码后，session被清空，设置key为pass的值用以区分是失效还是修改密码
				String pass = (String) request.getSession().getAttribute("pass");
				if ("success".equals(pass)) {
					restful.setCode(ECode.NOT_LOGGED_IN.getCode());
					restful.setMessage("密码修改成功，请重新登录！");
					response.getWriter().print(JSONObject.toJSONString(restful));
					response.getWriter().close();
					return false;
				}
				if (!ifUserLogin(loginVo)) {
					restful.setCode(ECode.NOT_LOGGED_IN.getCode());
					restful.setMessage("因长时间未操作，本网页和服务器的连接已断开，请重新登陆！");
					response.getWriter().print(JSONObject.toJSONString(restful));
					response.getWriter().close();
					return false;
				}
			} else {
				restful.setCode(ECode.MALFORMEDURL.getCode());
				restful.setMessage("非法访问系统！");
				response.getWriter().print(JSONObject.toJSONString(restful));
				response.getWriter().close();
				return false;
			}

			// 权限验证
			try {
				// 如果是超级管理员，则不做权限验证
				String ifSuperAdmin = loginVo.getIfSuperAdmin();
				if ("Y".equals(ifSuperAdmin)) {
					return true;
				}

				// 外部请求的url
				String requestUrl = request.getHeader("requestURL");
				String requestMethod = request.getMethod();
				boolean needPermission = feignClient.needPermissionByUrl(requestMethod);

				// 需要权限认证
				if (needPermission) {
					Map<Integer, PermissionDto> permissionRestful = feignClient.findPermissionByUrl(requestUrl,
							requestMethod);
					Collection<PermissionDto> list = permissionRestful.values();

					// 外部系统调用直接返回
//					String ifAgencyUser = loginVo.getIfAgencyUser();
//					if (!"Y".equals(ifAgencyUser)) {
//						restful.setCode(ECode.UNAUTHORIZED.getCode());
//						restful.setMessage("你没有" + operationMap.get(requestMethod)
//								+ list.iterator().next().getPermissionName() + "的权限！");
//						response.getWriter().print(JSONObject.toJSONString(restful));
//						response.getWriter().close();
//						return false;
//					}

//					String agencyId = loginVo.getAgencyId();
//					if (agencyId == null || "".equals(agencyId)) {
//						restful.setCode(ECode.UNAUTHORIZED.getCode());
//						restful.setMessage("用户未代理任何经销商，无权限操作系统！");
//						response.getWriter().print(JSONObject.toJSONString(restful));
//						response.getWriter().close();
//						return false;
//					}

					if (requestUrl.contains("/operationLogDatas")) {
						String majorKeyId = request.getParameter("majorKeyId");
						if (majorKeyId != null && !"".equals(majorKeyId)) {
							return true;
						}
					}

					// 验证用户是否有权限
					boolean hasPermission = feignClient.hasPermission(requestMethod);
					if (hasPermission) {
						return true;
					}

					if (list != null && list.size() > 0) {
						restful.setCode(ECode.UNAUTHORIZED.getCode());
						restful.setMessage("你没有" + operationMap.get(requestMethod)
								+ list.iterator().next().getPermissionName() + "的权限！");
						response.getWriter().print(JSONObject.toJSONString(restful));
						response.getWriter().close();
						return false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.info("系统异常{}", e.getMessage());
				restful.setCode(ECode.SYSTEM_UNKNOWN_EXCEPTION.getCode());
				restful.setMessage(ECode.SYSTEM_UNKNOWN_EXCEPTION.getMessage());
				response.getWriter().print(JSONObject.toJSONString(restful));
				response.getWriter().close();
				return false;
			}
		}

		return true;
	}

	private boolean ifUserLogin(UserLoginInfoVo loginVo) {
		if (loginVo == null) {
			return false;
		}

		if (loginVo.getUserLoginId() == null || "".equals(loginVo.getUserLoginId())) {
			return false;
		}

		return true;
	}
}
