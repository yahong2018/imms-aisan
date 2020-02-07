package com.zhxh.imms.mes.mfc.controller;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.mes.mfc.domain.DefectCode;
import com.zhxh.imms.mes.mfc.logic.DefectCodeLogic;
import com.zhxh.imms.web.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mes/mfc/defectCode")
public class DefectCodeController extends CrudController<DefectCode> {
    private final DefectCodeLogic defectCodeLogic;
    public DefectCodeController(DefectCodeLogic defectCodeLogic) {
        this.defectCodeLogic = defectCodeLogic;
    }

    @Override
    protected CrudLogic<DefectCode> getLogic() {
        return defectCodeLogic;
    }
}
