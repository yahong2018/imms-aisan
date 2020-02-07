package com.zhxh.imms.mes.material.controller;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.data.DbQueryParameter;
import com.zhxh.imms.mes.material.domain.Bom;
import com.zhxh.imms.mes.material.logic.BomLogic;
import com.zhxh.imms.mes.material.model.BomTree;
import com.zhxh.imms.mes.material.model.BomTreeBuilder;
import com.zhxh.imms.web.ExtJsStoreQueryResult;
import com.zhxh.imms.web.CrudController;
import com.zhxh.imms.web.ExtJsTreeResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/mes/material/bom")
public class BomController extends CrudController<Bom> {
    private final BomLogic bomLogic;
    private final BomTreeBuilder treeBuilder;
    public BomController(BomLogic bomLogic, BomTreeBuilder treeBuilder) {
        this.bomLogic = bomLogic;
        this.treeBuilder = treeBuilder;
    }

    @Override
    protected CrudLogic<Bom> getLogic() {
        return this.bomLogic;
    }

    @RequestMapping("getBomByMaterialCode")
    public ExtJsStoreQueryResult getBomByMaterialCode(String materialCode){
        DbQueryParameter query= new DbQueryParameter();
        query.setWhere("m.material_code=#{materialCode}");
        query.put("materialCode",materialCode);

        return super.getAllByQuery(query);
    }


    @RequestMapping("getBomTree")
    public ExtJsTreeResult getBomTree(String materialCode){
        BomTree bomTree =  treeBuilder.buildTree(materialCode, false);
        ExtJsTreeResult treeResult= new ExtJsTreeResult();
        treeResult.setChildren(bomTree);
        treeResult.setText(".");
        return treeResult;
    }
}
