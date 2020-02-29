package com.zhxh.imms.data;

import com.zhxh.imms.web.FilterExpression;

import java.util.HashMap;

public class DbQuery extends HashMap<String, Object> {
    public DbQuery() {
    }

    public DbQuery(Class clazz, FilterExpression... expressions) {
        FilterExpression.fillWhere(clazz, this, expressions);
    }

    public Integer getLimit() {
        return (Integer) this.get("limit");
    }

    public Integer getPage() {
        return (Integer) this.get("page");
    }

    public Integer getStart() {
        return (Integer) this.get("start");
    }

    public String getOrderBy() {
        return (String) this.get("orderBy");
    }

    public String getWhere() {
        return (String) this.get("where");
    }

    public void setLimit(Integer limit) {
        this.put("limit", limit);
    }

    public void setOrderBy(String orderBy) {
        this.put("orderBy", orderBy);
    }

    public void setPage(int page) {
        this.put("page", page);
    }

    public void setStart(int start) {
        this.put("start", start);
    }

    public void setWhere(String where) {
        this.put("where", where);
    }
}
