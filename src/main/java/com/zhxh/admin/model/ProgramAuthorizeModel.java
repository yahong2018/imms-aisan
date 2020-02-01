package com.zhxh.admin.model;

import com.zhxh.admin.domain.BaseProgram;
import com.zhxh.admin.domain.BuildInPrivilege;
import com.zhxh.admin.domain.ProgramPrivilege;
import com.zhxh.admin.domain.SystemProgram;
import com.zhxh.utils.BeanUtils;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramAuthorizeModel extends BaseProgram {
    private Object[] children;
    private boolean expanded;
    private boolean leaf;
    private boolean checked;
    private String dataType;
    private long programPrivilegeId;

    public void fromSystemProgram(SystemProgram program) {
        this.setRecordId(program.getRecordId());
        this.setProgramCode(program.getProgramCode());
        this.setProgramName(program.getProgramName());
        this.setProgramStatus(program.getProgramStatus());
        this.setGlyph(program.getGlyph());
        this.setParameters(program.getParameters());
        this.setShowOrder(program.getShowOrder());
        this.setUrl(program.getUrl());
        this.setParentId(program.getParentId());

        this.leaf = false;
        this.expanded = true;
        this.checked = false;
        this.dataType = "app.model.admin.SystemMenuTreeModel";
        this.children = new ProgramAuthorizeModel[program.getChildren().size()];
        ProgramPrivilege runPrivilege = program.getPrivileges().stream().filter(x -> x.getPrivilegeCode().equals(BuildInPrivilege.RUN.toString())).findFirst().get();
        this.setProgramPrivilegeId(runPrivilege.getRecordId());

        for (int i = 0; i < program.getChildren().size(); i++) {
            SystemProgram child = program.getChildren().get(i);
            ProgramAuthorizeModel childModel = new ProgramAuthorizeModel();
            childModel.fromSystemProgram(child);
            this.children[i] = childModel;
        }

        if (program.getChildren().size() == 0) {
            this.children = new PrivilegeAuthorizeModel[program.getPrivileges().size() - 1];
            for (int i = 0, j = 0; i < program.getPrivileges().size(); i++) {
                ProgramPrivilege privilege = program.getPrivileges().get(i);
                if (privilege.getPrivilegeCode().equals(BuildInPrivilege.RUN.toString())) {
                    continue;
                }

                PrivilegeAuthorizeModel model = new PrivilegeAuthorizeModel();
                this.children[j] = model;
                model.setRecordId(privilege.getRecordId());
                model.setChecked(false);
                model.setProgramId(privilege.getProgramId());
                model.setPrivilegeCode(privilege.getPrivilegeCode());
                model.setPrivilegeName(privilege.getPrivilegeName());
                model.setDataType("app.model.admin.ProgramPrivilegeModel");

                j++;
            }
        }
        this.setExpanded(this.children.length > 0);
        this.setLeaf(this.children.length == 0);
    }
}
