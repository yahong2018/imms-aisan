package com.zhxh.admin.mapper;
        
import org.springframework.stereotype.Component;

import com.zhxh.admin.domain.SystemUser;
import com.zhxh.data.mapper.CrudMapper;

@Component
public interface SystemUserMapper extends SystemAccountMapper<SystemUser> {
    SystemUser getByCode(String userCode);
}
