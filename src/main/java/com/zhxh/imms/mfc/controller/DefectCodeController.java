package com.zhxh.imms.mfc.controller;

import com.zhxh.data.CrudLogic;
import com.zhxh.imms.mfc.domain.DefectCode;
import com.zhxh.imms.mfc.logic.DefectCodeLogic;
import com.zhxh.web.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mfc/defectCode")
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
