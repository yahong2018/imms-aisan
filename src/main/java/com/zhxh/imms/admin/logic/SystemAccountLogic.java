package com.zhxh.imms.admin.logic;

import com.zhxh.imms.admin.domain.SystemRole;
import com.zhxh.imms.admin.domain.SystemUser;
import com.zhxh.imms.admin.mapper.SystemAccountMapper;
import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.data.domain.Entity;

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
