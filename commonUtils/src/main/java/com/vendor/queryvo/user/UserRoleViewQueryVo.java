package com.vendor.queryvo.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class UserRoleViewQueryVo {

    private String name;

    private String owneruuid;

    private String mobile;

    private String roleuuid;

    private String useruuid;


    private String usercreateat;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwneruuid() {
        return owneruuid;
    }

    public void setOwneruuid(String owneruuid) {
        this.owneruuid = owneruuid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRoleuuid() {
        return roleuuid;
    }

    public void setRoleuuid(String roleuuid) {
        this.roleuuid = roleuuid;
    }

    public String getUseruuid() {
        return useruuid;
    }

    public void setUseruuid(String useruuid) {
        this.useruuid = useruuid;
    }

    public String getUsercreateat() {
        return usercreateat;
    }

    public void setUsercreateat(String usercreateat) {
        this.usercreateat = usercreateat;
    }

    public String getUsermodifiedat() {
        return usermodifiedat;
    }

    public void setUsermodifiedat(String usermodifiedat) {
        this.usermodifiedat = usermodifiedat;
    }

    private String usermodifiedat;
}
