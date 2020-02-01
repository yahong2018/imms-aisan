package com.zhxh.imms.wdb;

import com.zhxh.si.imms.wdb.WdbSyncService;
import com.zhxh.startup.ZhxhApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZhxhApplication.class)
public class WdbSyncTest {
    @Autowired
    private WdbSyncService wdbSyncService;

    @Test
    public void getDataTest() {
        wdbSyncService.syncData();
    }
}
