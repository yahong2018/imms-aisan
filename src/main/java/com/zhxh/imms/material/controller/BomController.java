package com.zhxh.imms.material.controller;

import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.imms.material.domain.Bom;
import com.zhxh.imms.material.logic.BomLogic;
import com.zhxh.imms.material.model.BomTree;
import com.zhxh.imms.material.model.BomTreeBuilder;
import com.zhxh.web.ExtJsStoreQueryResult;
import com.zhxh.web.CrudController;
import com.zhxh.web.ExtJsTreeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/material/bom")
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
