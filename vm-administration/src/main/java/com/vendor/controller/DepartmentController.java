package com.vendor.controller;

import com.vendor.entity.ListResponse;
import com.vendor.bean.user.Departments;
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



    @RequestMapping(value = "/api/{ver}/Departments", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public Departments createDepartment(@PathVariable("ver") String version, @RequestBody Departments Department) {

        log.info("Department:" + Department.getUuid() + " name:" + Department.getName() + ",version:" + version);

        System.out.println(Department.getName());
      //  Departments newDepartment = (Departments) this.getDepartmentService().create(Department);

       Departments newDepartment = (Departments) DepartmentxxService.create(Department);
       return newDepartment;
        //return  null;
    }

    @RequestMapping(value = "/api/{ver}/Departments/batchCreate", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.CREATED)
    public Integer batchCreateDepartment(@PathVariable("ver") String version, @RequestBody List<Departments> Departments) {

        log.info( ",version:" + version);

        //  Departments newDepartment = (Departments) this.getDepartmentService().create(Department);

       return  DepartmentxxService.batchInsert(Departments);
    }


    @RequestMapping(value = "/api/{ver}/Departments/{DepartmentUUID}", method = {RequestMethod.GET})
    @ResponseBody
    public Departments getDepartments(@PathVariable("ver") String version, @PathVariable("DepartmentUUID") String DepartmentUUID) throws  DataNotFoundException {
        log.info(",version:" + version);
        log.info("DepartmentUUID  :" + DepartmentUUID);
        if(DepartmentUUID.contentEquals("2") )
        {
            throw  new DataNotFoundException("5003","dd");
        }

        return DepartmentxxService.get(DepartmentUUID);
    }

    @RequestMapping(value = "/api/{ver}/Departments", method = {RequestMethod.GET})
    @ResponseBody
    public ListResponse<Departments> listDepartments(@PathVariable("ver") String version, DepartmentQueryVo Department
    , Integer page, Integer rows) {
        log.info(",version:" + version);
        log.info("Department  :" + GsonUtils.ToJson(Department, DepartmentQueryVo.class));

        ListResponse<Departments> t1 = DepartmentxxService.list(Department,page,rows);
        return t1;
    }

    @RequestMapping(value = "/api/{ver}/Departments/{DepartmentUUID}", method = {RequestMethod.POST})
    @ResponseBody
    public Departments updateDepartments(@PathVariable("ver") String version, @PathVariable("DepartmentUUID") String DepartmentUUID,
                             @RequestBody Departments updateDepartment   ) {
        log.info(",version:" + version);
        log.info("DepartmentUUID  :" + DepartmentUUID);


        return DepartmentxxService.update(DepartmentUUID,updateDepartment);
    }

    @RequestMapping(value = "/api/{ver}/Departments/{DepartmentUUID}", method = {RequestMethod.DELETE})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public Departments deleteDepartments(@PathVariable("ver") String version, @PathVariable("DepartmentUUID") String DepartmentUUID) {
        log.info(",version:" + version);
        log.info("DepartmentUUID22  :" + DepartmentUUID);



        return DepartmentxxService.delete(DepartmentUUID);
    }


    @RequestMapping(value = "/api/{ver}/Departments/batchDelete", method = {RequestMethod.POST})
    @ResponseBody
    @ResponseStatus(code= HttpStatus.NO_CONTENT)
    public Integer batchDeleteDepartments(@PathVariable("ver") String version, @RequestBody List<String> DepartmentUUIDs) {
        log.info(",version:" + version);
        log.info("DepartmentUUIDs  :" + DepartmentUUIDs);

        return DepartmentxxService.batchDelete(DepartmentUUIDs);
    }


    @RequestMapping(value = "/api/{ver}/Departments/batchUpdate", method = {RequestMethod.POST})
    @ResponseBody
    public Integer batchUpdateDepartments(@PathVariable("ver") String version, String DepartmentUUIDsStr,
                             @RequestBody Departments updateDepartment   ) {
        log.info(",version:" + version);
        log.info("DepartmentUUIDs  :" + DepartmentUUIDsStr);
        List  DepartmentUUIDs = GsonUtils.ToObjectList(DepartmentUUIDsStr);

        return DepartmentxxService.batchUpdate(DepartmentUUIDs,updateDepartment) != null ? 1 : 0;
    }

}
