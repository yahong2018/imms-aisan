package com.zhxh.imms.mes.mfc.logic;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.mes.mfc.domain.WorkstationBind;
import com.zhxh.imms.mes.mfc.mapper.WorkstationBindMapper;
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

    public WorkstationBind getOutsourceCardWorkstationBindInfo(Long outsourceCardId) {
        return this.getBindMapper().getOutsourceCardWorkstationBindInfo(outsourceCardId);
    }
}
