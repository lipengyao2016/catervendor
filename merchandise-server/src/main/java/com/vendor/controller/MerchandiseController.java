package com.vendor.controller;


import com.vendor.bean.merchandise.Merchandise;
import com.vendor.bean.merchandise.MerchandiseExt;
import com.vendor.entity.ListResponse;
import com.vendor.queryvo.merchandise.MerchandiseQueryVo;
import com.vendor.service.IMerchandiseService;
import com.vendor.utils.*;
import com.vendor.utils.ApiResponse;
import io.swagger.annotations.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品。 前端控制器
 * </p>
 *
 * @author lpy
 * @since 2019-06-18
 */
@Api(tags={"商品"},value = "Test")
@RestController
//@RequestMapping("/vendor/merchandise")
public class MerchandiseController {

    private Log log = LogFactory.getLog(MerchandiseController.class);
    //  @Autowired
    // merchandiseService merchandiseService;

    @Autowired
    IMerchandiseService merchandiseService;



    @ApiOperation(value = "创建商品")
    @RequestMapping(value = "/api/{ver}/merchandise", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public ApiResponse<CreateRes> createMerchandise(@PathVariable("ver") String version,
                                                    @ApiParam(name = "商品信息", required = true)@RequestBody Merchandise merchandise) {

        log.info("merchandise:"+ " name:" + merchandise.getName() + ",version:" + version);

        System.out.println(merchandise.getName());


        Long lid = merchandiseService.create(merchandise);
        ApiResponse<CreateRes> sucedResponse =  ApiResponse.getSucedResponse();
        CreateRes createRes = new CreateRes();
        createRes.setId(lid);
        sucedResponse.setData(createRes);
        return  sucedResponse;
    }

    @RequestMapping(value = "/api/{ver}/merchandise/batchCreate", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public ApiResponse batchCreateMerchandise(@PathVariable("ver") String version,
                                              @RequestBody List<Merchandise> merchandises) {
        

        log.info( ",version:" + version);

        boolean bRet = merchandiseService.batchInsert(merchandises) ;
        return ApiResponse.getSucedResponse();
    }


    @ApiOperation(value = "获取商品")
    @ApiImplicitParams({@ApiImplicitParam( name = "expand",value = "扩展属性,如 [category,brand]", required = false)})
    @RequestMapping(value = "/api/{ver}/merchandise/{merchandiseUUID}", method = {RequestMethod.GET})
    @ResponseBody
    public MerchandiseExt getMerchandise(@PathVariable("ver") String version,
                                         @PathVariable("merchandiseUUID") Long merchandiseUUID,
                                      @RequestParam(value = "expand",required = false) String expandStr
    )throws DataNotFoundException {
        log.info(",version:" + version);
        log.info("merchandiseUUID  :" + merchandiseUUID);


        List<String> expands = null;
        if(StringUtil.isNotEmpty(expandStr))
        {
            expands = (List<String>) GsonUtils.ToObject(expandStr,List.class);
        }
        else
        {
            expands = new ArrayList<>();
        }

        return merchandiseService.get(merchandiseUUID,expands);
    }



    @ApiImplicitParams({@ApiImplicitParam( name = "expand",value = "扩展属性,如 [category,brand]", required = false)})
    @RequestMapping(value = "/api/{ver}/merchandise", method = {RequestMethod.GET})
    @ResponseBody
    public ListResponse<MerchandiseExt> listMerchandise(@PathVariable("ver") String version,
                                                     MerchandiseQueryVo merchandise
                                                     , @RequestParam(value = "expand",required = false) String expandStr
            , Integer page, Integer rows) {
        log.info(",version:" + version);
        log.info("merchandise  :" + GsonUtils.ToJson(merchandise, MerchandiseQueryVo.class));
        List<String> expands = null;
        if(StringUtil.isNotEmpty(expandStr))
        {
            expands = (List<String>) GsonUtils.ToObject(expandStr,List.class);
        }
        else
        {
            expands = new ArrayList<>();
        }

        ListResponse<MerchandiseExt> t1 = merchandiseService.list(merchandise,page,rows,expands);
        return t1;
    }




    @RequestMapping(value = "/api/{ver}/merchandise/{merchandiseUUID}", method = {RequestMethod.POST})
    @ResponseBody
    public ApiResponse updateMerchandise(@PathVariable("ver") String version,
                                      @PathVariable("merchandiseUUID") Long merchandiseUUID,
                                      @RequestBody Merchandise updateMerchandise   ) {
        log.info(",version:" + version);
        log.info("merchandiseUUID  :" + merchandiseUUID);


        merchandiseService.update(merchandiseUUID,updateMerchandise);
        return ApiResponse.getSucedResponse();
    }

    @RequestMapping(value = "/api/{ver}/merchandise/{merchandiseUUID}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public ApiResponse deleteMerchandise(@PathVariable("ver") String version,
                                      @PathVariable("merchandiseUUID") Long merchandiseUUID) {
        log.info(",version:" + version);
        log.info("merchandiseUUID22  :" + merchandiseUUID);


        merchandiseService.delete(merchandiseUUID);
        return  ApiResponse.getSucedResponse();
    }


    @RequestMapping(value = "/api/{ver}/merchandise/batchDelete", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public ApiResponse batchDeleteMerchandise(@PathVariable("ver") String version,
                                           @RequestBody List<Long> merchandiseUUIDs) {
        log.info(",version:" + version);
        log.info("merchandiseUUIDs  :" + merchandiseUUIDs);


        merchandiseService.batchDelete(merchandiseUUIDs);
        return  ApiResponse.getSucedResponse();
    }


    @RequestMapping(value = "/api/{ver}/merchandise/batchUpdate",
            method = {RequestMethod.POST})
    @ResponseBody
    public ApiResponse batchUpdateMerchandise(@PathVariable("ver") String version,
                                           @RequestParam("merchandiseUUIDs")  String merchandiseUUIDsStr,
                                           @RequestBody Merchandise updateMerchandise   ) {
        log.info(",version:" + version);
        log.info("merchandiseUUIDs  :" + merchandiseUUIDsStr);
        List  merchandiseUUIDs = GsonUtils.ToObjectList(merchandiseUUIDsStr);

        merchandiseService.batchUpdate(merchandiseUUIDs,updateMerchandise);
        return ApiResponse.getSucedResponse();
    }



}

