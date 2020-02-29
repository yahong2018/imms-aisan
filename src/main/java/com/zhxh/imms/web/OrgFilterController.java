package com.zhxh.imms.web;

import com.zhxh.imms.admin.domain.SystemUser;
import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.data.DbQuery;
import com.zhxh.imms.data.domain.Entity;
import com.zhxh.imms.mes.org.domain.Operator;
import com.zhxh.imms.mes.org.logic.OperatorLogic;
import com.zhxh.imms.utils.GlobalConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class OrgFilterController<T extends Entity> extends CrudController<T> {
    @Autowired
    private OperatorLogic operatorLogic;

    protected String getWorkshopIdProperty(){
        return "workshopId";
    }

    @Override
    protected DbQuery getFilterStringFromRequest(Class clazz) {
        SystemUser currentUser = GlobalConstants.getCurrentUser();
        if(currentUser.getRoles().stream().noneMatch(x->x.getRoleCode().equals("FILTER_BY_WORKSHOP"))){
            return super.getFilterStringFromRequest(clazz);
        }
        String empId = GlobalConstants.getCurrentUser().getUserCode();
        Operator operator = operatorLogic.getByEmployeeId(empId);
        String workshopIdColumn = CrudLogic.getFieldsMap(clazz).get(this.getWorkshopIdProperty());
        DbQuery query = super.getFilterStringFromRequest(clazz);
        String filter = workshopIdColumn + " in (select workshop_id from zhxh_operator_workshop where operator_id=" + operator.getRecordId() + ")";
        if (!StringUtils.isEmpty(query.getWhere())) {
            filter = query.getWhere() + " and " + filter;
        }
        query.setWhere(filter);

        return query;
    }
}
