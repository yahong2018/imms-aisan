package com.zhxh.imms.mes.material.logic;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.data.DbQuery;
import com.zhxh.imms.mes.material.domain.Material;
import com.zhxh.imms.mes.material.mapper.MaterialMapper;
import com.zhxh.imms.web.FilterExpression;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaterialLogic extends CrudLogic<Material> {
    public Material getByCode(String materialCode){
        FilterExpression expr = new FilterExpression("materialCode","=",materialCode);
        DbQuery query = new DbQuery();
        FilterExpression.fillWhere(Material.class,query,expr);

        return this.get(query);
    }

    public List<Material> getMaterialsHasNoBom(){
        return ((MaterialMapper) this.getMapper()).getMaterialsHasNoBom();
    }
}
