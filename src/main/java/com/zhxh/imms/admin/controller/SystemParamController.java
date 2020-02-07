package com.zhxh.imms.admin.controller;

import com.zhxh.imms.admin.domain.SystemParam;
import com.zhxh.imms.admin.logic.SystemParamLogic;
import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.web.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/admin/systemParam")
public class SystemParamController extends CrudController<SystemParam> {
    @Autowired
    private SystemParamLogic systemParamLogic;

    @Override
    protected CrudLogic<SystemParam> getLogic() {
        return systemParamLogic;
    }
}
