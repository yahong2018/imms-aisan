package com.zhxh.imms.si.kocheer.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zhxh.imms.data.domain.Entity;
import com.zhxh.imms.si.kocheer.command.Command_28;
import com.zhxh.imms.si.kocheer.command.Command_28_Item;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DeviceCommand extends Entity {
    private Integer CmdType = 0;
    private Integer GID;
    private Integer DID;
    private Integer CmdNumber = 28;
    private String CmdContent;
    private LocalDateTime CmdMakeTime = LocalDateTime.now();

    @Override
    public String toString() {
        return "DeviceCommand{" +
                "CmdType=" + CmdType +
                ", GID=" + GID +
                ", DID=" + DID +
                ", CmdNumber=" + CmdNumber +
                ", CmdContent='" + CmdContent + '\'' +
                ", CmdMakeTime=" + CmdMakeTime +
                '}';
    }

    public DeviceCommand() {
    }

    public DeviceCommand(int gid, int did, Command_28 command28) {
        this.GID = gid;
        this.DID = did;
        this.buildCommandContent(command28);
    }


    public void buildCommandContent(Command_28 command28) {
        StringBuilder sb = new StringBuilder();
        sb.append(command28.getTemplate()).append("|");
        sb.append(command28.isClear() ? "1" : "0").append("|");

        int lineCount = 0;
        StringBuilder lineBuilder = new StringBuilder();
        for (Command_28_Item item : command28.getItems()) {
            lineCount += item.toDeviceItemString(lineBuilder);
        }
        sb.append(lineCount);
        sb.append(lineBuilder.toString());

        this.CmdContent = sb.toString();
    }
}
