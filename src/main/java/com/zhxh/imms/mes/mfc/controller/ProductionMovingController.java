package com.zhxh.imms.mes.mfc.controller;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.mes.mfc.domain.ProductionMoving;
import com.zhxh.imms.mes.mfc.logic.ProductionMovingLogic;
import com.zhxh.imms.web.OrgFilterController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mes/mfc/productionOrderMoving")
public class ProductionMovingController extends OrgFilterController<ProductionMoving> {
    private final ProductionMovingLogic productionMovingLogic;
    public ProductionMovingController(ProductionMovingLogic productionMovingLogic) {
        this.productionMovingLogic = productionMovingLogic;
    }

    @Override
    protected CrudLogic<ProductionMoving> getLogic() {
        return productionMovingLogic;
    }

    @Override
    public int create(ProductionMoving item){
        return productionMovingLogic.reportWipMove(item, ProductionMoving.DIRECTION_ADVANCE);
    }
}
