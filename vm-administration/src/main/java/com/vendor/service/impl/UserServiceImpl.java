package com.vendor.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vendor.bean.user.*;
import com.vendor.config.DataSourceProperties;
import com.vendor.entity.ListResponse;
import com.vendor.mapper.UsersMapper;
import com.vendor.queryvo.user.UserCreateVo;
import com.vendor.queryvo.user.UserQueryVo;
import com.vendor.service.*;
import com.vendor.utils.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements IUserService {

    private Log log = LogFactory.getLog(UserServiceImpl.class);



    private UsersMapper UserDao;
    private IBaseService<Users,UserQueryVo> baseService;

    @Autowired
    private SnowFlake snowFlake;
    @Autowired
    private IUserOrganizationService userOrganizationService;

    @Autowired
    private IUserRoleMembershipService userRoleMembershipService;


   // @Autowired
    private SqlSessionFactory sqlSessionFactory;


    private DataSourceProperties dataSourceProperties;

    public UserServiceImpl()
    {
        System.out.println("UserServiceImpl init");
    }

    @Autowired(required = true)
    public UserServiceImpl(UsersMapper UserDao,
                           SqlSessionFactory sqlSessionFactory, DataSourceProperties dataSourceProperties)
    {
        this.UserDao = UserDao;
        this.baseService = new BaseMyBaticsServiceImpl<>(this.UserDao,Users.class
                ,UsersCriteria.class);
        this.sqlSessionFactory = sqlSessionFactory;


    }


    @Override
    public Users create(Users obj) {
         return this.baseService.create(obj);
    }

    @Override
    public Users get(String uuid) {
         return this.baseService.get(uuid);
    }

    @Override
    public ListResponse<Users> list(UserQueryVo queryObj , Integer page, Integer rows) {
        return this.baseService.list(queryObj,page,rows);
    }

    @Override
    public Users update(String uuid, Users updateObj) {
        return this.baseService.update(uuid,updateObj);
    }

    @Transactional
    @Override
    public Users delete(String uuid) {

        List<UserRoleMemberships> userRoleMembershipsList = userRoleMembershipService.getUserRoles(uuid);

        List<String> userRoleUUIDs = new ArrayList<>();
        for(UserRoleMemberships tempUserRole :userRoleMembershipsList )
        {
            userRoleUUIDs.add(tempUserRole.getUuid());
        }

        userRoleMembershipService.batchDelete(userRoleUUIDs);

        return this.baseService.delete(uuid);
    }

    @Override
    public Users update(Users updateObj) throws DataNotFoundException {
        return this.baseService.update(updateObj);
    }

    @Override
    public int batchInsert(List<Users> record) {
        return this.baseService.batchInsert(record);
    }

    @Override
    public int batchDelete(List<String> uuids) {
        return this.baseService.batchDelete(uuids);
    }

    @Override
    public Users batchUpdate(List<String> uuids, Users updateObj) throws DataNotFoundException {
        return this.baseService.batchUpdate(uuids,updateObj);
    }

    @Transactional()
    @Override
    public Users create(UserCreateVo userCreateVo) {
        Users newUser = new Users();
        if(userCreateVo.getOwnerUUID() != null)
        {
            UserOrganizations userOrganizations = userOrganizationService.getOrCreateUserOrganization(userCreateVo.getOwnerUUID());
            newUser.setUserorganizationuuid(userOrganizations.getUuid());

            log.info("userOrganizations:" + GsonUtils.ToJson(userOrganizations,UserOrganizations.class));
        }

        String[] excludeAtts ={"ownerUUID","roleUUID","userRoleMemberShips"};
        BeanHelper.copyPropertiesExcludeAttr(userCreateVo,newUser,excludeAtts);
        DBEntityUtils.preCreate(newUser);

        for(UserRoleMemberships userRoleMemberShips : userCreateVo.getUserRoleMemberShips())
        {
            DBEntityUtils.preCreate(userRoleMemberShips);
            userRoleMemberShips.setUseruuid(newUser.getUuid());
        }

        if(userCreateVo.getUserRoleMemberShips().size() > 0)
        {
            log.info("getUserRoleMemberShips:" + GsonUtils.ToJson(userCreateVo.getUserRoleMemberShips(),List.class));
            this.userRoleMembershipService.batchInsert(userCreateVo.getUserRoleMemberShips());
        }

      //   throw new DataNotFoundException("7010","insert Test");

        Users user = this.create(newUser);
        return user;
    }

    @Override
    public Users update(UserCreateVo userCreateVo) {
        Users user;
        try {
            Users updateUser = new Users();


            List<UserRoleMemberships> userRoleMembershipsList = userRoleMembershipService.getUserRoles(userCreateVo.getUuid());

            List<String> userRoleUUIDs = new ArrayList<>();
            for(UserRoleMemberships tempUserRole :userRoleMembershipsList )
            {
                userRoleUUIDs.add(tempUserRole.getUuid());
            }

            userRoleMembershipService.batchDelete(userRoleUUIDs);

            String[] excludeAtts ={"roleUUID","userRoleMemberShips"};
            BeanHelper.copyPropertiesExcludeAttr(userCreateVo,updateUser,excludeAtts);
            DBEntityUtils.preUpdate(updateUser);

            for(UserRoleMemberships userRoleMemberShips : userCreateVo.getUserRoleMemberShips())
            {
                DBEntityUtils.preCreate(userRoleMemberShips);
                userRoleMemberShips.setUseruuid(updateUser.getUuid());
            }

            if(userCreateVo.getUserRoleMemberShips().size() > 0)
            {
                log.info("getUserRoleMemberShips:" + GsonUtils.ToJson(userCreateVo.getUserRoleMemberShips(),List.class));
                this.userRoleMembershipService.batchInsert(userCreateVo.getUserRoleMemberShips());
            }

            log.info("updateUser:" + GsonUtils.ToJson(updateUser,Users.class));
             user = this.update(updateUser);

        }
        catch (Exception e)
        {
            throw  new DataNotFoundException("7001","create error");
        }
        return user;

    }

    @Override
    public ListResponse<UserRoleOrgs> getUserRole(UserRoleOrgQueryVo userRoleOrgQueryVo, Integer page, Integer rows) {

        if(page == null)
        {
            page = 1;
        }
        if(rows == null)
        {
            rows = 10000;
        }

        PageHelper.startPage(page -1, rows);

        userRoleOrgQueryVo.setName("%" + userRoleOrgQueryVo.getName() + "%");

        List<UserRoleOrgs>  userRoleOrgs = this.UserDao.getUserRole(userRoleOrgQueryVo);

        PageInfo pageInfo=new PageInfo<>(userRoleOrgs);
        ListResponse<UserRoleOrgs> response = new ListResponse<>();
        response.setItems(pageInfo.getList());
        response.setTotalSize( ((Long) pageInfo.getTotal()).intValue() );
        response.setTotalPageCount(pageInfo.getPages());
        response.setPageSize(rows);
        response.setCurrentPage(page);

        return  response;

    }


    @Override
    public String betchAddUser() {
        snowFlake=new SnowFlake();
        Random ra =new Random();
        long currentTimeMillis = System.currentTimeMillis();
        String result="执行开始："+currentTimeMillis+"-------\n";
        for (int i = 0; i < 10000; i++) {
            String nextId = snowFlake.nextId()+"";
            Users userCreateVo =new Users();
            userCreateVo.setUuid(nextId);
            userCreateVo.setAge(ra.nextInt(100)+1);
            userCreateVo.setName(ra.nextInt(10000)+1+"");
            userCreateVo.setEmail("testing@qq.com");
            userCreateVo.setMobile(ra.nextInt(11)+1+"");

            System.out.println(nextId);
            this.create(userCreateVo);
        }
        long currentTimeMillis2 = System.currentTimeMillis();
        result+="执行结束："+currentTimeMillis2+"-------\n";
        result+="耗时："+(currentTimeMillis2-currentTimeMillis)+"毫秒";
        return result;
    }


}
