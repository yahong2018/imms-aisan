package com.zhxh.imms.material.controller;

import com.zhxh.data.CrudLogic;
import com.zhxh.imms.material.domain.Material;
import com.zhxh.imms.material.logic.MaterialLogic;
import com.zhxh.imms.material.model.BomTree;
import com.zhxh.imms.material.model.BomTreeBuilder;
import com.zhxh.web.CrudController;
import com.zhxh.web.ExtJsTreeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/material/material")
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
