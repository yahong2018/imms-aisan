package com.zhxh.web;

import com.google.gson.Gson;
import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.data.domain.Entity;
import com.zhxh.utils.GlobalConstants;
import com.zhxh.utils.Logger;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = RuntimeException.class)
public abstract class CrudController<T extends Entity> {
    protected abstract CrudLogic<T> getLogic();

    @PostMapping("create")
    public int create(T item) {
        return this.getLogic().create(item);
    }

    @PostMapping("update")
    public int update(T item) {
        return this.getLogic().update(item);
    }

    @PostMapping("deleteAll")
    public int deleteAll(@RequestBody Long[] ids) {
        return this.getLogic().deleteAll(ids);
    }

    @PostMapping("delete")
    public int delete(Long id)  {
        return this.getLogic().delete(id);
    }

    @RequestMapping("getAll")
    public ExtJsStoreQueryResult getAll() {
        DbQueryParameter queryParameter = this.buildDbQueryFromRequest(this.getLogic().getItemClass());
        return getAllByQuery(queryParameter);
    }

    protected ExtJsStoreQueryResult getAllByQuery(DbQueryParameter query) {
        List<T> list = this.getLogic().getAll(query);
        int total = this.getLogic().getPageTotal(query);

        ExtJsStoreQueryResult result = new ExtJsStoreQueryResult();
        result.setRootProperty(list);
        result.setTotal(total);

        return result;
    }

    protected DbQueryParameter buildDbQueryFromRequest(Class clazz) {
        DbQueryParameter queryMap = getFilterStringFromRequest(clazz);

        HttpServletRequest request = GlobalConstants.getRequest();
        final String[] orderBy = {request.getParameter("orderBy")};
        if (!StringUtils.isEmpty(orderBy[0])) {
            Map<String, String> fieldsMap = CrudLogic.getFieldsMap(clazz);
            fieldsMap.forEach((k, v) -> {
                String placeHolder = "#{" + k + "}";
                orderBy[0] = StringUtils.replace(orderBy[0], placeHolder, v);
            });
            queryMap.setOrderBy(orderBy[0]);
        }

        String strStart = request.getParameter("start");
        if (strStart != null && !strStart.isEmpty()) {
            queryMap.setStart(Integer.parseInt(strStart));
        }

        String strLimit = request.getParameter("limit");
        if (strLimit != null && !strLimit.isEmpty()) {
            queryMap.setLimit(Integer.parseInt(strLimit));
        }

        return queryMap;
    }

    protected DbQueryParameter getFilterStringFromRequest(Class clazz) {
        DbQueryParameter queryMap = new DbQueryParameter();
        try {
            FilterExpression[] expressions = this.getFilterExpressionFromReRequest();
            FilterExpression.fillWhere(clazz, queryMap, expressions);
            return queryMap;
        } catch (Exception e) {
            Logger.error(e);
        }
        return queryMap;
    }

    protected FilterExpression[] getFilterExpressionFromReRequest() throws UnsupportedEncodingException {
        HttpServletRequest request = GlobalConstants.getRequest();
        if (request.getParameter("filterExpr") == null) {
            return null;
        }
        String rawSearch = request.getParameter("filterExpr")
                .replaceAll("\r|\n", "")
                .replaceAll(" ", "+");
        byte[] buffer = Base64.getDecoder().decode(rawSearch);
        String strUtf8 = new String(buffer, "UTF-8");
        if (StringUtils.isEmpty(strUtf8)) {
            return null;
        }
        //   String strIso = new String(buffer, "ISO-8859-1");
        // String strUtf8 = java.net.URLDecoder.decode(strIso, "UTF-8");

        Gson gson = GlobalConstants.getGson();
        return gson.fromJson(strUtf8, FilterExpression[].class);

    }


}
