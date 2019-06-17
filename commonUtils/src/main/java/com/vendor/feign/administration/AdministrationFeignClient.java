package com.vendor.feign.administration;

import java.util.List;
import java.util.Map;

import com.vendor.common.RestfulResponse;
import com.vendor.dto.PermissionDto;
import com.vendor.vo.UserVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "system-administration", fallbackFactory = AdministrationHystrixClientFallbackFactory.class)
public interface AdministrationFeignClient {
	/**
	 * 模糊匹配反查用户信息
	 * 
	 * @param keyword
	 */
	//@RequestMapping("/sysconfig/user/username/{keyword}")
	@RequestMapping("/sysconfig/user/username/{keyword}")
	public RestfulResponse<Map<String, String>> findUserNameByKeyword(@PathVariable("keyword") String keyword);

    /**
     * 查询该用户是否有操作该菜单的权限
     *
     * @param requestMethod
     */
    @RequestMapping("/sysconfig/permission/holdPermission/{requestMethod}")
    public RestfulResponse<Boolean> hasPermission(@PathVariable("requestMethod") String requestMethod);

	/**
	 * 根据权限名称模糊匹配反查权限信息
	 * 
	 * @param permissionName
	 */
	@RequestMapping("/sysconfig/permission/vagueperPmissionNames/{permissionName}")
	public RestfulResponse<Map<Integer, String>> findPermissionByVagueName(
            @PathVariable("permissionName") String permissionName);

	/**
	 * 根据权限ID数据反查权限信息
	 * 
	 * @param permissionSeqIdArr
	 */
	@RequestMapping("/sysconfig/permission/permissionNames/{permissionSeqIdArr}")
	public RestfulResponse<Map<Integer, String>> findPermissionByIds(
            @PathVariable("permissionSeqIdArr") Integer[] permissionSeqIdArr);

	/**
	 * 根据数据库表名反查权限信息
	 * 
	 * @param tableName
	 */
	@RequestMapping("/sysconfig/permission/permissionNamesByTable/{tableName}")
	public RestfulResponse<Map<Integer, String>> findPermissionByTableName(@PathVariable("tableName") String tableName);

	/**
	 * 查询权限菜单是否需要验证
	 * 
	 * @param requestMethod
	 */
	@RequestMapping("/sysconfig/permission/needPermissionByUrl/{requestMethod}")
	public RestfulResponse<Boolean> needPermissionByUrl(@PathVariable("requestMethod") String requestMethod);

	/**
	 * 查询用户是否超级管理员
	 * 
	 * @param userLoginId
	 */
	@RequestMapping("/sysconfig/role/superAdmin/{userLoginId}")
	public RestfulResponse<Boolean> ifSuperAdmin(@PathVariable("userLoginId") String userLoginId);

	/**
	 * 根据权限url和请求方式获取对应权限编码
	 * 
	 * @param permissionUrl
	 * @param requestMethod
	 */
	@RequestMapping("/sysconfig/permission/permissionsByUrl")
	public RestfulResponse<Map<Integer, PermissionDto>> findPermissionByUrl(
            @RequestParam(value = "permissionUrl", required = true) String permissionUrl,
            @RequestParam(value = "requestMethod", required = false) String requestMethod);

	/**
	 * 根据用户名数组查询用户登录名和姓名
	 * 
	 * @param userIdArr
	 */
	@RequestMapping("/sysconfig/user/usernames/{userIdArr}")
	public RestfulResponse<Map<String, String>> findUserNames(@PathVariable("userIdArr") String[] userIdArr);

	/**
	 * 查询是经销商所有用户登录账号
	 * 
	 * @param agencyId
	 */
	@RequestMapping("/sysconfig/user/userIds/{agencyId}")
	public RestfulResponse<String> findAgencyUserIds(@PathVariable("agencyId") String agencyId);

	/**
	 * 查询经销商所有门店编码
	 * 
	 * @param agencyId
	 */
	@RequestMapping("agencyInfo/storeIds/{agencyId}")
	public RestfulResponse<List<String>> findAgencyStoreIds(@PathVariable("agencyId") String agencyId);

	/**
	 * 查询用户详情
	 * 
	 * @param userLoginId
	 */
	@RequestMapping("/sysconfig/user/{userLoginId}")
	public RestfulResponse<UserVo> get(@PathVariable("userLoginId") String userLoginId);

	/**
	 * 批量查询用户详情
	 * 
	 */
	@RequestMapping("/sysconfig/user/getBatch/{userLoginIdArray}")
	public RestfulResponse<List<UserVo>> getBatch(@PathVariable("userLoginIdArray") String[] userLoginIdArray);

	/**
	 * 批量查询用户详情、查询条件姓名/手机号
	 * 
	 */
	@RequestMapping("/sysconfig/user/getBatchByKeyword/{userLoginIdArray}/{keyword}")
	public RestfulResponse<List<UserVo>> getBatchByKeyword(
            @PathVariable("userLoginIdArray") String[] userLoginIdArray, @PathVariable("keyword") String keyword);

	/**
	 * 查询部门名称信息
	 * 
	 * @param departmentIdArr
	 */
	@RequestMapping("/agencyInfo/departmentNames/{departmentIdArr}")
	public RestfulResponse<Map<String, String>> findDepartmentNames(
            @PathVariable("departmentIdArr") String[] departmentIdArr);


	/**
	 * PC端权限菜单路径
	 * 
	 * @param permissionUrlArr
	 */
	@RequestMapping("/sysconfig/permission/permissionPcMenuUrls")
	public RestfulResponse<Map<String, String>> findPcMenuUrl(
            @RequestParam("permissionUrlArr") String[] permissionUrlArr);

	/**
	 * 查询用户被授权敏感字段的通讯录信息
	 * 
	 * @return
	 */
//	@RequestMapping("permissionAuthSensitives")
//	public RestfulResponse<List<UserAuthSensitiveVo>> findUserAuthSensitiveVoList();

	/**
	 * 查询用户被授权敏感字段的信息
	 * 
	 * @return
	 */
//	@RequestMapping("permissionAuthtoMe")
//	public RestfulResponse<List<PermissionSensitiveVo>> findPermissionAuthFromUserList();
}