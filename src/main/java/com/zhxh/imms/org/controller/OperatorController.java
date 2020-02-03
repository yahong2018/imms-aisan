package com.zhxh.imms.org.controller;

import com.zhxh.data.CrudLogic;
import com.zhxh.imms.org.domain.Operator;
import com.zhxh.imms.org.logic.OperatorLogic;
import com.zhxh.web.CrudController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/org/operator")
public class OperatorController extends CrudController<Operator> {
    private final OperatorLogic operatorLogic;

    public OperatorController(OperatorLogic operatorLogic) {
        this.operatorLogic = operatorLogic;
    }

    @Override
    protected CrudLogic<Operator> getLogic() {
        return this.operatorLogic;
    }

    @PostMapping("updateOperatorWorkshop")
    public int updateOperatorWorkshop(@RequestBody Long[] workshopIdList, Long operatorId) {
        return this.operatorLogic.updateOperatorWorkshop(operatorId, workshopIdList);
    }
}
