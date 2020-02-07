package com.zhxh.imms.admin.controller;

import com.zhxh.imms.admin.domain.ProgramPrivilege;
import com.zhxh.imms.admin.domain.SystemRole;
import com.zhxh.imms.admin.logic.SystemRoleLogic;
import com.zhxh.imms.admin.model.ProgramAuthorizeModel;
import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.web.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/imms/admin/systemRole")
public class SystemRoleController extends CrudController<SystemRole> {
    @Autowired
    private SystemRoleLogic systemRoleLogic;

    @PostMapping("updatePrivilege")
    public int updatePrivilege(long roleId, @RequestBody ProgramPrivilege[] privileges){
        return this.systemRoleLogic.updatePrivilege(roleId,Arrays.asList(privileges));
    }

    @RequestMapping("rolePrivileges")
    public List<ProgramPrivilege> rolePrivileges(long roleId){
        return this.systemRoleLogic.get(roleId).getPrivileges();
    }

    @RequestMapping("allMenuWithPrivilege")
    public List<ProgramAuthorizeModel> allMenuWithPrivilege(){
        return this.systemRoleLogic.buildMenuWithPrivilege();
    }

    @PostMapping("updatePrivileges")
    public int updatePrivileges(long roleId,@RequestBody ProgramPrivilege[]currentPrivileges ){
        List<ProgramPrivilege> privileges = new ArrayList<>(Arrays.asList(currentPrivileges));
        return this.systemRoleLogic.updatePrivilege(roleId,privileges);
    }

    @Override
    protected CrudLogic<SystemRole> getLogic() {
        return this.systemRoleLogic;
    }
}
