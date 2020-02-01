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
1.员工:刷工卡
  系统:提示
      请选择功能：
      1.工件退回
      2.给前工程发卡
      3.尾数报工

2.员工:按键选择2,按确定
  系统:提示
       请刷看板

3.员工：刷看板
  系统: 提示
       请输入派发数量
4.员工:输入数量，按确定
  系统: 提示
        已派发xxx，继续发卡请刷其他看板
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ZhxhApplication.class)
public class IssueTest {
    @Autowired
    private DeviceUpDataProcessService dataProcessService;

    int gid = 3;
    int did = 1;

    boolean isRepeat = false;
    String empCardNo = "GK001";
    String menu = "2";
    String rfId;
    String issueQty;

    //@Test
    public void issue_1() {
        int dataType = 1;
        String reqData = empCardNo;
        DeviceCommand cmd = dataProcessService.processContent(0, gid, did, dataType, LocalDateTime.now(), LocalDateTime.now(), reqData, null, null);
        System.out.println();
        System.out.println("issue_1----------->"+cmd);
        System.out.println();
    }

    //@Test
    public void issue_2() {
        issue_1();

        int dataType = 3;
        String reqData = menu;
        DeviceCommand cmd = dataProcessService.processContent(0, gid, did, dataType, LocalDateTime.now(), LocalDateTime.now(), reqData, null, null);
        System.out.println();
        System.out.println("issue_2----------->"+cmd);
        System.out.println();
    }

    //@Test
    public void issue_3() {
        if (!isRepeat) {
            issue_2();
        }
        int dataType = 1;
        if (StringUtils.isEmpty(rfId)) {
            rfId = "RF01";
        }
        String reqData = rfId;
        DeviceCommand cmd = dataProcessService.processContent(0, gid, did, dataType, LocalDateTime.now(), LocalDateTime.now(), reqData, null, null);
        System.out.println();
        System.out.println("issue_3----------->"+cmd);
        System.out.println();
    }

    //@Test
    public void issue_4() {
        issue_3();
        int dataType = 3;
        if(StringUtils.isEmpty(issueQty)){
            issueQty="295";
        }
        String reqData = issueQty;
        DeviceCommand cmd = dataProcessService.processContent(0, gid, did, dataType, LocalDateTime.now(), LocalDateTime.now(), reqData, null, null);
        System.out.println();
        System.out.println("issue_4----------->"+cmd);
        System.out.println();
    }

    @Test
    public void issue_twice() {
        this.isRepeat = false;
        issue_4();

        this.isRepeat = true;
        rfId="RF02";
        issueQty="75";
        issue_4();
    }
}
