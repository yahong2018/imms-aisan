package com.zhxh.data.domain;

import java.time.LocalDateTime;
import java.util.Map;

import com.zhxh.admin.domain.SystemUser;
import com.zhxh.data.DataOperateType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TraceInfo extends Entity {
	private Long businessId;
	private String className;
	private Long operatorId;
	private LocalDateTime operateTime;
	private DataOperateType operateType;
	private String target;
	private SystemUser operator;
}
