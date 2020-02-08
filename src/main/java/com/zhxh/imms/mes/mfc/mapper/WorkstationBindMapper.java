package com.zhxh.imms.mes.mfc.mapper;

import com.zhxh.imms.mes.mfc.domain.WorkstationBind;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public interface WorkstationBindMapper extends CrudMapper<WorkstationBind> {
    Map<String, Object> getQtyCardWorkstationBindInfo(@Param("workstationId") Long workstationId, @Param("componentId") Long componentId);
    WorkstationBind getOutsourceCardWorkstationBindInfo(@Param("workstationId") Long workstationId, @Param("outsourceCardId") Long outsourceCardId);
}
