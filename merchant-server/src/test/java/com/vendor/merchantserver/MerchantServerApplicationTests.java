package com.vendor.merchantserver;

import com.vendor.service.impl.MerchantNumberServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MerchantServerApplicationTests {

	@Autowired
	private MerchantNumberServiceImpl merchantNumberService;

	@Test
	public void contextLoads() {
	}

	@Test
	public  void testInitMerchantNumber()
	{
		int numberCnt = 1000;
		merchantNumberService.initNumber((short)8,numberCnt);
		System.out.println("testInitMerchantNumber ok");
	}

}
