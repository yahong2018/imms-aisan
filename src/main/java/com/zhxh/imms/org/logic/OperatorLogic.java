package com.zhxh.imms.org.logic;

import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.imms.org.domain.Operator;
import com.zhxh.imms.org.domain.Workshop;
import com.zhxh.imms.org.mapper.OperatorMapper;
import com.zhxh.web.FilterExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperatorLogic extends CrudLogic<Operator> {
    private OperatorMapper getOperatorMapper(){
        return ((OperatorMapper) this.getMapper());
    }

    public Operator getByEmployeeCard(String cardNo) {
        FilterExpression expr = new FilterExpression("employeeCardNo", "=", cardNo);
        DbQueryParameter query = new DbQueryParameter();
        FilterExpression.fillWhere(Operator.class, query, expr);

        return super.get(query);
    }

    public Operator getByEmployeeId(String empId) {
        DbQueryParameter query = new DbQueryParameter();
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
