package com.zhxh.imms.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SystemRole extends SystemAccount {
	private String roleCode;
	private String roleName;
	@JsonIgnore
	private List<SystemUser> users = new ArrayList<SystemUser>();
	@JsonIgnore
	private List<ProgramPrivilege> privileges = new ArrayList<>();
}

