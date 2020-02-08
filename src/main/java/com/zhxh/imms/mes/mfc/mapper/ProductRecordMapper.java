package com.zhxh.imms.mes.mfc.mapper;

import com.zhxh.imms.mes.mfc.domain.ProductRecord;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public interface ProductRecordMapper extends CrudMapper<ProductRecord> {
    Map<String, Integer> getReportedSummary(@Param("workstationId") Long workstationId, @Param("timeOfOriginWork")LocalDate timeOfOriginWork, @Param("productionId")Long productionId, @Param("shiftId")Integer shiftId);
}
