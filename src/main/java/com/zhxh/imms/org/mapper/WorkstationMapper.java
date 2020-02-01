package com.zhxh.imms.org.mapper;

import com.zhxh.data.mapper.CrudMapper;
import com.zhxh.imms.org.domain.Workstation;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface WorkstationMapper extends CrudMapper<Workstation> {
    List<String> getWorkshopWocgList(Long workshopId);
    List<String> getWorkshopLocList(Long workshopId);
}
