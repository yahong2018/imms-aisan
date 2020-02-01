package com.zhxh.si.imms.kocheer.command;

import lombok.Getter;
import lombok.Setter;

import java.io.UnsupportedEncodingException;

@Getter
@Setter
public class Command_28_Item_Display implements Command_28_Item {
    private String content;

    public Command_28_Item_Display(){}

    public Command_28_Item_Display(String content){
        this.content = content;
    }

    @Override
    public String toString() {
        return "Command_28_Item_Display{" +
                "content='" + content + '\'' +
                '}';
    }

    public int toDeviceItemString(StringBuilder lineBuilder) {
       // int LINE_MAX_LENGTH = 38;
        int LINE_MAX_LENGTH = 24;
        int total_len = 0;
        int line_count = 1;
        if (content.length() > 0) {
            lineBuilder.append("|1|");
        }

        for (int i = 0; i < content.length(); i++) {
            String s = content.substring(i, i + 1);

            if(s.equalsIgnoreCase("\n")){ //遇到\n强制分行
                line_count += 1;
                lineBuilder.append("|0");
                lineBuilder.append("|").append(line_count).append("|");

                total_len = 0;
                continue;
            }

            int len = 0;
            try {
                len = s.getBytes("gb2312").length;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (total_len + len > LINE_MAX_LENGTH) { //大于24个英文字母，或者12个汉字，强制分行
                line_count += 1;
                lineBuilder.append("|0");
                lineBuilder.append("|").append(line_count).append("|");

                total_len = 0;
            }
            lineBuilder.append(s);
            total_len += len;
        }
        if (content.length() > 0) {
            lineBuilder.append("|0");
        }

        return line_count;
    }
}
