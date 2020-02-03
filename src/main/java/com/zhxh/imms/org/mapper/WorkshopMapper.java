package com.zhxh.imms.org.mapper;

import com.zhxh.data.mapper.CrudMapper;
import com.zhxh.imms.org.domain.Workshop;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WorkshopMapper extends CrudMapper<Workshop> {
    List<Workshop> getOperatorWorkshops(Long operatorId);
}
