package com.vendor.config.entity;

import java.util.List;
import java.util.regex.Pattern;

public class ConfigRule {
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getPlanKeys() {
        return planKeys;
    }

    public void setPlanKeys(List<String> planKeys) {
        this.planKeys = planKeys;
    }

    private List<String>  planKeys;

}
