package com.zhxh.imms.mes.org.mapper;

import com.zhxh.imms.mes.mfc.mapper.CrudMapper;
import com.zhxh.imms.mes.org.domain.Workshop;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WorkshopMapper extends CrudMapper<Workshop> {
    List<Workshop> getOperatorWorkshops(Long operatorId);
}
