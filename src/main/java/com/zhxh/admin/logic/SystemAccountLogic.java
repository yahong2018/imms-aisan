package com.zhxh.admin.logic;

import com.zhxh.admin.domain.SystemRole;
import com.zhxh.admin.domain.SystemUser;
import com.zhxh.admin.mapper.SystemAccountMapper;
import com.zhxh.data.CrudLogic;
import com.zhxh.data.domain.Entity;

public abstract class SystemAccountLogic<E extends Entity> extends CrudLogic<E> {
	public int assignRole(SystemUser user, SystemRole role) {
		SystemAccountMapper<E> mapper = (SystemAccountMapper<E>) this.getMapper();
		return mapper.createRoleUser(role.getRecordId(), user.getRecordId());
	}

	public int revokeRole(SystemUser user, SystemRole role) {
		SystemAccountMapper<E> mapper = (SystemAccountMapper<E>) this.getMapper();
		return mapper.deleteRoleUser(role.getRecordId(), user.getRecordId());
	}
}
