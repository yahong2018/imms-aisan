package com.zhxh.imms.admin.mapper;

import org.apache.ibatis.annotations.Param;

import com.zhxh.imms.data.domain.Entity;
import com.zhxh.imms.data.mapper.CrudMapper;

public interface SystemAccountMapper<E extends Entity> extends CrudMapper<E> {
	int createRoleUser(@Param("roleId") Long roleId, @Param("userId") Long userId);

	int deleteRoleUser(@Param("roleId") Long roleId, @Param("userId") Long userId);
}
