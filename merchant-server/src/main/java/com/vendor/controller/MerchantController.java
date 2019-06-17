package com.vendor.controller;


import com.vendor.bean.merchant.Merchant;
import com.vendor.entity.ListResponse;
import com.vendor.queryvo.merchant.MerchantQueryVo;
import com.vendor.service.IMerchantNumberService;
import com.vendor.service.IMerchantService;
import com.vendor.service.impl.MerchantServiceImpl;
import com.vendor.utils.DataNotFoundException;
import com.vendor.utils.GsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.List;

@RestController
//@RequestMapping("/order")
public class MerchantController {

    private Log log = LogFactory.getLog(MerchantController.class);
  //  @Autowired
   // merchantService merchantService;

    @Autowired
    IMerchantService MerchantService;

    @Autowired
    IMerchantNumberService merchantNumberService;


   /* @Autowired
    ApolloBootConfig apolloBootConfig;

    @Autowired
    ApolloConfigBean apolloConfigBean;*/



    @RequestMapping(value = "/api/{ver}/merchants", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public Merchant createMerchant(HttpServletRequest request, @PathVariable("ver") String version, @RequestBody Merchant merchant) {

      // Enumeration<String> attrEnums = request.getAttributeNames();
/*
        while (attrEnums.hasMoreElements())
        {
            String attrKey = attrEnums.nextElement();
            log.info(" attr key:" +  attrKey
                    + ",value:" + request.getAttribute(attrKey));
        }*/

        long lCur = System.currentTimeMillis();

        log.info("merchant:" + merchant.getId()
                + " name:" + merchant.getName() + ",version:" + version);

        System.out.println(merchant.getName());

/*        log.info(" apollo config redis.host :" + apolloConfigBean.getProperty("redis.host"));
        log.info(" apollo config ServerDomain :" + apolloConfigBean.getProperty("server.domain"));*/

        //  Merchant newMerchant = (Merchant) this.getMerchantService().create(merchant);

       Merchant newMerchant = (Merchant) MerchantService.create(merchant);

       log.info("createMerchant tm:" + (System.currentTimeMillis() - lCur));
       return newMerchant;
        //return  null;
    }

    @RequestMapping(value = "/api/{ver}/merchants/batchCreate", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public Integer batchCreateMerchant(@PathVariable("ver") String version, @RequestBody List<Merchant> merchants) {

        log.info( ",version:" + version);

        //  Merchant newMerchant = (Merchant) this.getMerchantService().create(merchant);

       return  MerchantService.getBaseService().batchInsert(merchants);
    }


    @RequestMapping(value = "/api/{ver}/merchants/{merchantId}", method = {RequestMethod.GET})
    @ResponseBody
    public Merchant getMerchant(@PathVariable("ver") String version, @PathVariable("merchantId") Long merchantId) throws  DataNotFoundException {
        log.info(",version:" + version);
        log.info("merchantId  :" + merchantId);
        if(merchantId == 2 )
        {
            throw  new DataNotFoundException(5003,"dd");
        }

        return MerchantService.getBaseService().get(merchantId);
    }

    @RequestMapping(value = "/api/{ver}/merchants", method = {RequestMethod.GET})
    @ResponseBody
    public ListResponse<Merchant> listMerchant(@PathVariable("ver") String version,
                                               MerchantQueryVo merchant, Integer page, Integer rows) {
        log.info(",version:" + version);
        log.info("merchant  :" + GsonUtils.ToJson(merchant, MerchantQueryVo.class));

        ListResponse<Merchant> t1 = MerchantService.getBaseService().list(merchant,page,rows);
        return t1;
    }

    @RequestMapping(value = "/api/{ver}/merchants/{merchantId}", method = {RequestMethod.POST})
    @ResponseBody
    public Merchant updateMerchant(@PathVariable("ver") String version, @PathVariable("merchantId") Long merchantId,
                             @RequestBody Merchant updateMerchant   ) {
        log.info(",version:" + version);
        log.info("merchantId  :" + merchantId);

        Merchant retMerchant =  MerchantService.getBaseService().update(merchantId,updateMerchant);

        return  retMerchant;
    }

    @RequestMapping(value = "/api/{ver}/merchants/{merchantId}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public Merchant deleteMerchant(@PathVariable("ver") String version, @PathVariable("merchantId") Long merchantId) {
        log.info(",version:" + version);
        log.info("merchantId22  :" + merchantId);

        return MerchantService.getBaseService().delete(merchantId);
    }


    @RequestMapping(value = "/api/{ver}/merchants/batchDelete", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public Integer batchDeleteMerchant(@PathVariable("ver") String version, @RequestBody List<Long> merchantIds) {
        log.info(",version:" + version);
        log.info("merchantIds  :" + merchantIds);

        return MerchantService.getBaseService().batchDelete(merchantIds);
    }


    @RequestMapping(value = "/api/{ver}/merchants/batchUpdate", method = {RequestMethod.POST})
    @ResponseBody
    public Integer batchUpdateMerchant(@PathVariable("ver") String version, String merchantIdsStr,
                             @RequestBody Merchant updateMerchant   ) {
        log.info(",version:" + version);
        log.info("merchantIds  :" + merchantIdsStr);
        List  merchantIds = GsonUtils.ToObjectList(merchantIdsStr);

        return MerchantService.getBaseService().batchUpdate(merchantIds,updateMerchant) != null ? 1 : 0;
    }


    @RequestMapping(value = "/api/{ver}/merchantNumbers", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public String createMerchantNumber(HttpServletRequest request,
                                       @PathVariable("ver") String version, Short merchantType, Integer numberCnt) {

        log.info("merchantType:" + merchantType
                + " numberCnt:" + numberCnt + ",version:" + version);


        merchantNumberService.initNumber(merchantType,numberCnt);
        return "success";
    }


}
