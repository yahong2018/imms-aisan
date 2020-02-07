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

/*
   工件退回
   1.员工刷工卡，系统提示菜单：
      请选择功能：
      1.工件退回
      2.给前工程发卡
      3.尾数报工

   2.员工选择1，系统提示
       请刷看板

   3.员工刷看板，系统提示
       请输入退回数量

   4.员工输入退回数量，系统提示
       接收人刷工卡确认

   5.接收人刷工卡，系统提示
       已退还XXX(工件)XX个

 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZhxhApplication.class)
public class BackWipToPrevTest {
    @Autowired
    private DeviceUpDataProcessService dataProcessService;

    int gid = 3;
    int did = 1;

    String empCardNo = "GK001";
    String empCardNo_1 = "GK002";
    String menu = "1";
    String rfId="RF01";
    String backQty="30";

    @Test
    public void back_0(){
        int dataType = 1;
        String reqData = empCardNo;
        DeviceCommand cmd = dataProcessService.processContent(0, gid, did, dataType, LocalDateTime.now(), LocalDateTime.now(), reqData, null, null);
        System.out.println();
        System.out.println("backWipToPre.back_0----------->"+cmd);  //提示菜单
        System.out.println();
    }

    @Test
    public void back_1(){
        back_0();

        int dataType = 3;
        String reqData = menu;
        DeviceCommand cmd = dataProcessService.processContent(0, gid, did, dataType, LocalDateTime.now(), LocalDateTime.now(), reqData, null, null);
        System.out.println();
        System.out.println("backWipToPre.back_1----------->"+cmd); //提示刷工卡
        System.out.println();
    }

    @Test
    public void back_2(){
        back_1();
        int dataType = 1;
        String reqData = rfId;
        DeviceCommand cmd = dataProcessService.processContent(0, gid, did, dataType, LocalDateTime.now(), LocalDateTime.now(), reqData, null, null);
        System.out.println();
        System.out.println("backWipToPre.back_2----------->"+cmd); //提示输入数量
        System.out.println();
    }

    @Test
    public void back_3(){
        back_2();
        int dataType = 3;
        String reqData = backQty;
        DeviceCommand cmd = dataProcessService.processContent(0, gid, did, dataType, LocalDateTime.now(), LocalDateTime.now(), reqData, null, null);
        System.out.println();
        System.out.println("backWipToPre.back_3----------->"+cmd); //提示刷工卡
        System.out.println();
    }

    @Test
    public void back_4(){
        back_3();
        int dataType = 1;
        String reqData = empCardNo_1;
        DeviceCommand cmd = dataProcessService.processContent(0, gid, did, dataType, LocalDateTime.now(), LocalDateTime.now(), reqData, null, null);
        System.out.println();
        System.out.println("backWipToPre.back_4----------->"+cmd);
        System.out.println();
    }
}
