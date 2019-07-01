package com.vendor.feign.administration;

import java.util.List;
import java.util.Map;

import com.vendor.common.RestfulResponse;
import com.vendor.dto.PermissionDto;
import com.vendor.vo.PermissionVo;
import com.vendor.vo.UserVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@FeignClient(name = "vm-administration", fallbackFactory = AdministrationHystrixClientFallbackFactory.class)
public interface AdministrationFeignClient {

    /**
     * 查询该用户是否有操作该菜单的权限
     *
     * @param requestMethod
     */
    @RequestMapping(value = "/permission/needPermissionByUrl/{requestMethod}",method = RequestMethod.GET)
    public RestfulResponse<Boolean> needPermissionByUrl(@PathVariable("requestMethod") String requestMethod);

    /**
     * 查询该用户是否有操作该菜单的权限
     *
     * @param requestMethod
     */
    @RequestMapping(value = "/permission/holdPermission/{requestMethod}",method = RequestMethod.GET)
    public RestfulResponse<Boolean> hasPermission(@PathVariable("requestMethod") String requestMethod);

    /**
     * 根据权限url和请求方式获取对应权限编码
     *
     * @param permissionUrl
     * @param requestMethod
     * @return
     */
    @RequestMapping(value = "/permission/permissionsByUrl",method = RequestMethod.GET)
    public RestfulResponse<PermissionVo> findPermissionByUrl(
            @RequestParam(value = "permissionUrl", required = true) String permissionUrl,
            @RequestParam(value = "requestMethod", required = false) String requestMethod);


}