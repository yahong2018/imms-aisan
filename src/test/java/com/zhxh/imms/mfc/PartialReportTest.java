package com.zhxh.imms.mfc;

import com.zhxh.si.imms.kocheer.DeviceUpDataProcessService;
import com.zhxh.si.imms.kocheer.domain.DeviceCommand;
import com.zhxh.startup.ZhxhApplication;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/*
   1.员工刷工卡，系统给出提示菜单

   2.员工选择3，系统提示刷工卡

   3.员工刷工卡，系统提示输入报工数量

   4.员工输入报工数量，系统完成报工

 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZhxhApplication.class)
public class PartialReportTest {
    @Autowired
    private DeviceUpDataProcessService dataProcessService;

    int gid = 3;
    int did = 1;

    String empCardNo = "GK001";
    String menu = "3";
    String rfId = "RF01";
    String reportQty = "5";

    public void p_0() {
        int dataType = 1;
        String reqData = empCardNo;
        DeviceCommand cmd = dataProcessService.processContent(0, gid, did, dataType, LocalDateTime.now(), LocalDateTime.now(), reqData, null, null);
        System.out.println();
        System.out.println("PartialReportTest.p_0----------->" + cmd);
        System.out.println();
    }

    public void p_1() {
        p_0();

        int dataType = 3;
        String reqData = menu;
        DeviceCommand cmd = dataProcessService.processContent(0, gid, did, dataType, LocalDateTime.now(), LocalDateTime.now(), reqData, null, null);
        System.out.println();
        System.out.println("PartialReportTest.p_1----------->" + cmd);
        System.out.println();
    }

    public void p_2() {
        p_1();
        int dataType = 1;
        String reqData = rfId;
        DeviceCommand cmd = dataProcessService.processContent(0, gid, did, dataType, LocalDateTime.now(), LocalDateTime.now(), reqData, null, null);
        System.out.println();
        System.out.println("PartialReportTest.p_2----------->" + cmd);
        System.out.println();
    }

    @Test
    public void p_3() {
        p_2();
        int dataType = 3;
        String reqData = reportQty;
        DeviceCommand cmd = dataProcessService.processContent(0, gid, did, dataType, LocalDateTime.now(), LocalDateTime.now(), reqData, null, null);
        System.out.println();
        System.out.println("PartialReportTest.p_3----------->" + cmd);
        System.out.println();
    }

}
