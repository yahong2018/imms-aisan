package com.zhxh.imms.mes.material.mapper;

import com.zhxh.imms.mes.mfc.mapper.CrudMapper;
import com.zhxh.imms.mes.material.domain.Bom;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BomMapper extends CrudMapper<Bom> {
    List<Bom> getBom(@Param("materialId") Long materialId,@Param("isWhole") boolean isWhole);

    List<Bom> getMaterialBom(String materialCode);
}
