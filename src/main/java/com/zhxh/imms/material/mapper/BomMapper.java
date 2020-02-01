package com.zhxh.imms.material.mapper;

import com.zhxh.data.mapper.CrudMapper;
import com.zhxh.imms.material.domain.Bom;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BomMapper extends CrudMapper<Bom> {
    List<Bom> getMaterialParts(Long materialId);

    List<Bom> getMaterialBom(String materialCode);
}
