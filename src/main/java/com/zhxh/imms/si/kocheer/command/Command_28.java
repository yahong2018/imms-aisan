package com.zhxh.imms.si.kocheer.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Getter
@Setter
public class Command_28 {
    private Integer template = 8;
    private boolean clear = true;
    private Command_28_Item[] items;

    @JsonIgnore
    private Object tag;

    public Command_28() {
    }

    @Override
    public String toString() {
        return "Command_28{" +
                "items=" + Arrays.toString(items) +
                '}';
    }

    public Command_28(int template) {
        this.template = template;
    }

    public static Command_28 ok(int template, String message) {
        Command_28 cmd = new Command_28(template);

        Command_28_Item[] items = new Command_28_Item[2];
        Command_28_Item_BasicControl controlCmd = new Command_28_Item_BasicControl();
        items[0] = controlCmd;
        controlCmd.ok();

        Command_28_Item_Display messageCommand = new Command_28_Item_Display(message);
        items[1] = messageCommand;

        cmd.setItems(items);
        return cmd;
    }


    public static Command_28 menu(int template, boolean error) {
        Command_28 cmd = new Command_28(template);
        int itemCount = 4;
        Command_28_Item[] items = new Command_28_Item[itemCount];
        Command_28_Item_BasicControl controlCmd = new Command_28_Item_BasicControl();
        items[0] = controlCmd;
        controlCmd.multiKey();

        String menu = "请选择功能：\n1.工件退回\n2.给前工程发卡\n3.尾数报工";
        Command_28_Item_Display menuCommand = new Command_28_Item_Display(menu);
        items[1] = menuCommand;

        String hint = "按键1,2,3进行功能选择";
        Command_28_Item_Hint hintCommand = new Command_28_Item_Hint(hint);
        items[2] = hintCommand;

        Command_28_Item_BasicControl lampCommand = new Command_28_Item_BasicControl();
        items[3] = lampCommand;
        if (error) {
            lampCommand.error();
        }else{
            lampCommand.ok();
        }

        cmd.setItems(items);
        return cmd;
    }

    public static Command_28 desktop() {
        Command_28_Item[] items = new Command_28_Item[1];
        Command_28_Item_BasicControl controlCmd = new Command_28_Item_BasicControl();
        items[0] = controlCmd;

        Command_28 cmd = new Command_28(9);
        cmd.setItems(items);
        return cmd;
    }

    public static Command_28 error(int template, String errorMessage) {
        Command_28 cmd = new Command_28(template);

        Command_28_Item[] items = new Command_28_Item[2];
        Command_28_Item_BasicControl controlCmd = new Command_28_Item_BasicControl();
        items[0] = controlCmd;
        controlCmd.error();

        Command_28_Item_Display displayCmd = new Command_28_Item_Display();
        items[1] = displayCmd;
        if (template == 4) {
            displayCmd.setMaxLength(38);
        }
        displayCmd.setContent(errorMessage);

        cmd.setItems(items);
        return cmd;
    }

    public static Command_28 test(int gid, int did, String param) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String now = LocalDateTime.now().format(formatter);
        Command_28_Item[] items = new Command_28_Item[2];

        Command_28_Item_BasicControl controlCmd = new Command_28_Item_BasicControl();
        items[0] = controlCmd;
        controlCmd.multiKey();
        controlCmd.sound(3, 100);
        controlCmd.lightRed(3000);
        controlCmd.lightGreen(2000);
        controlCmd.lightYellow(1000);

        Command_28_Item_Display menuCommand = new Command_28_Item_Display("时间:" + now + ",GID:" + gid + ",DID:" + did + ",param1:" + param);
        items[1] = menuCommand;

        Command_28 cmd = new Command_28(4);

        cmd.setItems(items);
        return cmd;
    }
}
