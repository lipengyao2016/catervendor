package com.vendor.controller;


import com.vendor.bean.merchandise.Category;
import com.vendor.bean.merchandise.CategoryExt;
import com.vendor.entity.ListResponse;
import com.vendor.queryvo.merchandise.CategoryQueryVo;
import com.vendor.service.ICategoryService;
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
 * 商品分类。 前端控制器
 * </p>
 *
 * @author lpy
 * @since 2019-06-18
 */
@Api(tags={"分类管理"},value = "Test")
@RestController
//@RequestMapping("/vendor/category")
public class CategoryController {

    private Log log = LogFactory.getLog(CategoryController.class);
    //  @Autowired
    // categoryService categoryService;

    @Autowired
    ICategoryService categoryService;


    @ApiOperation(value = "创建分类")
    @RequestMapping(value = "/api/{ver}/category", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public ApiResponse<CreateRes> createCategory(@PathVariable("ver") String version,
                            @ApiParam(name = "分类信息", required = true) @RequestBody Category category) {

        log.info("category:"+ " name:" + category.getName() + ",version:" + version);

        System.out.println(category.getName());

        Long lid = categoryService.create(category);
        ApiResponse<CreateRes> sucedResponse =  ApiResponse.getSucedResponse();
        CreateRes createRes = new CreateRes();
        createRes.setId(lid);
        sucedResponse.setData(createRes);
        return  sucedResponse;
    }

    @ApiOperation(value = "批量创建分类")
    @RequestMapping(value = "/api/{ver}/category/batchCreate", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public ApiResponse batchCreateCategory(@PathVariable("ver") String version,
                                           @RequestBody List<Category> categories) {

        log.info( ",version:" + version);


        boolean bRet = categoryService.batchInsert(categories) ;
        return ApiResponse.getSucedResponse();
    }


    @ApiOperation(value = "获取分类")
    @RequestMapping(value = "/api/{ver}/category/{categoryUUID}", method = {RequestMethod.GET})
    @ResponseBody
    public Category getCategory(@PathVariable("ver") String version,
                          @PathVariable("categoryUUID") Long categoryUUID) throws DataNotFoundException {
        log.info(",version:" + version);
        log.info("categoryUUID  :" + categoryUUID);


        return categoryService.get(categoryUUID);
    }

    @ApiOperation(value = "列表分类")
    @RequestMapping(value = "/api/{ver}/category", method = {RequestMethod.GET})
    @ResponseBody
    public ListResponse<Category> listCategory(@PathVariable("ver") String version, CategoryQueryVo category
            , Integer page, Integer rows) {
        log.info(",version:" + version);
        log.info("category  :" + GsonUtils.ToJson(category, CategoryQueryVo.class));

        ListResponse<Category> t1 = categoryService.list(category,page,rows);
        return t1;
    }

    @ApiOperation(value = "更新分类")
    @RequestMapping(value = "/api/{ver}/category/{categoryUUID}", method = {RequestMethod.POST})
    @ResponseBody
    public ApiResponse updateCategory(@PathVariable("ver") String version,
                                   @PathVariable("categoryUUID") Long categoryUUID,
                                   @RequestBody Category updateCategory   ) {
        log.info(",version:" + version);
        log.info("categoryUUID  :" + categoryUUID);


        categoryService.update(categoryUUID,updateCategory);
        return ApiResponse.getSucedResponse();
    }

    @RequestMapping(value = "/api/{ver}/category/{categoryUUID}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public ApiResponse deleteCategory(@PathVariable("ver") String version,
                                   @PathVariable("categoryUUID") Long categoryUUID) {
        log.info(",version:" + version);
        log.info("categoryUUID22  :" + categoryUUID);


        categoryService.delete(categoryUUID);
        return  ApiResponse.getSucedResponse();
    }


    @RequestMapping(value = "/api/{ver}/category/batchDelete", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public ApiResponse batchDeleteCategory(@PathVariable("ver") String version,
                                        @RequestBody List<Long> categoryUUIDs) {
        log.info(",version:" + version);
        log.info("categoryUUIDs  :" + categoryUUIDs);



        categoryService.batchDelete(categoryUUIDs);
        return  ApiResponse.getSucedResponse();
    }


    @RequestMapping(value = "/api/{ver}/category/batchUpdate",
            method = {RequestMethod.POST})
    @ResponseBody
    public ApiResponse batchUpdateCategory(@PathVariable("ver") String version,
                                        @RequestParam("categoryUUIDs")  String categoryUUIDsStr,
                                        @RequestBody Category updateCategory   ) {
        log.info(",version:" + version);
        log.info("categoryUUIDs  :" + categoryUUIDsStr);
        List  categoryUUIDs = GsonUtils.ToObjectList(categoryUUIDsStr);

        categoryService.batchUpdate(categoryUUIDs,updateCategory);
        return ApiResponse.getSucedResponse();
    }


    @ApiOperation(value = "列表分类，按树形结构排列")
    @RequestMapping(value = "/api/{ver}/category/tree", method = {RequestMethod.GET})
    @ResponseBody
    public List<CategoryExt> listTreeCategory(@PathVariable("ver") String version)
    {
        log.info(",version:" + version);
        return categoryService.listTreeCategory();

    }

}

