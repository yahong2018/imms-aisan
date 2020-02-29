package com.zhxh.imms.mes.org.logic;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.data.DbQuery;
import com.zhxh.imms.mes.org.domain.Operator;
import com.zhxh.imms.mes.org.mapper.OperatorMapper;
import com.zhxh.imms.web.FilterExpression;
import org.springframework.stereotype.Component;

@Component
public class OperatorLogic extends CrudLogic<Operator> {
    private OperatorMapper getOperatorMapper(){
        return ((OperatorMapper) this.getMapper());
    }

    public Operator getByEmployeeCard(String cardNo) {
        FilterExpression expr = new FilterExpression("employeeCardNo", "=", cardNo);
        DbQuery query = new DbQuery();
        FilterExpression.fillWhere(Operator.class, query, expr);

        return super.get(query);
    }

    public Operator getByEmployeeId(String empId) {
        DbQuery query = new DbQuery();
        FilterExpression expr = new FilterExpression("employeeId", "=", empId);
        FilterExpression.fillWhere(Operator.class, query, expr);
        return this.get(query);
    }

    public int updateOperatorWorkshop(Long operatorId, Long[] workshopIdList){
        this.getOperatorMapper().clearOperatorWorkshop(operatorId);
        for(Long workshopId:workshopIdList){
            this.getOperatorMapper().addOperatorWorkshop(operatorId,workshopId);
        }
        return workshopIdList.length;
    }
}
