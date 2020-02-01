package com.zhxh.imms.org.logic;

import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.imms.org.domain.Workstation;
import com.zhxh.imms.org.mapper.WorkstationMapper;
import com.zhxh.web.FilterExpression;
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

        DbQueryParameter query = new DbQueryParameter(Workstation.class, expressions);

        return this.get(query);
    }
}
