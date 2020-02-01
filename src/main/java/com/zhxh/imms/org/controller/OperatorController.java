package com.zhxh.imms.org.controller;

import com.zhxh.data.CrudLogic;
import com.zhxh.imms.org.domain.Operator;
import com.zhxh.imms.org.logic.OperatorLogic;
import com.zhxh.web.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/org/operator")
public class OperatorController extends CrudController<Operator> {
    @Autowired
    private OperatorLogic operatorLogic;
    @Override
    protected CrudLogic<Operator> getLogic() {
        return this.operatorLogic;
    }
}
