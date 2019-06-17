package com.vendor.controller;

import com.vendor.bean.user.Users;
import com.vendor.entity.ListResponse;
import com.vendor.bean.user.UserRoleOrgs;
import com.vendor.bean.user.UserRoleViews;
import com.vendor.queryvo.user.QueryParams;
import com.vendor.queryvo.user.UserCreateVo;
import com.vendor.queryvo.user.UserQueryVo;
import com.vendor.bean.user.UserRoleOrgQueryVo;
import com.vendor.queryvo.user.UserRoleViewQueryVo;
import com.vendor.service.IUserRoleViewService;
import com.vendor.service.IUserService;
import com.vendor.utils.DataNotFoundException;
import com.vendor.utils.GsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/order")
public class UserController {

    private Log log = LogFactory.getLog(UserController.class);


    @Autowired
    IUserService UserService;

    @Autowired
    IUserRoleViewService userRoleViewService;


    @RequestMapping(value = "/api/{ver}/users", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public Users createUser(@PathVariable("ver") String version,
                            @RequestBody UserCreateVo User) {

        log.info("User:" + User.getId() + " name:" + User.getName() + ",version:" + version);
        System.out.println(User.getName());

        long lCur = System.currentTimeMillis();
       Users newUser = (Users) UserService.create(User);
        log.info("createUser tm:" + (System.currentTimeMillis() - lCur));
       return newUser;
        //return  null;
    }


    @RequestMapping(value = "/api/{ver}/users/{UserId}", method = {RequestMethod.GET})
    @ResponseBody
    public Users getUsers(@PathVariable("ver") String version,
                          @PathVariable("UserId") Long UserId) throws  DataNotFoundException {
        log.info(",version:" + version);
        log.info("UserId  :" + UserId);
        if(UserId.equals(2) )
        {
            throw  new DataNotFoundException("5003","dd");
        }

        return UserService.get(UserId);
    }

    @RequestMapping(value = "/api/batchUsers", method = {RequestMethod.GET})
    @ResponseBody
    public String batchUsers() throws  DataNotFoundException {
        String result="";
        try {
            result= UserService.betchAddUser();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @RequestMapping(value = "/api/{ver}/users", method = {RequestMethod.GET})
    @ResponseBody
    public ListResponse<Users> listUsers(@PathVariable("ver") String version,
                                         UserQueryVo UserQuery,Integer page,
                                         Integer rows) {
        log.info(",version:" + version);
        log.info("User  :" + GsonUtils.ToJson(UserQuery, UserQueryVo.class));
        return UserService.list(UserQuery
                ,page,rows);
    }

    @RequestMapping(value = "/api/{ver}/users/{UserId}", method = {RequestMethod.POST})
    @ResponseBody
    public Users updateUsers(@PathVariable("ver") String version,
                             @PathVariable("UserId") Long UserId,
                             @RequestBody UserCreateVo updateUser   ) {
        log.info(",version:" + version);
        log.info("UserId  :" + UserId);
        updateUser.setId(UserId);
        return UserService.update(updateUser);
    }

    @RequestMapping(value = "/api/{ver}/users/{UserId}",
            method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public Users deleteUsers(@PathVariable("ver") String version,
                             @PathVariable("UserId") Long UserId) {
        log.info(",version:" + version);
        log.info("UserId22  :" + UserId);

        return UserService.delete(UserId);
    }

    @RequestMapping(value = "/api/{ver}/userRoleOrgs", method = {RequestMethod.GET})
    @ResponseBody
    public ListResponse<UserRoleOrgs> findUserListByOrgaId(@PathVariable("ver") String version, UserRoleOrgQueryVo userRoleOrgQueryVo
            , Integer page, Integer rows) {
        log.info(",version:" + version);
        return UserService.getUserRole(userRoleOrgQueryVo,page,rows);
    }

    @RequestMapping(value = "/api/{ver}/userRoleViews", method = {RequestMethod.GET})
    @ResponseBody
    public ListResponse<UserRoleViews> listUserRoleViews(@PathVariable("ver") String version,
                                                         UserRoleViewQueryVo userRoleViews
            , Integer page, Integer rows) {
        log.info(",version:" + version);
        return userRoleViewService.list(userRoleViews,page,rows);
    }


/*    @RequestMapping(value = "/api/{ver}/userRoleOrgs", method = {RequestMethod.GET})
    @ResponseBody
    public ListResponse<UserRoleOrgs> findUserListByOrgaId(@PathVariable("ver") String version, String orgaId, Integer page, Integer rows) {
        log.info(",version:" + version);
        return UserService.findUserListByOrgaId(orgaId,page,rows);
    }*/

}
