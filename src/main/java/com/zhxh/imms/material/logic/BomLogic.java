package com.zhxh.imms.material.logic;

import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.imms.material.domain.Bom;
import com.zhxh.imms.material.mapper.BomMapper;
import com.zhxh.web.FilterExpression;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BomLogic extends CrudLogic<Bom> {
    private BomMapper getBomMapper() {
        return ((BomMapper) this.getMapper());
    }

    public List<Bom> getMaterialParts(Long materialId) {
        return this.getBomMapper().getMaterialParts(materialId);
    }

    public List<Bom> getMaterialBom(String materialCode) {
        return this.getBomMapper().getMaterialBom(materialCode);
    }
}
