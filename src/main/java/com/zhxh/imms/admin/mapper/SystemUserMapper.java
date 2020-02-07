package com.zhxh.imms.admin.mapper;
        
import org.springframework.stereotype.Component;

import com.zhxh.imms.admin.domain.SystemUser;

@Component
public interface SystemUserMapper extends SystemAccountMapper<SystemUser> {
    SystemUser getByCode(String userCode);
}
