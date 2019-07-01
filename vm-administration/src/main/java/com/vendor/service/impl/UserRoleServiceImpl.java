package com.vendor.service.impl;

import com.vendor.admin.Role;
import com.vendor.admin.User;
import com.vendor.admin.UserRole;
import com.vendor.entity.ListResponse;
import com.vendor.mapper.UserRoleMapper;
import com.vendor.queryvo.admin.UserRoleQueryVo;
import com.vendor.service.BaseMybaticsPlusServiceImpl;
import com.vendor.service.IUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class UserRoleServiceImpl
        extends BaseMybaticsPlusServiceImpl<UserRoleMapper, UserRole,UserRoleQueryVo>
        implements IUserRoleService {
    @Override
    public List<Long> getRoleIDsByUserId(Long userId) {
        UserRoleQueryVo userRole = new UserRoleQueryVo();
        userRole.setUserId(String.valueOf(userId));
        ListResponse<UserRole> retUserRoleList = this.list(userRole,null,null);
        List<Long> roleIds = retUserRoleList.getItems().stream()
                .map(tempUserRole->{
                    return  tempUserRole.getRoleId();
                }).collect(Collectors.toList());
        return roleIds;
    }
}
