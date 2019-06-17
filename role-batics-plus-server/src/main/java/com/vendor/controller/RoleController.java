package com.vendor.controller;


import com.vendor.entity.ListResponse;
import com.vendor.entity.Roles;
import com.vendor.queryvo.RoleQueryVo;
import com.vendor.service.IRolesService;
import com.vendor.utils.ApiResponse;
import com.vendor.utils.DataNotFoundException;
import com.vendor.utils.GsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/order")
public class RoleController {

    private Log log = LogFactory.getLog(RoleController.class);
  //  @Autowired
   // roleService roleService;

    @Autowired
    IRolesService rolexxService;



    @RequestMapping(value = "/api/{ver}/roles", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public ApiResponse createRole(@PathVariable("ver") String version, @RequestBody Roles role) {

        log.info("role:"+ " name:" + role.getName() + ",version:" + version);

        System.out.println(role.getName());

        //  Roles newRole = (Roles) this.getRoleService().create(role);
       // DBEntityUtils.preCreate(role);
        boolean bRet = rolexxService.create(role);
        return ApiResponse.getSucedResponse();
        //return  null;
    }

    @RequestMapping(value = "/api/{ver}/roles/batchCreate", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public ApiResponse batchCreateRole(@PathVariable("ver") String version, @RequestBody List<Roles> roles) {

        log.info( ",version:" + version);

        //  Roles newRole = (Roles) this.getRoleService().create(role);
     /*   for(Roles role:roles)
        {
            DBEntityUtils.preCreate(role);
        }
*/
        boolean bRet = rolexxService.batchInsert(roles) ;
        return ApiResponse.getSucedResponse();
    }


    @RequestMapping(value = "/api/{ver}/roles/{roleUUID}", method = {RequestMethod.GET})
    @ResponseBody
    public Roles getRoles(@PathVariable("ver") String version,
                          @PathVariable("roleUUID") Long roleUUID) throws  DataNotFoundException {
        log.info(",version:" + version);
        log.info("roleUUID  :" + roleUUID);

      /*  QueryWrapper<Roles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid",roleUUID);
        Roles role =  rolexxService.getOne(queryWrapper);
        return role;*/

      return rolexxService.get(roleUUID);
    }

    @RequestMapping(value = "/api/{ver}/roles", method = {RequestMethod.GET})
    @ResponseBody
    public ListResponse<Roles> listRoles(@PathVariable("ver") String version, RoleQueryVo role
    , Integer page, Integer rows) {
        log.info(",version:" + version);
        log.info("role  :" + GsonUtils.ToJson(role, RoleQueryVo.class));

        ListResponse<Roles> t1 = rolexxService.list(role,page,rows);
        return t1;
    }

    @RequestMapping(value = "/api/{ver}/roles/{roleUUID}", method = {RequestMethod.POST})
    @ResponseBody
    public ApiResponse updateRoles(@PathVariable("ver") String version,
                                   @PathVariable("roleUUID") Long roleUUID,
                             @RequestBody Roles updateRole   ) {
        log.info(",version:" + version);
        log.info("roleUUID  :" + roleUUID);
      /*  UpdateWrapper<Roles> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("uuid",roleUUID);

        DBEntityUtils.preUpdate(updateRole);
        boolean bRet = rolexxService.update(updateRole,updateWrapper);
        return  updateRole;*/

        rolexxService.update(roleUUID,updateRole);
        return ApiResponse.getSucedResponse();
    }

    @RequestMapping(value = "/api/{ver}/roles/{roleUUID}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public ApiResponse deleteRoles(@PathVariable("ver") String version,
                             @PathVariable("roleUUID") Long roleUUID) {
        log.info(",version:" + version);
        log.info("roleUUID22  :" + roleUUID);

  /*      QueryWrapper<Roles> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uuid",roleUUID);
        rolexxService.remove(queryWrapper);
        return "success";*/

        rolexxService.delete(roleUUID);
        return  ApiResponse.getSucedResponse();
    }


    @RequestMapping(value = "/api/{ver}/roles/batchDelete", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public ApiResponse batchDeleteRoles(@PathVariable("ver") String version,
                                    @RequestBody List<Long> roleUUIDs) {
        log.info(",version:" + version);
        log.info("roleUUIDs  :" + roleUUIDs);

     /*   QueryWrapper<Roles> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("uuid",roleUUIDs);
        return rolexxService.remove(queryWrapper)?1:0;*/

        rolexxService.batchDelete(roleUUIDs);
        return  ApiResponse.getSucedResponse();
    }


    @RequestMapping(value = "/api/{ver}/roles/batchUpdate",
            method = {RequestMethod.POST})
    @ResponseBody
    public ApiResponse batchUpdateRoles(@PathVariable("ver") String version,
                                    @RequestParam("roleUUIDs")  String roleUUIDsStr,
                             @RequestBody Roles updateRole   ) {
        log.info(",version:" + version);
        log.info("roleUUIDs  :" + roleUUIDsStr);
        List  roleUUIDs = GsonUtils.ToObjectList(roleUUIDsStr);

      /*  QueryWrapper<Roles> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("uuid",roleUUIDs);
        DBEntityUtils.preUpdate(updateRole);
        return rolexxService.update(updateRole,queryWrapper)  ? 1 : 0;*/

         rolexxService.batchUpdate(roleUUIDs,updateRole);
         return ApiResponse.getSucedResponse();
    }

}
