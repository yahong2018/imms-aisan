package com.zhxh.imms.admin.domain;

import com.zhxh.imms.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SystemAccount extends Entity {
	private StartupStatus accountStatus;
	private RecordCreationType recordCreationType = RecordCreationType.CUSTOM;
}
