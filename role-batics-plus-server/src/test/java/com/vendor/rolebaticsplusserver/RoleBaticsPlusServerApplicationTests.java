package com.vendor.rolebaticsplusserver;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vendor.entity.Roles;
import com.vendor.mapper.RolesMapper;
//import com.vendor.utils.GsonUtils;
import com.vendor.utils.DBEntityUtils;
import com.vendor.utils.GsonUtils;
import com.vendor.utils.StrUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleBaticsPlusServerApplicationTests {

	@Autowired
	private RolesMapper rolesMapper;

	@Autowired
	private  DBEntityUtils dbEntityUtils;

	@Test
	public void contextLoads() {
	}

	@Test
	public void testSelect() {
		QueryWrapper<Roles> queryWrapper = new QueryWrapper<>();
		List<String>  statusQueryStr = new ArrayList<>();
		statusQueryStr.add("enabled");
		statusQueryStr.add("preEnabled");
		Date beginDate = null;
		Date endDate = null;
		try {
			 beginDate = StrUtils.convertStrToDate("2019-05-02 17:09:00");
			endDate = StrUtils.convertStrToDate("2019-05-22 23:36:43");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		queryWrapper.like("name","wangyuan").in("status",statusQueryStr)
		.isNull("description").between("createdAt",
				beginDate,endDate)
				.orderByAsc("createdAt");
		/*Map<String,Object>  eqMap = new HashMap<>();
		eqMap.put("status","enabled");
		eqMap.put("description",null);
		queryWrapper.allEq(eqMap);*/


		List<Roles> rolesList = rolesMapper.selectList(queryWrapper);
		System.out.println("size:" + rolesList.size());
		System.out.println("data:" + GsonUtils.ToJson(rolesList,List.class));
	}

	@Test
	public void testInsert() {
		Roles roles = new Roles();
	    roles.setName("buhuoyuan5");
		dbEntityUtils.preCreate(roles);
		int nInsertRet = rolesMapper.insert(roles);
		System.out.println("nInsertRet:" + nInsertRet + ",roles:" + GsonUtils.ToJson(roles,Roles.class));
	}

	@Test
	public void testUpdate() {
		Roles roles = new Roles();
		roles.setId(50l);
		roles.setName("buhuoyuan1");
		//roles.setVersion(0);
		dbEntityUtils.preUpdate(roles);
		int nUpdateRet = rolesMapper.updateById(roles);
		System.out.println("nUpdateRet:" + nUpdateRet + ",roles:" + GsonUtils.ToJson(roles,Roles.class));
	}

	@Test
	public void testDelete() {
		int nDeleteRet = rolesMapper.deleteById(55);
		System.out.println("nDeleteRet:" + nDeleteRet );
	}

	@Test
	public void testListPage() {
		QueryWrapper<Roles> queryWrapper = new QueryWrapper<>();
		List<String>  statusQueryStr = new ArrayList<>();
		statusQueryStr.add("enabled");
		statusQueryStr.add("preEnabled");
		Date beginDate = null;
		Date endDate = null;
		try {
			beginDate = StrUtils.convertStrToDate("2019-05-02 17:09:00");
			endDate = StrUtils.convertStrToDate("2019-05-22 23:36:43");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		queryWrapper
				//.like("name","wangyuan")
				.in("status",statusQueryStr)
				//.isNull("description")
				//.between("createdAt", beginDate,endDate)
				.orderByAsc("createdAt");

		//Integer count = rolesMapper.selectCount(queryWrapper);

		IPage<Roles> rolesIPage = new Page<Roles>(1, 2);

		IPage<Roles> rolesList =  rolesMapper.selectPage(rolesIPage,queryWrapper);
		System.out.println("size:" + rolesList.getCurrent());
		System.out.println("data:" + GsonUtils.ToJson(rolesList,Page.class));
	}

}
