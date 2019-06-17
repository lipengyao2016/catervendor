package com.vendor.queryvo.user;

import java.util.List;

public class QueryParams<T> {

    private Integer page;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public T getQueryObj() {
        return queryObj;
    }

    public void setQueryObj(T queryObj) {
        this.queryObj = queryObj;
    }

    private Integer rows;

    private T queryObj;
}
