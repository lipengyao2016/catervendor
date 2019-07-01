package com.vendor.controller;
import com.vendor.admin.Role;
import com.vendor.admin.RoleQueryVo;
import com.vendor.annotation.OperationLog;
import com.vendor.annotation.PermissionAuth;
/*import com.vendor.model.dto.RoleDto;
import com.vendor.model.pojo.RoleSearchCondition;*/
import com.vendor.entity.ListResponse;
import com.vendor.utils.ApiResponse;
import com.vendor.utils.CreateRes;
import com.vendor.admin.RolePermission;
import com.vendor.service.IRoleService;
import com.vendor.utils.GsonUtils;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController()
@Api(tags={"角色管理"},value = "Test")
//@RequestMapping("role")
//@PermissionAuth
public class RoleController {

    private Log log = LogFactory.getLog(RoleController.class);

    @Autowired
    IRoleService roleService;



    @OperationLog
    @ApiOperation(value = "新增角色")
    @PostMapping("/api/{ver}/roles")
    public ApiResponse<CreateRes> addRoleInfo(HttpServletRequest request,
                                              HttpServletResponse response,
                                              @PathVariable("ver") String version,
                                              @ApiParam(name = "角色信息",
                                                     required = true)
                                                 @RequestBody RolePermission dto)
            throws Exception {


        log.info("role:"+ " name:" + dto.getName() + ",version:" + version);



        Long lid = roleService.create(dto);
        ApiResponse<CreateRes> sucedResponse =  ApiResponse.getSucedResponse();
        CreateRes createRes = new CreateRes();
        createRes.setId(lid);
        sucedResponse.setData(createRes);
        return  sucedResponse;
    }

    @ApiOperation(value = "查询角色列表分页信息")
    @GetMapping("/api/{ver}/roles")

    public ListResponse<Role> list(@PathVariable("ver") String version,
                                   RoleQueryVo role
            , Integer page, Integer rows)

    {
        log.info(",version:" + version);
        log.info("role  :" + GsonUtils.ToJson(role, RoleQueryVo.class));

        ListResponse<Role> t1 = roleService.list(role,page,rows);
        return t1;
    }

   /* @NoPermissionAuth
    @ApiOperation(value = "查询角色详情")
    @GetMapping("/get/{rid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "rid", dataType = "java.lang.Long", value = "角色编码", defaultValue = "")})
    public RestfulResponse<RoleVo> get(@PathVariable("rid") Long rid) {
        RestfulResponse<RoleVo> result = new RestfulResponse<RoleVo>(new RoleVo());
        RoleVo roleVo = roleService.get(rid);
        if(roleVo == null){
            result.setMessage(ECode.NO_DATA_RESULT.getMessage());
            result.setCode(ECode.NO_DATA_RESULT.getCode());
        }
        result.setData(roleVo);
        return result;
    }

    @ApiOperation(value = "删除角色")
    @DeleteMapping("/delete/{rid}")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", name = "rid", dataType = "java.lang.Long", value = "角色编码", defaultValue = "")})
    public RestfulResponse<Void> delete(@PathVariable("rid") Long rid) {
        roleService.delete(rid);
        return new RestfulResponse<Void>();
    }


    @OperationLog
    @ApiOperation(value = "修改角色")
    @PostMapping("/update/updateRole")
    public RestfulResponse<Void> setUserInfo(HttpServletRequest request, HttpServletResponse response,
                                             @ApiParam(name = "角色信息", required = true) @RequestBody RoleDto dto) {
        RestfulResponse<Void> restful = new RestfulResponse<Void>();
        restful.setMajorKeyId(dto.getId().toString());
        restful.setOperateType(Response.UPDATE);
        restful.setTableName("role");

        roleService.update(dto);
        return restful;
    }*/
}
