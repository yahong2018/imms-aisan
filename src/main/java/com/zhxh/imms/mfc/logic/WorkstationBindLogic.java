package com.zhxh.imms.mfc.logic;

import com.zhxh.data.CrudLogic;
import com.zhxh.imms.mfc.domain.WorkstationBind;
import com.zhxh.imms.mfc.mapper.WorkstationBindMapper;
import com.zhxh.imms.org.mapper.WorkstationMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WorkstationBindLogic extends CrudLogic<WorkstationBind> {
    protected WorkstationBindMapper getBindMapper() {
        return (WorkstationBindMapper) this.getMapper();
    }

    public Map<String, Object> getWorkstationBindInfo(Long workstationId, Long componentId) {
        return this.getBindMapper().getQtyCardWorkstationBindInfo(workstationId, componentId);
    }

    public WorkstationBind getOutsourceCardWorkstationBindInfo(Long workstationId, Long outsourceCardId) {
        return this.getBindMapper().getOutsourceCardWorkstationBindInfo(workstationId, outsourceCardId);
    }
}
