package com.zhxh.data.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.zhxh.data.domain.TraceInfo;

@Component
public interface TraceInfoMapper extends CrudMapper<TraceInfo> {	
	List<TraceInfo> getTraceInfos(Map map);
}
