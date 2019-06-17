package com.vendor.service.impl;


import com.vendor.bean.user.*;
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
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    private Log log = LogFactory.getLog(UserServiceImpl.class);


    @Autowired
    private DBEntityUtils dbEntityUtils;


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
                           SqlSessionFactory sqlSessionFactory,
                           DataSourceProperties dataSourceProperties,
                           DBEntityUtils dbEntityUtils)
    {
        this.UserDao = UserDao;
        this.baseService = new BaseMyBaticsServiceImpl<>(this.UserDao,Users.class
                ,UsersCriteria.class,dbEntityUtils);
        this.sqlSessionFactory = sqlSessionFactory;


    }


    @Override
    public Users create(Users obj) {
         return this.baseService.create(obj);
    }

    @Override
    public Users get(Long id) {
         return this.baseService.get(id);
    }

    @Override
    public ListResponse<Users> list(UserQueryVo queryObj , Integer page, Integer rows) {
        return this.baseService.list(queryObj,page,rows);

       /* PageHelper.startPage(page, rows);

        UsersCriteria criteria = new UsersCriteria();
        List roles = this.UserDao.selectByExample(criteria);
        PageInfo pageInfo=new PageInfo<>(roles);
        ListResponse response = new ListResponse();
        response.setItems(pageInfo.getList());
        response.setTotalSize( ((Long) pageInfo.getTotal()).intValue() );
        response.setTotalPageCount(pageInfo.getPages());
        response.setPageSize(rows);
        response.setCurrentPage(page);

        return response;*/

    }

    @Override
    public Users update(Long id, Users updateObj) {
        return this.baseService.update(id,updateObj);
    }

    @Transactional
    @Override
    public Users delete(Long id) {

        List<UserRoleMemberships> userRoleMembershipsList = userRoleMembershipService.getUserRoles(id);

        List<Long> userRoleids = new ArrayList<>();
        for(UserRoleMemberships tempUserRole :userRoleMembershipsList )
        {
            userRoleids.add(tempUserRole.getId());
        }

        userRoleMembershipService.batchDelete(userRoleids);

        return this.baseService.delete(id);
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
    public int batchDelete(List<Long> ids) {
        return this.baseService.batchDelete(ids);
    }

    @Override
    public Users batchUpdate(List<Long> ids, Users updateObj) throws DataNotFoundException {
        return this.baseService.batchUpdate(ids,updateObj);
    }

    @Transactional()
    @Override
    public Users create(UserCreateVo userCreateVo) {
        Users newUser = new Users();
        if(userCreateVo.getOwnerId() != null)
        {
            UserOrganizations userOrganizations =
                    userOrganizationService.getOrCreateUserOrganization(
                            userCreateVo.getOwnerId());
            newUser.setUserOrganizationId(userOrganizations.getId());

            log.info("userOrganizations:"
                    + GsonUtils.ToJson(userOrganizations,UserOrganizations.class));
        }

        String[] excludeAtts ={"ownerId","roleId","userRoleMemberShips"};
        BeanHelper.copyPropertiesExcludeAttr(userCreateVo,newUser,excludeAtts);
        dbEntityUtils.preCreate(newUser);

        for(UserRoleMemberships userRoleMemberShips : userCreateVo.getUserRoleMemberShips())
        {
           // dbEntityUtils.preCreate(userRoleMemberShips);
            userRoleMemberShips.setUserId(newUser.getId());
        }

        if(userCreateVo.getUserRoleMemberShips().size() > 0)
        {
            log.info("getUserRoleMemberShips:" + GsonUtils.ToJson(userCreateVo.getUserRoleMemberShips(),List.class));
            this.userRoleMembershipService.batchInsert(
                    userCreateVo.getUserRoleMemberShips());
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


            List<UserRoleMemberships> userRoleMembershipsList =
                    userRoleMembershipService.getUserRoles(userCreateVo.getId());

            List<Long> userRoleids = new ArrayList<>();
            for(UserRoleMemberships tempUserRole :userRoleMembershipsList )
            {
                userRoleids.add(tempUserRole.getId());
            }

            userRoleMembershipService.batchDelete(userRoleids);

            String[] excludeAtts ={"roleId","userRoleMemberShips"};
            BeanHelper.copyPropertiesExcludeAttr(userCreateVo,updateUser,excludeAtts);
            //dbEntityUtils.preUpdate(updateUser);

            for(UserRoleMemberships userRoleMemberShips : userCreateVo.getUserRoleMemberShips())
            {
               // dbEntityUtils.preCreate(userRoleMemberShips);
                userRoleMemberShips.setUserId(updateUser.getId());
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

       /* if(page == null)
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

        return  response;*/
         return  null;

    }


    @Override
    public String betchAddUser() {
       /* snowFlake=new SnowFlake();
        Random ra =new Random();
        long currentTimeMillis = System.currentTimeMillis();
        String result="执行开始："+currentTimeMillis+"-------\n";
        for (int i = 0; i < 10000; i++) {
            String nextId = snowFlake.nextId()+"";
            Users userCreateVo =new Users();
            userCreateVo.setid(nextId);
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
        return result;*/
       return  "";
    }


}
