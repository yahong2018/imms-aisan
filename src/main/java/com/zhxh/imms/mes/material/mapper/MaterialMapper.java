package com.zhxh.imms.mes.material.mapper;

import com.zhxh.imms.data.mapper.CrudMapper;
import com.zhxh.imms.mes.material.domain.Material;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MaterialMapper extends CrudMapper<Material> {
    List<Material> getMaterialsHasNoBom();
}
