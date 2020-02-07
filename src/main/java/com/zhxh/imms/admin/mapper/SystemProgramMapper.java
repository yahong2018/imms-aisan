package com.zhxh.imms.admin.mapper;

import com.zhxh.imms.admin.domain.SystemProgram;
import com.zhxh.imms.admin.model.SystemMenu;
import com.zhxh.imms.data.mapper.CrudMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SystemProgramMapper extends CrudMapper<SystemProgram> {
    List<SystemProgram> getChildPrograms(String parentId);
    List<SystemMenu> getChildMenus(String parentId);
    List<SystemMenu> getUserMenu(long userId);
}
