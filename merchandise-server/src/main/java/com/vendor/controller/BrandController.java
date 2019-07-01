package com.vendor.controller;


import com.vendor.bean.merchandise.Brand;
import com.vendor.entity.ListResponse;
import com.vendor.queryvo.merchandise.BrandQueryVo;
import com.vendor.service.IBrandService;
import com.vendor.utils.ApiResponse;
import com.vendor.utils.CreateRes;
import com.vendor.utils.DataNotFoundException;
import com.vendor.utils.GsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 品牌。 前端控制器
 * </p>
 *
 * @author lpy
 * @since 2019-06-18
 */
@Api(tags={"品牌"},value = "Test")
@RestController
//@RequestMapping("/vendor/brand")
public class BrandController {

    private Log log = LogFactory.getLog(BrandController.class);

    @Autowired
    IBrandService brandService;


    @ApiOperation(value = "创建品牌")
    @RequestMapping(value = "/api/{ver}/brand", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public ApiResponse<CreateRes> createBrand(@PathVariable("ver") String version,
                                    @ApiParam(name = "品牌信息", required = true)       @RequestBody Brand brand) {

        log.info("brand:"+ " name:" + brand.getName() + ",version:" + version);

        System.out.println(brand.getName());
        
        Long lid = brandService.create(brand);
        ApiResponse<CreateRes> sucedResponse =  ApiResponse.getSucedResponse();
        CreateRes createRes = new CreateRes();
        createRes.setId(lid);
        sucedResponse.setData(createRes);
        return  sucedResponse;
    }

    @RequestMapping(value = "/api/{ver}/brand/batchCreate", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public ApiResponse batchCreateBrand(@PathVariable("ver") String version, @RequestBody List<Brand> brands) {

        log.info( ",version:" + version);


        boolean bRet = brandService.batchInsert(brands) ;
        return ApiResponse.getSucedResponse();
    }


    @RequestMapping(value = "/api/{ver}/brand/{brandUUID}", method = {RequestMethod.GET})
    @ResponseBody
    public Brand getBrand(@PathVariable("ver") String version,
                                @PathVariable("brandUUID") Long brandUUID) throws DataNotFoundException {
        log.info(",version:" + version);
        log.info("brandUUID  :" + brandUUID);


        return brandService.get(brandUUID);
    }

    @RequestMapping(value = "/api/{ver}/brand", method = {RequestMethod.GET})
    @ResponseBody
    public ListResponse<Brand> listBrand(@PathVariable("ver") String version, BrandQueryVo brand
            , Integer page, Integer rows) {
        log.info(",version:" + version);
        log.info("brand  :" + GsonUtils.ToJson(brand, BrandQueryVo.class));

        ListResponse<Brand> t1 = brandService.list(brand,page,rows);
        return t1;
    }

    @RequestMapping(value = "/api/{ver}/brand/{brandUUID}", method = {RequestMethod.POST})
    @ResponseBody
    public ApiResponse updateBrand(@PathVariable("ver") String version,
                                      @PathVariable("brandUUID") Long brandUUID,
                                      @RequestBody Brand updateBrand   ) {
        log.info(",version:" + version);
        log.info("brandUUID  :" + brandUUID);


        brandService.update(brandUUID,updateBrand);
        return ApiResponse.getSucedResponse();
    }

    @RequestMapping(value = "/api/{ver}/brand/{brandUUID}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public ApiResponse deleteBrand(@PathVariable("ver") String version,
                                      @PathVariable("brandUUID") Long brandUUID) {
        log.info(",version:" + version);
        log.info("brandUUID22  :" + brandUUID);


        brandService.delete(brandUUID);
        return  ApiResponse.getSucedResponse();
    }


    @RequestMapping(value = "/api/{ver}/brand/batchDelete", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public ApiResponse batchDeleteBrand(@PathVariable("ver") String version,
                                           @RequestBody List<Long> brandUUIDs) {
        log.info(",version:" + version);
        log.info("brandUUIDs  :" + brandUUIDs);

        brandService.batchDelete(brandUUIDs);
        return  ApiResponse.getSucedResponse();
    }


    @RequestMapping(value = "/api/{ver}/brand/batchUpdate",
            method = {RequestMethod.POST})
    @ResponseBody
    public ApiResponse batchUpdateBrand(@PathVariable("ver") String version,
                                           @RequestParam("brandUUIDs")  String brandUUIDsStr,
                                           @RequestBody Brand updateBrand   ) {
        log.info(",version:" + version);
        log.info("brandUUIDs  :" + brandUUIDsStr);
        List  brandUUIDs = GsonUtils.ToObjectList(brandUUIDsStr);

        brandService.batchUpdate(brandUUIDs,updateBrand);
        return ApiResponse.getSucedResponse();
    }


}

