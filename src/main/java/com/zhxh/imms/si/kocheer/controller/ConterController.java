package com.zhxh.imms.si.kocheer.controller;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.si.kocheer.domain.ConterInfo;
import com.zhxh.imms.si.kocheer.logic.ConterInfoLogic;
import com.zhxh.imms.web.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/si/kocheer/conter")
public class ConterController extends CrudController<ConterInfo> {
    private final ConterInfoLogic conterInfoLogic;

    public ConterController(ConterInfoLogic conterInfoLogic) {
        this.conterInfoLogic = conterInfoLogic;
    }

    @Override
    protected CrudLogic<ConterInfo> getLogic() {
        return this.conterInfoLogic;
    }

    @Override
    public int create(ConterInfo item) {
        if (item.getIsUse() == null) {
            item.setIsUse(0);
        }
        return super.create(item);
    }

    @Override
    public int update(ConterInfo item) {
        if (item.getIsUse() == null) {
            item.setIsUse(0);
        }
        return super.update(item);
    }
}
