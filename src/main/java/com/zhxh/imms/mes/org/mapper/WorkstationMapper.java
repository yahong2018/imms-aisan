package com.zhxh.imms.mes.org.mapper;

import com.zhxh.imms.data.CrudMapper;
import com.zhxh.imms.mes.org.domain.Workstation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WorkstationMapper extends CrudMapper<Workstation> {
    List<String> getWorkshopWocgList(Long workshopId);
    List<String> getWorkshopLocList(Long workshopId);
}
