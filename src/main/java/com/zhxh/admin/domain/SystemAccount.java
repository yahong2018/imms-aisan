package com.zhxh.admin.domain;

import com.zhxh.data.domain.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class SystemAccount extends Entity {
	private StartupStatus accountStatus;
	private RecordCreationType recordCreationType = RecordCreationType.CUSTOM;
}
