package com.zhxh.si.imms.kocheer;

import com.google.gson.*;
import com.zhxh.admin.domain.SystemUser;
import com.zhxh.admin.logic.SystemUserLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.si.imms.kocheer.domain.*;
import com.zhxh.si.imms.kocheer.logic.ConterInfoLogic;
import com.zhxh.si.imms.kocheer.logic.StationInfoLogic;
import com.zhxh.si.imms.kocheer.wdto.DataGetRequestData;
import com.zhxh.si.imms.kocheer.wdto.DeviceUpData;
import com.zhxh.si.imms.kocheer.wdto.StationLoginRequestData;
import com.zhxh.utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@WebService(serviceName = "KocheerService", targetNamespace = "http://kocheer.imms.si.zhxh.com/",
        endpointInterface = "com.zhxh.si.imms.kocheer.KocheerService"
)
@Component
public class KocheerServiceImpl implements KocheerService {
    @Autowired
    private StationInfoLogic stationInfoLogic;

    @Autowired
    private ConterInfoLogic conterInfoLogic;

    @Autowired
    private SystemUserLogic userLogic;

    @Autowired
    private DeviceUpDataProcessService upDataLogic;



    @Override
    public String GetServerTime(String json) {
        String nowString = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(LocalDateTime.now());
        return GsonCreator.getUpperCamelGson().toJson(nowString);
    }

    @Override
    public String StationLogin(String json) {
        StationLoginRequestData requestData = GsonCreator.getUpperCamelGson().fromJson(json, StationLoginRequestData.class);

//		String s = "{\"StationID\":1,\"StationCode\":\"C001\",\"StationName\":\"机房\",\"StationPosition\":\"机房\","+
//                "\"SoftWareName\":\"26+Middleware\",\"SoftWareVersion\":\"1\",\"StationIP\":\"192.168.16.1\",\"StationLoginState\":1,\"LoginUserID\":1,\"FirstLoginTime\":\"\",\"LastLoginTime\":\"\",\"LastLogOutTime\":null,\"LastStateUpdateTime\":\"\",\"IsUse\":1,\"LoginName\":\"admin\",\"UserName\":\"管理员\",\"UserType\":1,\"UserID\":1} ";

        StationInfo stationInfo = stationInfoLogic.autoLogin(requestData);
        Gson gson = GsonCreator.getUpperCamelGson();
        String result = gson.toJson(stationInfo);
        result = result.replace("RecordId", "StationID");
        return result;
    }

    @Override
    public String GetStationConterList(String json) {
        //String s = "[{\"ConterID\":2,\"StationID\":1,\"GID\":1,\"ConterName\":\"222\",\"StartDID\":1,\"EndDID\":2,\"IP\":\"192.136.2.2\",\"Port\":4000,\"Position\":\"\",\"IsUse\":1,\"Remark\":\"\"}]";
        //return s;
        Gson gson = GsonCreator.getUpperCamelGson();
        DataGetRequestData requestData = gson.fromJson(json, DataGetRequestData.class);
        List<ConterInfo> conters = this.conterInfoLogic.getStationConters(requestData.getLoginStationID());
        String result = gson.toJson(conters);
        return result;
    }

    @Override
    public String GetStationList(String json) {
        Gson gson = GsonCreator.getUpperCamelGson();
        DataGetRequestData requestData = gson.fromJson(json, DataGetRequestData.class);
//		String s = "[{\"StationID\":1,\"StationCode\":\"C001\",\"StationName\":\"机房01\",\"StationPosition\":\"机房01\",\"SoftWareName\":\"26+Middleware\",\"SoftWareVersion\":\"1\",\"StationIP\":\"192.168.16.1\",\"StationLoginState\":1,\"LoginUserID\":1,\"FirstLoginTime\":\"\\/Date(580000374000)\\/\",\"LastLoginTime\":\"\\/Date(1580003682000)\\/\",\"LastLogOutTime\":\"\\/Date(1480731576150)\\/\",\"LastStateUpdateTime\":\"\\/Date(1580003710683)\\/\",\"IsUse\":1,\"Remark\":\"\"}]";
//		return s;
        DbQueryParameter query = new DbQueryParameter();
        List<StationInfo> list = this.stationInfoLogic.getAll(query);
        list.get(0).setUserID(requestData.getLoginUserID());
        String str = gson.toJson(list).replace("recordId", "StationID").replace("/Date(", "\\/Date(").replace(")/", ")\\/");
        return str;
    }

    @Override
    public String GetConterList(String json) {
        Gson gson = GsonCreator.getUpperCamelGson();
        DataGetRequestData requestData = gson.fromJson(json, DataGetRequestData.class);
        List<ConterInfo> conterInfoList = this.conterInfoLogic.getAll(new DbQueryParameter());
        String result = gson.toJson(conterInfoList);
        return result;

        //String s="[{\"ConterID\":2,\"StationID\":1,\"GID\":1,\"ConterName\":\"压铸\",\"StartDID\":1,\"EndDID\":30,\"IP\":\"192.168.122.51\",\"Port\":4000,\"Position\":\"\",\"IsUse\":1,\"Remark\":\"\"}]";
        //return s;
    }

    @Override
    public String ConterAdd(String json) {
        String s = "{\"ConterID\":1,\"StationID\":1,\"GID\":1,\"ConterName\":\"控制器\",\"StartDID\":1,\"EndDID\":30,\"IP\":\"192.168.16.1\",\"Port\":4001,\"Position\":\"位置\",\"IsUse\":1,\"Remark\":\"备注\"}";
        return s;
    }

    @Override
    public void ConterEdit(String json) {

    }

    @Override
    public void ConterDel(String json) {
    }

    @Override
    public String GetSystemUserList(String json) {
        SystemUser systemUser = userLogic.getByCode("Kocheer");
        UserInfo userInfo = new UserInfo();
        userInfo.setUserID(systemUser.getRecordId());
        userInfo.setLoginName(systemUser.getUserCode());
        userInfo.setUserName(systemUser.getDisplayName());
        userInfo.setUserType(1);
        userInfo.setLoginPass(systemUser.getPwd());
        userInfo.setRemark("");

        //String s= "[{\"UserID\":1,\"LoginName\":\"admin\",\"LoginPass\":\"\",\"UserName\":\"管理员\",\"UserType\":1,\"Remark\":\"\"},{\"UserID\":2,\"LoginName\":\"test1\",\"LoginPass\":\"\",\"UserName\":\"TEST1\",\"UserType\":0,\"Remark\":\"\"}]";

        UserInfo[] result = new UserInfo[]{userInfo};
        Gson gson = GsonCreator.getUpperCamelGson();
        String resultStr = gson.toJson(result);
        return resultStr;
    }

    @Override
    public String SystemUserAdd(String json) {
        String s = "{\"UserID\":1,\"LoginName\":\"admin\",\"LoginPass\":\"\",\"UserName\":\"管理员\",\"UserType\":\"1\",\"Remark\":\"这个管理员账号\"}";
        return s;
    }

    @Override
    public void SystemUserEdit(String json) {

    }

    @Override
    public void SystemUserDel(String json) {

    }

    @Override
    public String GetStationCmdList(String json) {
        return "[]";
    }

    @Override
    public String UploadDeviceData(String json) {
        Logger.info("收到上传数据:" + json);
        Gson gson = GsonCreator.getUpperCamelGson();
        DeviceUpData upData = gson.fromJson(json, DeviceUpData.class);

        String result = upDataLogic.processUpData(upData);
        result = result.replace("/Date(", "\\/Date(").replace(")/", ")\\/");
        Logger.info("返回数据:" + result);
        return result;
//		String s="[{\"DeviceCmdID\":1,\"CmdType\":0,\"GID\":1,\"DID\":1,\"CmdNumber\":28,\"CmdContent\":\"4|1|1|1|第一行测试|1|FF0000\",\"CmdMakeTime\":\"\\/Date(1480833937210)\\/\"}]";
//        Logger.info("返回数据:" + s);
//		return s;
    }

    @Override
    public void UploadDeviceState(String json) {

    }

    @Override
    public void ReportCmdExecuteResult(String json) {

    }

}
