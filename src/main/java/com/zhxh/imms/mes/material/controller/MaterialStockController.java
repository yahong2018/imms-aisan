package com.zhxh.imms.mes.material.controller;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.mes.material.domain.MaterialStock;
import com.zhxh.imms.mes.material.logic.MaterialStockLogic;
import com.zhxh.imms.web.OrgFilterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mes/material/materialStock")
public class MaterialStockController extends OrgFilterController<MaterialStock> {
    @Autowired
    private MaterialStockLogic materialStockLogic;

    @Override
    protected CrudLogic<MaterialStock> getLogic() {
        return this.materialStockLogic;
    }

    @Override
    protected String getWorkshopIdProperty() {
        return "storeId";
    }
}
