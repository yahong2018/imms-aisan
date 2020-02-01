package com.zhxh.imms.org.logic;

import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.imms.org.domain.Operator;
import com.zhxh.web.FilterExpression;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OperatorLogic extends CrudLogic<Operator> {
    public Operator getByEmployeeCard(String cardNo) {
        FilterExpression expr = new FilterExpression("employeeCardNo","=",cardNo);
        DbQueryParameter query = new DbQueryParameter();
        FilterExpression.fillWhere(Operator.class,query,expr);

        List<Operator> list = this.getAll(query);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public Operator getByEmployeeId(String empId){
        DbQueryParameter query = new DbQueryParameter();
        FilterExpression expr = new FilterExpression("employeeId","=",empId);
        FilterExpression.fillWhere(Operator.class,query,expr);
        List<Operator> list = this.getAll(query);
        if(list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
