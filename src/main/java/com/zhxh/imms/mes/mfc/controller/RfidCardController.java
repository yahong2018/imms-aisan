package com.zhxh.imms.mes.mfc.controller;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.mes.mfc.domain.RfidCard;
import com.zhxh.imms.mes.mfc.logic.RfidCardLogic;
import com.zhxh.imms.web.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mes/mfc/rfidCard")
public class RfidCardController extends CrudController<RfidCard> {
    @Autowired
    private RfidCardLogic rfidCardLogic;
    @Override
    protected CrudLogic<RfidCard> getLogic() {
        return this.rfidCardLogic;
    }
}
