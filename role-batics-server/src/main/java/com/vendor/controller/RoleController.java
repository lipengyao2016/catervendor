package com.vendor.controller;

import com.vendor.bean.role.Roles;
import com.vendor.entity.ListResponse;
import com.vendor.queryvo.RoleQueryVo;
import com.vendor.service.RoleBaticsServiceImpl;
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
    RoleBaticsServiceImpl roleBaticsService;


   /* @Autowired
    ApolloBootConfig apolloBootConfig;

    @Autowired
    ApolloConfigBean apolloConfigBean;*/



    @RequestMapping(value = "/api/{ver}/roles", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public Roles createRole(@PathVariable("ver") String version, @RequestBody Roles role) {

        log.info("role:" + role.getId()
                + " name:" + role.getName() + ",version:" + version);

        System.out.println(role.getName());

/*        log.info(" apollo config redis.host :" + apolloConfigBean.getProperty("redis.host"));
        log.info(" apollo config ServerDomain :" + apolloConfigBean.getProperty("server.domain"));*/

        //  Roles newRole = (Roles) this.getRoleService().create(role);

       Roles newRole = (Roles) roleBaticsService.getBaseService().create(role);
       return newRole;
        //return  null;
    }

    @RequestMapping(value = "/api/{ver}/roles/batchCreate", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public Integer batchCreateRole(@PathVariable("ver") String version, @RequestBody List<Roles> roles) {

        log.info( ",version:" + version);

        //  Roles newRole = (Roles) this.getRoleService().create(role);

       return  roleBaticsService.getBaseService().batchInsert(roles);
    }


    @RequestMapping(value = "/api/{ver}/roles/{roleId}", method = {RequestMethod.GET})
    @ResponseBody
    public Roles getRoles(@PathVariable("ver") String version, @PathVariable("roleId") Long roleId) throws  DataNotFoundException {
        log.info(",version:" + version);
        log.info("roleId  :" + roleId);
        if(roleId == 2 )
        {
            throw  new DataNotFoundException("5003","dd");
        }

        return roleBaticsService.getBaseService().get(roleId);
    }

    @RequestMapping(value = "/api/{ver}/roles", method = {RequestMethod.GET})
    @ResponseBody
    public ListResponse<Roles> listRoles(@PathVariable("ver") String version,
                                         RoleQueryVo role, Integer page, Integer rows) {
        log.info(",version:" + version);
        log.info("role  :" + GsonUtils.ToJson(role, RoleQueryVo.class));

        ListResponse<Roles> t1 = roleBaticsService.getBaseService().list(role,page,rows);
        return t1;
    }

    @RequestMapping(value = "/api/{ver}/roles/{roleId}", method = {RequestMethod.POST})
    @ResponseBody
    public Roles updateRoles(@PathVariable("ver") String version, @PathVariable("roleId") Long roleId,
                             @RequestBody Roles updateRole   ) {
        log.info(",version:" + version);
        log.info("roleId  :" + roleId);


        return roleBaticsService.getBaseService().update(roleId,updateRole);
    }

    @RequestMapping(value = "/api/{ver}/roles/{roleId}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public Roles deleteRoles(@PathVariable("ver") String version, @PathVariable("roleId") Long roleId) {
        log.info(",version:" + version);
        log.info("roleId22  :" + roleId);

        return roleBaticsService.getBaseService().delete(roleId);
    }


    @RequestMapping(value = "/api/{ver}/roles/batchDelete", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public Integer batchDeleteRoles(@PathVariable("ver") String version, @RequestBody List<Long> roleIds) {
        log.info(",version:" + version);
        log.info("roleIds  :" + roleIds);

        return roleBaticsService.getBaseService().batchDelete(roleIds);
    }


    @RequestMapping(value = "/api/{ver}/roles/batchUpdate", method = {RequestMethod.POST})
    @ResponseBody
    public Integer batchUpdateRoles(@PathVariable("ver") String version, String roleIdsStr,
                             @RequestBody Roles updateRole   ) {
        log.info(",version:" + version);
        log.info("roleIds  :" + roleIdsStr);
        List  roleIds = GsonUtils.ToObjectList(roleIdsStr);

        return roleBaticsService.getBaseService().batchUpdate(roleIds,updateRole) != null ? 1 : 0;
    }

}
