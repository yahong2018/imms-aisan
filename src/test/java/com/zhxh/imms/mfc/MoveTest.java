package com.zhxh.imms.mfc;

import com.zhxh.si.imms.kocheer.DeviceUpDataProcessService;
import com.zhxh.si.imms.kocheer.domain.DeviceCommand;
import com.zhxh.startup.ZhxhApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZhxhApplication.class)
public class MoveTest {
    @Autowired
    private DeviceUpDataProcessService dataProcessService;

    /*
    移库测试
     */
    @Test
    public void moveTest(){
        //GID:3 DID 8为粗加工
        int gid = 3, did = 8, dataType = 1;
        String param1="RF01";
        DeviceCommand cmd = dataProcessService.processContent(0,gid,did,dataType, LocalDateTime.now(),LocalDateTime.now(),param1,null,null);
        System.out.println(cmd.toString());
    }
}
