package com.zhxh.admin.mapper;

import com.zhxh.admin.domain.ProgramPrivilege;
import com.zhxh.data.mapper.CrudMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProgramPrivilegeMapper extends CrudMapper<ProgramPrivilege> {
    List<ProgramPrivilege> getRolePrivileges(long roleId);

    List<ProgramPrivilege> getUserPrivilege(long userId);

    int assignPrivilege(@Param("roleId") long roleId, @Param("privilegeId") long privilegeId);
    int revokePrivilege(@Param("roleId") long roleId, @Param("privilegeId") long privilegeId);
}