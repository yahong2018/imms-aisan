package com.zhxh.imms.mes.material.controller;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.mes.material.domain.Material;
import com.zhxh.imms.mes.material.logic.MaterialLogic;
import com.zhxh.imms.web.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mes/material/material")
public class MaterialController extends CrudController<Material> {
    private final MaterialLogic materialLogic;


    public MaterialController(MaterialLogic materialLogic) {
        this.materialLogic = materialLogic;

    }

    @Override
    protected CrudLogic<Material> getLogic() {
        return materialLogic;
    }



}
