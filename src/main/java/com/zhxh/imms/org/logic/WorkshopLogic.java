package com.zhxh.imms.org.logic;

import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.imms.org.domain.Workshop;
import com.zhxh.imms.org.mapper.WorkshopMapper;
import com.zhxh.web.FilterExpression;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkshopLogic extends CrudLogic<Workshop> {
    public List<Workshop> getOperatorWorkshops(Long operatorId){
        return ((WorkshopMapper) this.getMapper()).getOperatorWorkshops(operatorId);
    }

    public Workshop getGW() {
        FilterExpression filterExpression = new FilterExpression("workshopCode", "=", Workshop.WORKSHOP_GW);
        DbQueryParameter query = new DbQueryParameter(Workshop.class, filterExpression);

        return this.get(query);
    }
}
