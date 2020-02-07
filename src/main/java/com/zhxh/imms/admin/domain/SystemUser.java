package com.zhxh.imms.admin.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SystemUser extends SystemAccount implements UserDetails {
	private String userCode;
	private String displayName;
	private String pwd;
	private String email;
	private LocalDateTime lastLoginTime;
	private boolean online;

	private List<SystemRole> roles = new ArrayList<SystemRole>();
	private List<ProgramPrivilege> privileges=new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(this.getRoles().size());
		for (SystemRole role : this.getRoles()) {
			GrantedAuthority authority = new SimpleGrantedAuthority(role.getRoleCode());
			result.add(authority);
		}
		return result;
	}

	@Override
	public String getPassword() {
		return this.getPwd();
	}

	@Override
	public String getUsername() {
		return this.getUserCode();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.getAccountStatus() != StartupStatus.EXPIRED;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.getAccountStatus() != StartupStatus.STOPPED;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.getAccountStatus() == StartupStatus.NORMAL;
	}
}
