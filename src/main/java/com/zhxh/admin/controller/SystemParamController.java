package com.zhxh.admin.controller;

import com.zhxh.admin.domain.SystemParam;
import com.zhxh.admin.logic.SystemParamLogic;
import com.zhxh.data.CrudLogic;
import com.zhxh.web.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/admin/systemParam")
public class SystemParamController extends CrudController<SystemParam> {
    @Autowired
    private SystemParamLogic systemParamLogic;

    @RequestMapping("sync_wdb")
    public String syncWdb(){
        return "";
    }

    @Override
    protected CrudLogic<SystemParam> getLogic() {
        return systemParamLogic;
    }
}
