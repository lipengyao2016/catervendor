package com.vendor.service.impl;

import com.vendor.admin.Role;
import com.vendor.admin.User;
import com.vendor.admin.UserRole;
import com.vendor.admin.UserRoleExt;
import com.vendor.core.RequestHelper;
import com.vendor.entity.ListResponse;
import com.vendor.mapper.UserMapper;
import com.vendor.queryvo.admin.UserQueryVo;
import com.vendor.service.BaseMybaticsPlusServiceImpl;
import com.vendor.service.IUserRoleService;
import com.vendor.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.vendor.utils.BeanHelper;
import com.vendor.utils.DataNotFoundException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lpy
 * @since 2019-06-20
 */
@Service
public class UserServiceImpl
        extends BaseMybaticsPlusServiceImpl<UserMapper, User,UserQueryVo>
        implements IUserService {

    private static String passwordKey= "Q234JSDFKLAJ894JGKWEFAIODGJSAGA";

    @Autowired
    private IUserRoleService userRoleService;

    private String encyptPwd(String password)
    {
        return DigestUtils.sha256Hex( password + passwordKey);
    }

    @Override
    public Long create(UserRoleExt obj) {

        User user = new User();
        BeanHelper.copyPropertiesExcludeAttr(obj,user,new String[]{"roles"});
        user.setCurrentPassword(this.encyptPwd(user.getCurrentPassword()));
        Long userId = super.create(user);
        if(obj.getRoles().size() > 0 )
        {
           List<UserRole> userRoleList =  obj.getRoles().stream().map(roleId -> {
                UserRole userRole = new UserRole();
                userRole.setUserId(userId);
                userRole.setRoleId(roleId);
                return userRole;
            }).collect(Collectors.toList());
            userRoleService.batchInsert(userRoleList);
        }
        return userId;

    }

    @Override
    public UserRoleExt login(User obj) {

        UserQueryVo userQueryVo = new UserQueryVo();
        userQueryVo.setUserLoginId(obj.getUserLoginId());
        ListResponse<User>  findUserList = super.list(userQueryVo,null,null);
        if(findUserList.getItems().size() <= 0)
        {
            throw new DataNotFoundException(5002,"loginid is not exist");
        }
        User findUser = findUserList.getItems().get(0);
        if(!findUser.getCurrentPassword().equalsIgnoreCase(this.encyptPwd(obj.getCurrentPassword())))
        {
            throw new DataNotFoundException(5003,"password is not correct");
        }
        UserRoleExt userRoleExt = new UserRoleExt();
        BeanHelper.copyPropertiesExcludeAttr(findUser,userRoleExt,new String[]{"roles"});
        userRoleExt.setRoles(userRoleService.getRoleIDsByUserId(findUser.getId()));

        return userRoleExt;
    }
}
