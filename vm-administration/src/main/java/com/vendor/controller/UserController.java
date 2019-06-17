package com.vendor.controller;

import com.vendor.entity.ListResponse;
import com.vendor.bean.user.UserRoleOrgs;
import com.vendor.bean.user.Users;
import com.vendor.bean.user.UserRoleViews;
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
    public Users createUser(@PathVariable("ver") String version, @RequestBody UserCreateVo User) {

        log.info("User:" + User.getUuid() + " name:" + User.getName() + ",version:" + version);
        System.out.println(User.getName());

        long lCur = System.currentTimeMillis();
       Users newUser = (Users) UserService.create(User);
        log.info("createUser tm:" + (System.currentTimeMillis() - lCur));
       return newUser;
        //return  null;
    }


    @RequestMapping(value = "/api/{ver}/users/{UserUUID}", method = {RequestMethod.GET})
    @ResponseBody
    public Users getUsers(@PathVariable("ver") String version, @PathVariable("UserUUID") String UserUUID) throws  DataNotFoundException {
        log.info(",version:" + version);
        log.info("UserUUID  :" + UserUUID);
        if(UserUUID.contentEquals("2") )
        {
            throw  new DataNotFoundException("5003","dd");
        }

        return UserService.get(UserUUID);
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
    public ListResponse<Users> listUsers(@PathVariable("ver") String version, UserQueryVo User) {
        log.info(",version:" + version);
        log.info("User  :" + GsonUtils.ToJson(User, UserQueryVo.class));
        return UserService.list(User,User.getPage(),User.getRows());
    }

    @RequestMapping(value = "/api/{ver}/users/{UserUUID}", method = {RequestMethod.POST})
    @ResponseBody
    public Users updateUsers(@PathVariable("ver") String version, @PathVariable("UserUUID") String UserUUID,
                             @RequestBody UserCreateVo updateUser   ) {
        log.info(",version:" + version);
        log.info("UserUUID  :" + UserUUID);
        updateUser.setUuid(UserUUID);
        return UserService.update(updateUser);
    }

    @RequestMapping(value = "/api/{ver}/users/{UserUUID}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public Users deleteUsers(@PathVariable("ver") String version, @PathVariable("UserUUID") String UserUUID) {
        log.info(",version:" + version);
        log.info("UserUUID22  :" + UserUUID);

        return UserService.delete(UserUUID);
    }

    @RequestMapping(value = "/api/{ver}/userRoleOrgs", method = {RequestMethod.GET})
    @ResponseBody
    public ListResponse<UserRoleOrgs> findUserListByOrgaUUID(@PathVariable("ver") String version, UserRoleOrgQueryVo userRoleOrgQueryVo
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
    public ListResponse<UserRoleOrgs> findUserListByOrgaUUID(@PathVariable("ver") String version, String orgaUUID, Integer page, Integer rows) {
        log.info(",version:" + version);
        return UserService.findUserListByOrgaUUID(orgaUUID,page,rows);
    }*/

}
