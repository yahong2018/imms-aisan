package com.zhxh.imms.mes.material.logic;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.mes.material.domain.Bom;
import com.zhxh.imms.mes.material.mapper.BomMapper;
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