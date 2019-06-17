package com.vendor.controller;

import com.vendor.bean.user.Departments;
import com.vendor.entity.ListResponse;
import com.vendor.queryvo.user.DepartmentQueryVo;
import com.vendor.service.IDepartmentService;
import com.vendor.utils.DataNotFoundException;
import com.vendor.utils.GsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/order")
public class DepartmentController {

    private Log log = LogFactory.getLog(DepartmentController.class);
  //  @Autowired
   // DepartmentService DepartmentService;

    @Autowired
    IDepartmentService DepartmentxxService;



    @RequestMapping(value = "/api/{ver}/departments", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public Departments createDepartment(@PathVariable("ver") String version,
                                        @RequestBody Departments department) {

        log.info("Department:" + department.getId()
                + " name:" + department.getName() + ",version:" + version);

        System.out.println(department.getName());
      //  Departments newDepartment = (Departments) this.getDepartmentService().create(Department);

       Departments newDepartment = (Departments) DepartmentxxService.create(department);
       return newDepartment;
        //return  null;
    }

    @RequestMapping(value = "/api/{ver}/departments/batchCreate", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public Integer batchCreateDepartment(@PathVariable("ver") String version,
                                         @RequestBody List<Departments> departments) {

        log.info( ",version:" + version);

        //  Departments newDepartment = (Departments) this.getDepartmentService().create(Department);

       return  DepartmentxxService.batchInsert(departments);
    }


    @RequestMapping(value = "/api/{ver}/departments/{DepartmentId}",
            method = {RequestMethod.GET})
    @ResponseBody
    public Departments getDepartments(@PathVariable("ver") String version,
                                      @PathVariable("departmentId") Long departmentId) throws  DataNotFoundException {
        log.info(",version:" + version);
        log.info("DepartmentId  :" + departmentId);
        if(departmentId.equals(2) )
        {
            throw  new DataNotFoundException("5003","dd");
        }

        return DepartmentxxService.get(departmentId);
    }

    @RequestMapping(value = "/api/{ver}/departments", method = {RequestMethod.GET})
    @ResponseBody
    public ListResponse<Departments> listDepartments(@PathVariable("ver") String version,
                                                     DepartmentQueryVo Department
    , Integer page, Integer rows) {
        log.info(",version:" + version);
        log.info("Department  :" + GsonUtils.ToJson(Department, DepartmentQueryVo.class));

        ListResponse<Departments> t1 = DepartmentxxService.list(Department,page,rows);
        return t1;
    }

    @RequestMapping(value = "/api/{ver}/departments/{departmentId}", method = {RequestMethod.POST})
    @ResponseBody
    public Departments updateDepartments(@PathVariable("ver") String version,
                                         @PathVariable("departmentId") Long departmentId,
                             @RequestBody Departments updateDepartment   ) {
        log.info(",version:" + version);
        log.info("DepartmentId  :" + departmentId);


        return DepartmentxxService.update(departmentId,updateDepartment);
    }

    @RequestMapping(value = "/api/{ver}/departments/{departmentId}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public Departments deleteDepartments(@PathVariable("ver") String version,
                                         @PathVariable("departmentId") Long departmentId) {
        log.info(",version:" + version);
        log.info("DepartmentId22  :" + departmentId);



        return DepartmentxxService.delete(departmentId);
    }


    @RequestMapping(value = "/api/{ver}/departments/batchDelete", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public Integer batchDeleteDepartments(@PathVariable("ver") String version,
                                          @RequestBody List<Long> departmentIds) {
        log.info(",version:" + version);
        log.info("DepartmentIds  :" + departmentIds);

        return DepartmentxxService.batchDelete(departmentIds);
    }


    @RequestMapping(value = "/api/{ver}/departments/batchUpdate", method = {RequestMethod.POST})
    @ResponseBody
    public Integer batchUpdateDepartments(@PathVariable("ver") String version,
                                          String departmentIdsStr,
                             @RequestBody Departments updateDepartment   ) {
        log.info(",version:" + version);
        log.info("DepartmentIds  :" + departmentIdsStr);
        List  DepartmentIds = GsonUtils.ToObjectList(departmentIdsStr);

        return DepartmentxxService.batchUpdate(DepartmentIds,updateDepartment) != null ? 1 : 0;
    }

}
