package com.zhxh.imms.org.controller;

import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.imms.org.domain.Workshop;
import com.zhxh.imms.org.logic.Org;
import com.zhxh.imms.org.logic.WorkshopLogic;
import com.zhxh.web.CrudController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/org/workshop")
public class WorkshopController extends CrudController<Workshop> {
    @Autowired
    private WorkshopLogic workshopLogic;
    @Override
    protected CrudLogic<Workshop> getLogic() {
        return this.workshopLogic;
    }

    @Override
    protected DbQueryParameter buildDbQueryFromRequest(Class clazz) {
        DbQueryParameter query = super.buildDbQueryFromRequest(clazz);
        if (StringUtils.isEmpty(query.getWhere())) {
            query.setWhere("org_type='"+ Org.ORG_TYPE_WORKSHOP+"'");
        } else {
            query.setWhere("org_type='"+Org.ORG_TYPE_WORKSHOP+"' and " + query.getWhere());
        }
        return query;
    }
}
