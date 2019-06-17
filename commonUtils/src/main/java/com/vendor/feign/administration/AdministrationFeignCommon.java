package com.vendor.feign.administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vendor.common.ECode;
import com.vendor.common.MessageException;
import com.vendor.common.RestfulResponse;
import com.vendor.dto.PermissionDto;
import com.vendor.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdministrationFeignCommon {

	@Autowired
	private AdministrationFeignClient feignClient;

	/**
	 * 模糊匹配反查用户信息
	 * 
	 * @param keyword
	 * @return
	 */
	public Map<String, String> findUserNameByKeyword(String keyword) {
		RestfulResponse<Map<String, String>> usersResult = feignClient.findUserNameByKeyword(keyword);
		if (usersResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return new HashMap<String, String>();
		}
		if (usersResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(usersResult.getMessage());
		}

		return usersResult.getData();
	}
    /**
     * 查询该用户是否有操作该菜单的权限
     *
     * @param requestMethod
     * @return
     */
    public Boolean hasPermission(String requestMethod) {
        RestfulResponse<Boolean> permissionResult = feignClient.hasPermission(requestMethod);
        if (permissionResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
            return false;
        }
        if (permissionResult.getCode() != ECode.SUCCESS.getCode()) {
            throw new MessageException(permissionResult.getMessage());
        }

        return permissionResult.getData();
    }
	/**
	 * 根据权限名称模糊匹配反查权限信息
	 * 
	 * @param permissionName
	 * @return
	 */
	public Map<Integer, String> findPermissionByVagueName(String permissionName) {
		RestfulResponse<Map<Integer, String>> permissionResult = feignClient.findPermissionByVagueName(permissionName);
		if (permissionResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return new HashMap();
		}
		if (permissionResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(permissionResult.getMessage());
		}

		return permissionResult.getData();
	}

	/**
	 * 根据权限ID数据反查权限信息
	 * 
	 * @param permissionSeqIdArr
	 * @return
	 */
	public Map<Integer, String> findPermissionByIds(Integer[] permissionSeqIdArr) {
		if (permissionSeqIdArr == null || permissionSeqIdArr.length == 0) {
			return new HashMap();
		}

		RestfulResponse<Map<Integer, String>> permissionResult = feignClient.findPermissionByIds(permissionSeqIdArr);
		if (permissionResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			throw new MessageException("未查询到权限菜单信息");
		}
		if (permissionResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(permissionResult.getMessage());
		}

		return permissionResult.getData();
	}

	/**
	 * 根据数据库表名反查权限信息
	 * 
	 * @param tableName
	 * @return
	 */
	public Map<Integer, String> findPermissionByTableName(String tableName) {
		RestfulResponse<Map<Integer, String>> permissionResult = feignClient.findPermissionByTableName(tableName);
		if (permissionResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return new HashMap();
		}
		if (permissionResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(permissionResult.getMessage());
		}

		return permissionResult.getData();
	}

	/**
	 * 查询权限菜单是否需要验证
	 * 
	 * @param requestMethod
	 * @return
	 */
	public Boolean needPermissionByUrl(String requestMethod) {
		RestfulResponse<Boolean> permissionResult = feignClient.needPermissionByUrl(requestMethod);
		if (permissionResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return false;
		}
		if (permissionResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(permissionResult.getMessage());
		}

		return permissionResult.getData();
	}

	/**
	 * 查询用户是否超级管理员
	 * 
	 * @param userLoginId
	 * @return
	 */
	public Boolean ifSuperAdmin(String userLoginId) {
		RestfulResponse<Boolean> permissionResult = feignClient.ifSuperAdmin(userLoginId);
		if (permissionResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return false;
		}
		if (permissionResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(permissionResult.getMessage());
		}

		return permissionResult.getData();
	}

	/**
	 * 根据权限url和请求方式获取对应权限编码
	 * 
	 * @param permissionUrl
	 * @param requestMethod
	 * @return
	 */
	public Map<Integer, PermissionDto> findPermissionByUrl(String permissionUrl, String requestMethod) {
		RestfulResponse<Map<Integer, PermissionDto>> permissionResult = feignClient
				.findPermissionByUrl(permissionUrl, requestMethod);
		if (permissionResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return new HashMap();
		}
		if (permissionResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(permissionResult.getMessage());
		}

		return permissionResult.getData();
	}

	/**
	 * 根据用户名数组查询用户登录名和姓名
	 * 
	 * @param userIdList
	 * @return
	 */
	public Map<String, String> findUserNames(List<String> userIdList) {
		if (userIdList == null || userIdList.size() == 0) {
			return new HashMap();
		}

		String[] userIdArr = new String[userIdList.size()];
		RestfulResponse<Map<String, String>> userResult = feignClient.findUserNames(userIdList.toArray(userIdArr));
		if (userResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return new HashMap();
		}
		if (userResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(userResult.getMessage());
		}

		return userResult.getData();
	}

	/**
	 * 查询用户详情
	 * 
	 * @param userLoginId
	 * @return
	 */
	public UserVo get(String userLoginId) {
		RestfulResponse<UserVo> userResult = feignClient.get(userLoginId);
		if (userResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			throw new MessageException("未查询到用户信息");
		}
		if (userResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(userResult.getMessage());
		}

		return userResult.getData();
	}

	/**
	 * 批量查询用户详情
	 * 
	 * @param userLoginIdArray
	 * @return
	 */
	public List<UserVo> getBatch(String[] userLoginIdArray) {
		if (userLoginIdArray == null || userLoginIdArray.length == 0) {
			return new ArrayList();
		}

		RestfulResponse<List<UserVo>> userResult = feignClient.getBatch(userLoginIdArray);
		if (userResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			throw new MessageException("未查询到用户信息");
		}
		if (userResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(userResult.getMessage());
		}

		return userResult.getData();
	}

	/**
	 * 批量查询用户详情、查询条件姓名/手机号
	 * 
	 * @param userLoginIdArray
	 * @return
	 */
	public List<UserVo> getBatchByKeyword(String[] userLoginIdArray, String keyword) {
		if (userLoginIdArray == null || userLoginIdArray.length == 0) {
			return new ArrayList();
		}

		RestfulResponse<List<UserVo>> userResult = feignClient.getBatchByKeyword(userLoginIdArray, keyword);
		if (userResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return null;
		}
		if (userResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(userResult.getMessage());
		}

		return userResult.getData();
	}
 

	/**
	 * 查询部门名称信息
	 * 
	 * @param departmentIdArr
	 * @return
	 */
	public Map<String, String> findDepartmentNames(String[] departmentIdArr) {
		if (departmentIdArr == null || departmentIdArr.length == 0) {
			return new HashMap();
		}

		RestfulResponse<Map<String, String>> storeResult = feignClient.findDepartmentNames(departmentIdArr);
		if (storeResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return new HashMap();
		}
		if (storeResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(storeResult.getMessage());
		}

		return storeResult.getData();
	}


	/**
	 * PC端权限菜单路径
	 * 
	 * @param permissionUrlArr
	 * @return
	 */
	public Map<String, String> findPcMenuUrl(String[] permissionUrlArr) {
		if (permissionUrlArr == null || permissionUrlArr.length == 0) {
			return new HashMap();
		}

		RestfulResponse<Map<String, String>> permissionResult = feignClient.findPcMenuUrl(permissionUrlArr);
		if (permissionResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
			return new HashMap();
		}
		if (permissionResult.getCode() != ECode.SUCCESS.getCode()) {
			throw new MessageException(permissionResult.getMessage());
		}

		return permissionResult.getData();
	}

//	/**
//	 * 查询用户被授权敏感字段的通讯录信息
//	 *
//	 * @return
//	 */
//	public List<UserAuthSensitiveVo> findUserAuthSensitiveVoList() {
//		RestfulResponse<List<UserAuthSensitiveVo>> sensitiveResult = feignClient.findUserAuthSensitiveVoList();
//		if (sensitiveResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
//			return new ArrayList();
//		}
//		if (sensitiveResult.getCode() != ECode.SUCCESS.getCode()) {
//			throw new MessageException(sensitiveResult.getMessage());
//		}
//
//		return sensitiveResult.getData();
//	}

//	/**
//	 * 查询用户被授权敏感字段的信息
//	 *
//	 * @return
//	 */
//	public List<PermissionSensitiveVo> findPermissionAuthFromUserList() {
//		RestfulResponse<List<PermissionSensitiveVo>> sensitiveResult = feignClient.findPermissionAuthFromUserList();
//		if (sensitiveResult.getCode() == ECode.NO_DATA_RESULT.getCode()) {
//			return new ArrayList();
//		}
//		if (sensitiveResult.getCode() != ECode.SUCCESS.getCode()) {
//			throw new MessageException(sensitiveResult.getMessage());
//		}
//
//		return sensitiveResult.getData();
//	}
}
