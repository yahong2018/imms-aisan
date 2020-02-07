package com.zhxh.imms.mfc;


import com.zhxh.imms.si.kocheer.DeviceUpDataProcessService;
import com.zhxh.imms.si.kocheer.domain.DeviceCommand;
import com.zhxh.startup.ZhxhApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZhxhApplication.class)
public class WholeReportTest {
    @Autowired
    private DeviceUpDataProcessService dataProcessService;

    /*
    整箱报工测试
    */
    @Test
    public void wholeReport() {
        //GID:3 DID 1为压铸
        int gid = 3, did = 1, dataType = 1;
        String param1="RF01";

        DeviceCommand cmd = dataProcessService.processContent(0,gid,did,dataType, LocalDateTime.now(),LocalDateTime.now(),param1,null,null);

        System.out.println(cmd.toString());
    }
}
