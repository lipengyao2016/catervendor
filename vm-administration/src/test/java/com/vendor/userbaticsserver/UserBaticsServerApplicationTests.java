package com.vendor.userbaticsserver;

import com.alibaba.fastjson.JSON;
import com.vendor.admin.User;
import com.vendor.admin.UserRoleExt;
import com.vendor.controller.UserController;
import com.vendor.utils.GsonUtils;
import com.vendor.utils.HttpClientUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserBaticsServerApplicationTests {

    private Log log = LogFactory.getLog(UserBaticsServerApplicationTests.class);
	@Test
	public void contextLoads() {
	}

	@Test
    public void createUser() {
        String url = "http://localhost:9020/api/v1/users";
        UserRoleExt userRoleExt = new UserRoleExt();
        String name = "xiaowang111";
        userRoleExt.setUserLoginId(name);
        userRoleExt.setName(name);
        userRoleExt.setPhone("13410156521");
        userRoleExt.setCurrentPassword("888888");
        List<Long> roleIds  = new ArrayList<>();
        roleIds.add(342259970923958272l);
        roleIds.add(342261372630994944l);

        userRoleExt.setRoles(roleIds);
        String retData = HttpClientUtil.doPost(url, GsonUtils.ToJson(userRoleExt,UserRoleExt.class));
        System.out.println(retData);

    }

    @Test
    public void loginUser() {
        String url = "http://localhost:9020//login/loginForToken";
        User userRoleExt = new User();
        String name = "xiaowang111";
        userRoleExt.setUserLoginId(name);
        userRoleExt.setCurrentPassword("888888");

        String retData = HttpClientUtil.doPost(url, GsonUtils.ToJson(userRoleExt,
                User.class));
        System.out.println(retData);

    }

}
