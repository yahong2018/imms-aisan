package com.zhxh.imms.mes.org.mapper;

import com.zhxh.imms.data.CrudMapper;

import com.zhxh.imms.mes.org.domain.Operator;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface OperatorMapper extends CrudMapper<Operator> {
    int removeOperatorWorkshop(@Param("operatorId") Long operatorId, @Param("workshopId") Long workshopId);

    int addOperatorWorkshop(@Param("operatorId") Long operatorId, @Param("workshopId") Long workshopId);

    int clearOperatorWorkshop(Long operatorId);
}
