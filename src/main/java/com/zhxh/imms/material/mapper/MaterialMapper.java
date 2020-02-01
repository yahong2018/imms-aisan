package com.zhxh.imms.material.mapper;

import com.zhxh.data.mapper.CrudMapper;
import com.zhxh.imms.material.domain.Material;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MaterialMapper extends CrudMapper<Material> {
    List<Material> getMaterialsHasNoBom();
}
