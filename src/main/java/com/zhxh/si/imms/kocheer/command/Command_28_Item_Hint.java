package com.zhxh.si.imms.kocheer.command;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Command_28_Item_Hint implements Command_28_Item {
    private String hint;
    public Command_28_Item_Hint(){}
    public Command_28_Item_Hint(String hint){
        this.hint = hint;
    }

    @Override
    public int toDeviceItemString(StringBuilder lineBuilder) {
        lineBuilder.append("|200|").append(hint);
        return 1;
    }
}
