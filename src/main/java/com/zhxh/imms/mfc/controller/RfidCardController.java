package com.zhxh.imms.mfc.controller;

import com.zhxh.data.CrudLogic;
import com.zhxh.imms.mfc.domain.RfidCard;
import com.zhxh.imms.mfc.logic.RfidCardLogic;
import com.zhxh.web.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mfc/rfidCard")
public class RfidCardController extends CrudController<RfidCard> {
    @Autowired
    private RfidCardLogic rfidCardLogic;
    @Override
    protected CrudLogic<RfidCard> getLogic() {
        return this.rfidCardLogic;
    }
}
