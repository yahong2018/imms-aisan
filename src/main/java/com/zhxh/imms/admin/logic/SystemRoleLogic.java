package com.zhxh.imms.admin.logic;

import java.util.ArrayList;
import java.util.List;

import com.zhxh.imms.admin.domain.ProgramPrivilege;
import com.zhxh.imms.admin.domain.SystemProgram;
import com.zhxh.imms.admin.mapper.ProgramPrivilegeMapper;
import com.zhxh.imms.admin.mapper.SystemProgramMapper;
import com.zhxh.imms.admin.model.ProgramAuthorizeModel;
import com.zhxh.imms.data.DbQueryParameter;
import org.springframework.stereotype.Component;

import com.zhxh.imms.admin.domain.SystemRole;
import com.zhxh.imms.admin.domain.SystemUser;

@Component
public class SystemRoleLogic extends SystemAccountLogic<SystemRole> {
    private final ProgramPrivilegeMapper privilegeMapper;
    private final SystemProgramMapper programMapper;

    public SystemRoleLogic(ProgramPrivilegeMapper privilegeMapper, SystemProgramMapper programMapper) {
        this.privilegeMapper = privilegeMapper;
        this.programMapper = programMapper;
    }

    public int updateRoleUsers(SystemRole role) {
        SystemRole oldRole = this.get(role.getRecordId());
        List<SystemUser> oldUsers = oldRole.getUsers();
        List<SystemUser> currentUsers = new ArrayList<SystemUser>();
        currentUsers.addAll(role.getUsers());

        //添加不在原角色列表中的新角色
        for (int i = 0; i < currentUsers.size(); i++) {
            SystemUser newUser = role.getUsers().get(i);
            if (oldUsers.stream().anyMatch(x -> x.compareTo(newUser) == 0)) {
                currentUsers.remove(i);
                i--;
            }
        }
        currentUsers.forEach(x -> this.assignRole(x, role));

        //移除不在当前角色列表中的原角色
        for (int i = 0; i < oldUsers.size(); i++) {
            SystemUser oldUser = oldUsers.get(i);
            if (role.getUsers().stream().anyMatch(x -> x.compareTo(oldUser) == 0)) {
                oldUsers.remove(i);
                i--;
            }
        }
        oldUsers.forEach(x -> this.revokeRole(x, role));

        //返回结果
        return role.getUsers().size();
    }

    public int updatePrivilege(long roleId, List<ProgramPrivilege> privileges) {
        List<ProgramPrivilege> oldPrivileges = privilegeMapper.getRolePrivileges(roleId);
        this.addNewPrivilege(roleId, privileges, oldPrivileges);
        this.removeOldPrivileges(roleId, privileges, oldPrivileges);

        return privileges.size();
    }

    private void removeOldPrivileges(long roleId, List<ProgramPrivilege> privileges, List<ProgramPrivilege> oldPrivileges) {
        List<ProgramPrivilege> privilegesToRevoke=new ArrayList<>();
        for (int i = 0; i < oldPrivileges.size(); i++) {
            ProgramPrivilege thePrivilege = oldPrivileges.get(i);
            if (!privileges.stream().anyMatch(x -> x.compareTo(thePrivilege) == 0)) {
                privilegesToRevoke.add(thePrivilege);
            }
        }
        privilegesToRevoke.forEach(x -> this.privilegeMapper.revokePrivilege(roleId, x.getRecordId()));
    }

    private void addNewPrivilege(long roleId, List<ProgramPrivilege> privileges, List<ProgramPrivilege> oldPrivileges) {
        List<ProgramPrivilege> addedPrivileges = new ArrayList<>();
        for (int i = 0; i < privileges.size(); i++) {
            ProgramPrivilege thePrivilege = privileges.get(i);
            if (oldPrivileges.stream().allMatch(x -> x.compareTo(thePrivilege) != 0)) {
                addedPrivileges.add(thePrivilege);
            }
        }
        addedPrivileges.forEach(x -> this.privilegeMapper.assignPrivilege(roleId, x.getRecordId()));
    }

    private List<SystemProgram> getTopPrograms() {
        DbQueryParameter query = new DbQueryParameter();
        query.setWhere("parent_id=0");

        return this.programMapper.getAll(query);
    }

    public List<ProgramAuthorizeModel> buildMenuWithPrivilege() {
        List<SystemProgram> topPrograms = this.getTopPrograms();
        List<ProgramAuthorizeModel> menus = new ArrayList<>();

        for(SystemProgram program :topPrograms){
            ProgramAuthorizeModel model = new ProgramAuthorizeModel();
            menus.add(model);
            model.fromSystemProgram(program);
        }
        return menus;
    }

}
