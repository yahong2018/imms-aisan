package com.zhxh.imms.admin.logic;

import com.zhxh.imms.admin.domain.ProgramPrivilege;
import com.zhxh.imms.admin.domain.StartupStatus;
import com.zhxh.imms.admin.domain.SystemRole;
import com.zhxh.imms.admin.domain.SystemUser;
import com.zhxh.imms.admin.mapper.ProgramPrivilegeMapper;
import com.zhxh.imms.admin.mapper.SystemProgramMapper;
import com.zhxh.imms.admin.mapper.SystemUserMapper;
import com.zhxh.imms.admin.model.PasswordChangeItem;
import com.zhxh.imms.admin.model.SystemMenu;
import com.zhxh.imms.data.BusinessException;
import com.zhxh.imms.data.DbQueryParameter;
import com.zhxh.imms.utils.GlobalConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class SystemUserLogic extends SystemAccountLogic<SystemUser> implements UserDetailsService {
    private final ProgramPrivilegeMapper privilegeMapper;
    private final SystemProgramMapper programMapper;

    public SystemUserLogic(ProgramPrivilegeMapper privilegeMapper, SystemProgramMapper programMapper) {
        this.privilegeMapper = privilegeMapper;
        this.programMapper = programMapper;
    }

    public SystemUser getByCode(String userCode) {
        return ((SystemUserMapper) this.getMapper()).getByCode(userCode);
    }

    public int enable(long userId) {
        SystemUser user = this.get(userId);
        user.setAccountStatus(StartupStatus.NORMAL);
        return this.update(user);
    }

    public int disable(long userId) {
        SystemUser user = this.get(userId);
        user.setAccountStatus(StartupStatus.EXPIRED);
        return this.update(user);
    }

    @Override
    public int update(SystemUser item) {
        SystemUser dbItem = this.getMapper().get(item.getRecordId());
        item.setPwd(dbItem.getPwd());

        return super.update(item);
    }

    @Override
    public int create(SystemUser item){
        if (StringUtils.isEmpty(item.getPwd())) {
            item.setPwd(this.getDefaultPassword());
        }
        if (item.getAccountStatus() == null) {
            item.setAccountStatus(StartupStatus.NORMAL);
        }
        return super.create(item);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public int updateUserRoles(SystemUser user) {
        SystemUser oldUser = this.get(user.getRecordId());
        List<SystemRole> oldRoles = oldUser.getRoles();
        List<SystemRole> currentRoles = new ArrayList<SystemRole>();
        currentRoles.addAll(user.getRoles());

        // 添加不在原角色列表中的新角色
        for (int i = 0; i < currentRoles.size(); i++) {
            SystemRole newRole = user.getRoles().get(i);
            if (oldRoles.stream().anyMatch(x -> x.compareTo(newRole) == 0)) {
                currentRoles.remove(i);
                i--;
            }
        }
        currentRoles.forEach(x -> this.assignRole(user, x));

        // 移除不在当前角色列表中的原角色
        for (int i = 0; i < oldRoles.size(); i++) {
            SystemRole oldRole = oldRoles.get(i);
            if (user.getRoles().stream().anyMatch(x -> x.compareTo(oldRole) == 0)) {
                oldRoles.remove(i);
                i--;
            }
        }
        oldRoles.forEach(x -> this.revokeRole(user, x));

        // 返回结果
        return user.getRoles().size();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DbQueryParameter query = new DbQueryParameter();
        query.setWhere("u.user_code='" + username + "'");
        SystemUser user = this.get(query);
        UserDetails result = user;

        if (result == null) {
            throw new UsernameNotFoundException("用户或密码错误！");
        }
        return result;
    }

    public List<ProgramPrivilege> getUserPrivileges(long userId) {
        return this.privilegeMapper.getUserPrivilege(userId);
    }

    public List<SystemMenu> getUserMenu(long userId) {
        List<SystemMenu> systemMenus = this.programMapper.getUserMenu(userId);
        for (SystemMenu menu : systemMenus) {
            menu.setLeaf(menu.getChildren().size() == 0);
            menu.setExpanded(!menu.isLeaf());

            fillSystemMenuData(menu);
        }
        return systemMenus;
    }

    private void fillSystemMenuData(SystemMenu menu) {
        for (SystemMenu childMenu : menu.getChildren()) {
            childMenu.setLeaf(childMenu.getChildren().size() == 0);
            childMenu.setExpanded(!childMenu.isLeaf());

            fillSystemMenuData(childMenu);
        }
    }

    public int resetPassword(long userId) {
        SystemUser user = this.get(userId);
        user.setPwd(this.getDefaultPassword());
        return this.getMapper().update(user);
    }

    public int changeCurrentUserPassword(PasswordChangeItem changeItem){
        SystemUser currentUser = GlobalConstants.getCurrentUser();
        if(currentUser==null){
            return 0;
        }

        if(StringUtils.isEmpty(changeItem.getOld()) || StringUtils.isEmpty(changeItem.getPwd1()) || StringUtils.isEmpty(changeItem.getPwd2())){
            throw new BusinessException("[旧密码]、[新密码]、[确认新密码]都必须输入！");
        }

        if(!changeItem.getOld().equalsIgnoreCase(currentUser.getPwd())){
            return -2;
        }

        if(!changeItem.getPwd1().equalsIgnoreCase(changeItem.getPwd2())){
            return -1;
        }

        currentUser.setPwd(changeItem.getPwd1());
        this.update(currentUser);
        return 0;
    }

    @Override
    public boolean exists(SystemUser item) {
        boolean result = super.exists(item);
        if (result) {
            return true;
        }

        SystemUser oldItem = this.getByCode(item.getUserCode());
        return oldItem != null;
    }

    private String getDefaultPassword() {
        return "888888";
    }
}
