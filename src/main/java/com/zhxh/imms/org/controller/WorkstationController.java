package com.zhxh.imms.org.controller;

import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.imms.org.domain.Workstation;
import com.zhxh.imms.org.logic.Org;
import com.zhxh.imms.org.logic.WorkstationLogic;
import com.zhxh.imms.org.model.LocCode;
import com.zhxh.imms.org.model.Wocg;
import com.zhxh.web.ExtJsStoreQueryResult;
import com.zhxh.web.CrudController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/imms/org/workstation")
public class WorkstationController extends CrudController<Workstation> {
    @Autowired
    private WorkstationLogic workstationLogic;

    @Override
    protected CrudLogic<Workstation> getLogic() {
        return this.workstationLogic;
    }

    @RequestMapping("getWorkshopStations")
    public ExtJsStoreQueryResult getWorkshopStations(Long workshopId) {
        DbQueryParameter query = new DbQueryParameter();
        query.setWhere("parent_id=" + workshopId.toString());

        return this.getAllByQuery(query);
    }

    @Override
    protected DbQueryParameter buildDbQueryFromRequest(Class clazz) {
        DbQueryParameter query = super.buildDbQueryFromRequest(clazz);
        if (StringUtils.isEmpty(query.getWhere())) {
            query.setWhere("org_type='" + Org.ORG_TYPE_WORKSTATION + "'");
        } else {
            query.setWhere("org_type='" + Org.ORG_TYPE_WORKSTATION + "' and " + query.getWhere());
        }
        return query;
    }

    @RequestMapping("getWorkshopWocgList")
    public ExtJsStoreQueryResult getWorkshopWocgList(Long workshopId) {
        List<String> list = this.workstationLogic.getWorkshopWocgList(workshopId);
        List<Wocg> wocgList = new ArrayList<>();
        for(String wocg:list){
            Wocg model = new Wocg();
            model.setWocgCode(wocg);
            wocgList.add(model);
        }
        ExtJsStoreQueryResult result = new ExtJsStoreQueryResult();
        result.setRootProperty(wocgList);
        result.setTotal(list.size());
        return result;
    }

    @RequestMapping("getWorkshopLocList")
    public ExtJsStoreQueryResult getWorkshopLocList(Long workshopId) {
        List<String> list = this.workstationLogic.getWorkshopLocList(workshopId);
        List<LocCode> locCodeList = new ArrayList<>();
        for(String loc:list){
            LocCode model = new LocCode();
            model.setLocCode(loc);
            locCodeList.add(model);
        }
        ExtJsStoreQueryResult result = new ExtJsStoreQueryResult();
        result.setRootProperty(locCodeList);
        result.setTotal(locCodeList.size());
        return result;
    }
}
