package com.zhxh.imms.mes.mfc.mapper;

import com.zhxh.imms.data.mapper.CrudMapper;
import com.zhxh.imms.mes.mfc.domain.RfidCard;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface RfidCardMapper extends CrudMapper<RfidCard> {
    List<RfidCard> getOutsourceBindCard(@Param("workstationId")Long workstationId, @Param("outsourceCardId") Long outsourceCardId);

    Map<String,Integer> getIssuedTotalCountAndQty(Long productionId);
}