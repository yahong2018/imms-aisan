package com.zhxh.admin.mapper;

import org.apache.ibatis.annotations.Param;

import com.zhxh.data.domain.Entity;
import com.zhxh.data.mapper.CrudMapper;

public interface SystemAccountMapper<E extends Entity> extends CrudMapper<E> {
	int createRoleUser(@Param("roleId") Long roleId, @Param("userId") Long userId);

	int deleteRoleUser(@Param("roleId") Long roleId, @Param("userId") Long userId);
}
