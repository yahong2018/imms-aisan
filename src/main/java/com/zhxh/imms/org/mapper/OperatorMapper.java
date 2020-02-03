package com.zhxh.imms.org.mapper;

import com.zhxh.data.mapper.CrudMapper;
import com.zhxh.imms.org.domain.Operator;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface OperatorMapper extends CrudMapper<Operator> {
    int removeOperatorWorkshop(@Param("operatorId") Long operatorId, @Param("workshopId") Long workshopId);

    int addOperatorWorkshop(@Param("operatorId") Long operatorId, @Param("workshopId") Long workshopId);

    int clearOperatorWorkshop(Long operatorId);
}
