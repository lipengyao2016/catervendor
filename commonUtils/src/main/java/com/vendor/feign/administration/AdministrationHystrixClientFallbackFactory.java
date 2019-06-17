package com.vendor.feign.administration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vendor.common.ECode;
import com.vendor.common.RestfulResponse;
import com.vendor.dto.PermissionDto;
import com.vendor.vo.UserVo;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@SuppressWarnings({ "rawtypes", "unchecked" })
@Component
public class AdministrationHystrixClientFallbackFactory implements FallbackFactory<AdministrationFeignClient> {
	private static final Logger log = LoggerFactory.getLogger(AdministrationHystrixClientFallbackFactory.class);

	@Override
	public AdministrationFeignClient create(Throwable cause) {
		return new AdministrationFeignClientWithFallBackFactory() {
			@Override
			public RestfulResponse<Map<String, String>> findUserNameByKeyword(String keyword) {
				RestfulResponse<Map<String, String>> restful = new RestfulResponse<Map<String, String>>(new HashMap());
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询用户信息出错！");
				return restful;
			}

			@Override
			public RestfulResponse<Map<Integer, String>> findPermissionByVagueName(String permissionName) {
				RestfulResponse<Map<Integer, String>> restful = new RestfulResponse<Map<Integer, String>>(
						new HashMap<>());
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("匹配到权限出错！");
				return restful;
			}

			@Override
			public RestfulResponse<Map<Integer, String>> findPermissionByIds(Integer[] permissionSeqIdArr) {
				RestfulResponse<Map<Integer, String>> restful = new RestfulResponse<Map<Integer, String>>(
						new HashMap<>());
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("匹配到权限出错！");
				return restful;
			}

			@Override
			public RestfulResponse<Map<Integer, String>> findPermissionByTableName(String tableName) {
				RestfulResponse<Map<Integer, String>> restful = new RestfulResponse<Map<Integer, String>>(
						new HashMap<>());
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("匹配到权限出错！");
				return restful;
			}

			@Override
			public RestfulResponse<Boolean> hasPermission(String requestMethod) {
				RestfulResponse<Boolean> restful = new RestfulResponse<Boolean>(false);
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询权限出错！");
				return restful;
			}

			@Override
			public RestfulResponse<Boolean> needPermissionByUrl(String requestMethod) {
				RestfulResponse<Boolean> restful = new RestfulResponse<Boolean>(false);
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询权限出错！");
				return restful;
			}

			@Override
			public RestfulResponse<Boolean> ifSuperAdmin(String userLoginId) {
				RestfulResponse<Boolean> restful = new RestfulResponse<Boolean>(false);
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询角色出错！");
				return restful;
			}

			@Override
			public RestfulResponse<Map<Integer, PermissionDto>> findPermissionByUrl(String permissionUrl,
																					String requestMethod) {
				RestfulResponse<Map<Integer, PermissionDto>> restful = new RestfulResponse<Map<Integer, PermissionDto>>(
						new HashMap<>());
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("匹配到权限出错！");
				return restful;
			}

			@Override
			public RestfulResponse<Map<String, String>> findUserNames(String[] userIdArr) {
				RestfulResponse<Map<String, String>> restful = new RestfulResponse<Map<String, String>>(
						new HashMap<>());
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询用户信息出错！");
				return restful;
			}

			@Override
			public RestfulResponse<String> findAgencyUserIds(String agencyId) {
				RestfulResponse<String> restful = new RestfulResponse<String>("");
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询经销商用户出错！");
				return restful;
			}

			@Override
			public RestfulResponse<List<String>> findAgencyStoreIds(String agencyId) {
				RestfulResponse<List<String>> restful = new RestfulResponse<List<String>>(new ArrayList<>());
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询门店编码出错！");
				return restful;
			}

			@Override
			public RestfulResponse<UserVo> get(String userLoginId) {
				RestfulResponse<UserVo> restful = new RestfulResponse<UserVo>(new UserVo());
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询用户出错！");
				return restful;
			}

			@Override
			public RestfulResponse<List<UserVo>> getBatch(String[] userLoginIdArray) {
				RestfulResponse<List<UserVo>> restful = new RestfulResponse<List<UserVo>>(new ArrayList<>());
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询用户出错！");
				return restful;
			}

			@Override
			public RestfulResponse<Map<String, String>> findDepartmentNames(String[] departmentIdArr) {
				RestfulResponse<Map<String, String>> restful = new RestfulResponse<Map<String, String>>(new HashMap());
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询部门名称出错！");
				return restful;
			}

			@Override
			public RestfulResponse<Map<String, String>> findPcMenuUrl(String[] permissionUrlArr) {
				RestfulResponse<Map<String, String>> restful = new RestfulResponse<Map<String, String>>(new HashMap());
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询PC端菜单路径出错！");
				return restful;
			}

//			@Override
//			public RestfulResponse<List<UserAuthSensitiveVo>> findUserAuthSensitiveVoList() {
//				RestfulResponse<List<UserAuthSensitiveVo>> response = RestfulResponse
//						.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
//				response.setMessage("查询上下级出错！");
//				return response;
//			}

//			@Override
//			public RestfulResponse<List<PermissionSensitiveVo>> findPermissionAuthFromUserList() {
//				RestfulResponse<List<PermissionSensitiveVo>> response = RestfulResponse
//						.error(ECode.CICD_JOB_QUERY_CODE_BRANCHES_FAILED);
//				response.setMessage("查询上下级出错！");
//				return response;
//			}

			@Override
			public RestfulResponse<List<UserVo>> getBatchByKeyword(String[] userLoginIdArray, String keyword) {
				RestfulResponse<List<UserVo>> restful = new RestfulResponse<List<UserVo>>(new ArrayList<>());
				restful.setCode(ECode.CICD_JOB_QUERY_CONFIG_FAILED.getCode());
				restful.setMessage("查询用户出错！");
				return restful;
			}
		};
	}
}