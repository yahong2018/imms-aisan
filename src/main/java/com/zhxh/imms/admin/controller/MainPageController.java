package com.zhxh.imms.admin.controller;

import com.google.gson.Gson;
import com.zhxh.imms.admin.domain.ProgramPrivilege;
import com.zhxh.imms.admin.domain.SystemUser;
import com.zhxh.imms.admin.logic.SystemUserLogic;
import com.zhxh.imms.admin.model.SystemMenu;
import com.zhxh.imms.utils.GlobalConstants;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/imms/admin/mainPage")
public class MainPageController {
    private final SystemUserLogic userLogic;

    public MainPageController(SystemUserLogic userLogic) {
        this.userLogic = userLogic;
    }

    @RequestMapping("currentUserMenu")
    public String currentUserMenu(){
        SystemUser currentUser = GlobalConstants.getCurrentUser();
        List<SystemMenu> menus =  this.userLogic.getUserMenu(currentUser.getRecordId());
        Gson gson = GlobalConstants.getGson("handler", "traceInfos", "fieldsMap","privileges");
        String menuText = gson.toJson(menus);

        return menuText;
    }

    @RequestMapping("currentLogin")
    public String currentLogin() {
        SystemUser currentUser = GlobalConstants.getCurrentUser();

        long userId = currentUser.getRecordId();
        List<ProgramPrivilege> privileges = userLogic.getUserPrivileges(userId);

        Gson gson = GlobalConstants.getGson("handler", "traceInfos", "fieldsMap","program");
        String privilegeText = gson.toJson(privileges);

        String loginText = "{\"company\":\"爱三(佛山)汽车配件有限公司\","
                + "\"userName\":\"" + currentUser.getDisplayName() + "\","
                + "\"userCode\":\"" + currentUser.getUserCode() + "\","
                + "\"privileges\":" + privilegeText
                + "}";

        return loginText;
    }
}
