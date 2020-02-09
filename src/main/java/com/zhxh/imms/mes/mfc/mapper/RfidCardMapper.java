package com.zhxh.imms.mes.mfc.mapper;

import com.zhxh.imms.data.CrudMapper;
import com.zhxh.imms.mes.mfc.domain.RfidCard;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface RfidCardMapper extends CrudMapper<RfidCard> {
    List<RfidCard> getOutsourceBindCard(Long bindId);

    Map<String,Integer> getIssuedTotalCountAndQty(Long productionId);
}
