package com.zhxh.imms.si.kocheer;

import org.springframework.transaction.annotation.Transactional;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name = "KocheerService", targetNamespace = "http://kocheer.imms.si.zhxh.com/")
@Transactional(transactionManager = "immsTransactionManager", rollbackFor = RuntimeException.class)
public interface KocheerService {
    @WebMethod
    String StationLogin(String json);

    @WebMethod
    String GetStationConterList(String json);

    @WebMethod
    String GetStationCmdList(String json);

    @WebMethod
    String GetStationList(String json);

    @WebMethod
    String GetConterList(String json);

    @WebMethod
    String ConterAdd(String json);

    @WebMethod
    void ConterEdit(String json);

    @WebMethod
    void ConterDel(String json);

    @WebMethod
    String GetSystemUserList(String json);

    @WebMethod
    String SystemUserAdd(String json);

    @WebMethod
    void SystemUserEdit(String json);

    @WebMethod
    void SystemUserDel(String json);

    @WebMethod
    String GetServerTime(String json);

    @WebMethod
    String UploadDeviceData(String json);

    @WebMethod
    void UploadDeviceState(String json);

    @WebMethod
    void ReportCmdExecuteResult(String json);
}
