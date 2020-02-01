package com.zhxh.imms.material.controller;

import com.zhxh.data.CrudLogic;
import com.zhxh.imms.material.domain.MaterialStock;
import com.zhxh.imms.material.logic.MaterialStockLogic;
import com.zhxh.web.CrudController;
import com.zhxh.web.OrgFilterController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/material/materialStock")
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
