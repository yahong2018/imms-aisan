package com.zhxh.imms.admin.controller;

import com.zhxh.imms.admin.domain.StartupStatus;
import com.zhxh.imms.admin.domain.SystemRole;
import com.zhxh.imms.admin.domain.SystemUser;
import com.zhxh.imms.admin.logic.SystemUserLogic;
import com.zhxh.imms.admin.model.PasswordChangeItem;
import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.web.CrudController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("api/imms/admin/systemUser")
public class SystemUserController extends CrudController<SystemUser> {
    @Autowired
    private SystemUserLogic systemUserLogic;

    @RequestMapping("enable")
    public int enable(long userId) {
        return this.systemUserLogic.enable(userId);
    }

    @RequestMapping("disable")
    public int disable(long userId) {
        return this.systemUserLogic.disable(userId);
    }

    @RequestMapping("resetPassword")
    public int resetPassword(long userId){
        return this.systemUserLogic.resetPassword(userId);
    }

    @Override
    protected CrudLogic<SystemUser> getLogic() {
        return systemUserLogic;
    }

    @Override
    public int update(SystemUser item){
        if(item.getAccountStatus()==null){
            item.setAccountStatus(StartupStatus.NORMAL);
        }
        return super.update(item);
    }

    @PostMapping("updateUserRoles")
    public int updateRoleUsers(long userId, @RequestBody SystemRole[] roles){
        SystemUser user = this.systemUserLogic.get(userId);
        user.getRoles().clear();
        user.getRoles().addAll(Arrays.asList(roles));

        return this.systemUserLogic.updateUserRoles(user);
    }

    @PostMapping("changeCurrentUserPassword")
    public int changeCurrentUserPassword(@RequestBody PasswordChangeItem changeItem) {
        return this.systemUserLogic.changeCurrentUserPassword(changeItem);
    }
}
