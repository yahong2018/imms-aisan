package com.zhxh.imms.material.logic;

import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.imms.material.domain.Material;
import com.zhxh.imms.material.mapper.MaterialMapper;
import com.zhxh.web.FilterExpression;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MaterialLogic extends CrudLogic<Material> {
    public Material getByCode(String materialCode){
        FilterExpression expr = new FilterExpression("materialCode","=",materialCode);
        DbQueryParameter query = new DbQueryParameter();
        FilterExpression.fillWhere(Material.class,query,expr);

        return this.get(query);
    }

    public List<Material> getMaterialsHasNoBom(){
        return ((MaterialMapper) this.getMapper()).getMaterialsHasNoBom();
    }
}
