package com.zhxh.imms.mes.org.logic;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.data.DbQuery;
import com.zhxh.imms.mes.org.domain.Workstation;
import com.zhxh.imms.mes.org.mapper.WorkstationMapper;
import com.zhxh.imms.web.FilterExpression;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WorkstationLogic extends CrudLogic<Workstation> {
    public List<String> getWorkshopWocgList(Long workshopId) {
        return ((WorkstationMapper) this.getMapper()).getWorkshopWocgList(workshopId);
    }

    public List<String> getWorkshopLocList(Long workshopId) {
        return ((WorkstationMapper) this.getMapper()).getWorkshopLocList(workshopId);
    }

    public Workstation getByGidDid(Integer gid, Integer did) {
        FilterExpression[] expressions = new FilterExpression[2];
        expressions[0] = new FilterExpression("gid", "=", gid);
        expressions[1] = new FilterExpression("did", "=", did, "and");

        DbQuery query = new DbQuery(Workstation.class, expressions);

        return this.get(query);
    }
}
