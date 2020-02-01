package com.zhxh.data;

import com.zhxh.web.FilterExpression;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

public class DbQueryParameter extends HashMap<String, Object> {
    public DbQueryParameter() {
    }

    public DbQueryParameter(Class clazz, FilterExpression... expressions) {
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
