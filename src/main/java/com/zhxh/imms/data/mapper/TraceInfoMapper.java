package com.zhxh.imms.data.mapper;

import java.util.List;
import java.util.Map;

import com.zhxh.imms.data.CrudMapper;
import org.springframework.stereotype.Component;

import com.zhxh.imms.data.domain.TraceInfo;

@Component
public interface TraceInfoMapper extends CrudMapper<TraceInfo> {
	List<TraceInfo> getTraceInfos(Map map);
}
