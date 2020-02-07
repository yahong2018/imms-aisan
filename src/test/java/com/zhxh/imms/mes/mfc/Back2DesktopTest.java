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
public class Back2DesktopTest {
    @Autowired
    private DeviceUpDataProcessService dataProcessService;

    @Test
    public void back2DesktopTest(){
        int gid = 3, did = 8, dataType = 2;
        String param1="12";
        DeviceCommand cmd = dataProcessService.processContent(0,gid,did,dataType, LocalDateTime.now(),LocalDateTime.now(),param1,null,null);
        System.out.println(cmd.toString());
    }
}
