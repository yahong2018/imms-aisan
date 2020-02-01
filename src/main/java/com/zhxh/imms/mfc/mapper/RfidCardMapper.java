package com.zhxh.imms.mfc.mapper;

import com.zhxh.data.mapper.CrudMapper;
import com.zhxh.imms.mfc.domain.RfidCard;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface RfidCardMapper extends CrudMapper<RfidCard> {
    List<RfidCard> getOutsourceBindCard(@Param("workstationId")Long workstationId, @Param("outsourceCardId") Long outsourceCardId);

    Map<String,Integer> getIssuedTotalCountAndQty(Long productionId);
}
