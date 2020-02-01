package com.zhxh.imms.general;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

public class StringTest {
    @Test
    public void gb2312Test() throws UnsupportedEncodingException {
        String strUtf8 = "零零0一一1二二2三\n三3四三四4五\n四三5六四三6\n七四三二7八四三8九四三9十四三五六9七三";
        StringBuilder lineBuilder = new StringBuilder();

        int LINE_MAX_LENGTH = 24;
        int total_len = 0;
        int line_count = 1;
        if (strUtf8.length() > 0) {
            lineBuilder.append("|1|");
        }

        for (int i = 0; i < strUtf8.length() - 1; i++) {
            String s = strUtf8.substring(i, i + 1);
            if(s.equalsIgnoreCase("\n")){ //遇到\n强制分行
                line_count += 1;
                lineBuilder.append("|0");
                lineBuilder.append("|").append(line_count).append("|");

                total_len = 0;
                continue;
            }

            int len = s.getBytes("gb2312").length;
            if (total_len + len > LINE_MAX_LENGTH) {
                line_count += 1;
                lineBuilder.append("|0");
                lineBuilder.append("|").append(line_count).append("|");

                total_len = 0;
            }
            lineBuilder.append(s);
            total_len += len;
        }
        if (strUtf8.length() > 0) {
            lineBuilder.append("|0");
        }

        System.out.println("共计" + line_count + "行,内容为:" + lineBuilder.toString());
    }
}
