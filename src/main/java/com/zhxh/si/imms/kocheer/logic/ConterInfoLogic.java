package com.zhxh.si.imms.kocheer.logic;

import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.si.imms.kocheer.domain.ConterInfo;
import com.zhxh.web.FilterExpression;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConterInfoLogic extends CrudLogic<ConterInfo> {
    public List<ConterInfo> getStationConters(Long stationId){
        FilterExpression expr = new FilterExpression("stationID","=",stationId);
        DbQueryParameter queryParameter = new DbQueryParameter();
        FilterExpression.fillWhere(ConterInfo.class,queryParameter,expr);

        return this.getAll(queryParameter);
    }
}
