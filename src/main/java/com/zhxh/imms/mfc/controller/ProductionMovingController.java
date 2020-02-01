package com.zhxh.imms.mfc.controller;

import com.zhxh.data.CrudLogic;
import com.zhxh.imms.mfc.domain.ProductionMoving;
import com.zhxh.imms.mfc.logic.ProductionMovingLogic;
import com.zhxh.web.CrudController;
import com.zhxh.web.OrgFilterController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mfc/productionOrderMoving")
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
    public int create(ProductionMoving item) throws RuntimeException {
        return productionMovingLogic.reportWipMove(item, ProductionMoving.DIRECTION_ADVANCE);
    }
}
