package com.zhxh.imms.mes.material.mapper;

import com.zhxh.imms.data.mapper.CrudMapper;
import com.zhxh.imms.mes.material.domain.Bom;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BomMapper extends CrudMapper<Bom> {
    List<Bom> getMaterialParts(Long materialId);

    List<Bom> getMaterialBom(String materialCode);
}
