package com.zhxh.imms.si.kocheer.logic;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.data.DbQuery;
import com.zhxh.imms.si.kocheer.domain.ConterInfo;
import com.zhxh.imms.web.FilterExpression;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ConterInfoLogic extends CrudLogic<ConterInfo> {
    public List<ConterInfo> getStationConters(Long stationId){
        FilterExpression expr = new FilterExpression("stationID","=",stationId);
        DbQuery queryParameter = new DbQuery();
        FilterExpression.fillWhere(ConterInfo.class,queryParameter,expr);

        return this.getAll(queryParameter);
    }
}
