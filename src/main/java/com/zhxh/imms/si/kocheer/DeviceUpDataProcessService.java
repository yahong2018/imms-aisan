package com.zhxh.imms.si.kocheer;

import com.google.gson.Gson;
import com.zhxh.imms.data.BusinessException;
import com.zhxh.imms.mes.mfc.domain.RfidCard;
import com.zhxh.imms.mes.mfc.logic.RfidCardLogic;
import com.zhxh.imms.mes.org.domain.Operator;
import com.zhxh.imms.mes.org.domain.Workshop;
import com.zhxh.imms.mes.org.domain.Workstation;
import com.zhxh.imms.mes.org.logic.OperatorLogic;
import com.zhxh.imms.mes.org.logic.WorkshopLogic;
import com.zhxh.imms.mes.org.logic.WorkstationLogic;
import com.zhxh.imms.si.kocheer.command.Command_28;
import com.zhxh.imms.si.kocheer.domain.DeviceCommand;
import com.zhxh.imms.si.kocheer.domain.DeviceRawData;
import com.zhxh.imms.si.kocheer.domain.WorkstationSession;
import com.zhxh.imms.si.kocheer.logic.DeviceRawDataLogic;
import com.zhxh.imms.si.kocheer.logic.WorkstationSessionLogic;
import com.zhxh.imms.si.kocheer.logic.WorkstationSessionStepLogic;
import com.zhxh.imms.si.kocheer.wdto.DeviceUpData;
import com.zhxh.imms.utils.Logger;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.transaction.Transaction;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionTemplate;
import sun.rmi.runtime.Log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

@Component
public class DeviceUpDataProcessService {
    private final DeviceRawDataLogic deviceRawDataLogic;
    private final WorkstationLogic workstationLogic;
    private final WorkshopLogic workshopLogic;
    private final RfidCardLogic rfidCardLogic;
    private final WorkstationSessionLogic sessionLogic;
    private final OperatorLogic operatorLogic;
    private final DataSourceTransactionManager dataSourceTransactionManager;

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DeviceUpDataProcessService(DeviceRawDataLogic deviceRawDataLogic, WorkstationLogic workstationLogic, WorkshopLogic workshopLogic, RfidCardLogic rfidCardLogic, WorkstationSessionLogic sessionLogic, WorkstationSessionStepLogic sessionStepLogic, OperatorLogic operatorLogic, @Qualifier("immsTransactionManager") DataSourceTransactionManager dataSourceTransactionManager) {
        this.deviceRawDataLogic = deviceRawDataLogic;
        this.workstationLogic = workstationLogic;
        this.workshopLogic = workshopLogic;
        this.rfidCardLogic = rfidCardLogic;
        this.sessionLogic = sessionLogic;
        this.operatorLogic = operatorLogic;
        this.dataSourceTransactionManager = dataSourceTransactionManager;
    }

    public String processUpData(DeviceUpData upData) {
        //this.saveRawData(upData);

        String content = upData.getDataContent();
        String[] subContents = StringUtils.split(content, "|");  //多条消息之间用"|"隔开
        StringBuilder sb = new StringBuilder();
        StringBuilder sbParam1 = new StringBuilder();

        int isOffLine, dataType;
        LocalDateTime gatherTime, makeTime;
        String param1, param2, param3;
        List<DeviceCommand> returnCommandList = new ArrayList<>();
        for (String sub : subContents) {
            String[] params = sub.split(","); // 一条消息之间的各个部分用逗号","隔开，但有可能用为null

            String strIsOffLine = params[0];
            isOffLine = Integer.parseInt(strIsOffLine);

            String strDataType = params[1];
            dataType = Integer.parseInt(strDataType);

            String strDataGatherTime = params[2];
            gatherTime = LocalDateTime.parse(strDataGatherTime, formatter);

            String strDataMakeTime = params[3];
            makeTime = LocalDateTime.parse(strDataMakeTime, formatter);

            param1 = "";
            param2 = "";
            param3 = "";
            if (params.length > 4) {
                param1 = params[4];
            }
            if (params.length > 5) {
                param2 = params[5];
            }
            if (params.length > 6) {
                param3 = params[6];
            }
            sbParam1.delete(0, sbParam1.length());
            if (dataType == 3 || dataType == 6) { //多键数据和条码数据，传回的是ASCII码，需要转换
                for (int i = 0; i < param1.length(); i += 2) {
                    String str = param1.substring(i, i + 2);
                    char value = (char) Integer.parseInt(str, 16);
                    sbParam1.append(value);
                }
            } else {
                sbParam1.append(param1);
            }

            // DeviceCommand returnCommand = createTestCommand(isOffLine, upData.getGID(), upData.getDID(), dataType, gatherTime, makeTime, sbParam1.toString(), param2, param3);
            DeviceCommand returnCommand = this.processContent(isOffLine, upData.getGID(), upData.getDID(), dataType, gatherTime, makeTime, sbParam1.toString(), param2, param3);
            returnCommandList.add(returnCommand);
        }

        Gson gson = GsonCreator.getUpperCamelGson();
        String strCommand = gson.toJson(returnCommandList);
        sb.append(strCommand);
        return sb.toString();
    }

    private DeviceCommand createTestCommand(int isOffLine, int gid, int did, int dataType, LocalDateTime gatherTime, LocalDateTime makeTime, String param1, String param2, String param3) {
        Command_28 cmd = Command_28.test(gid, did, param1);
        DeviceCommand deviceCommand = new DeviceCommand(gid, did, cmd);
        return deviceCommand;
    }

    private void saveRawData(DeviceUpData upData) {
        DeviceRawData rawData = new DeviceRawData();
        rawData.setStationId(upData.getLoginStationID());
        rawData.setGid(upData.getGID());
        rawData.setDid(upData.getDID());
        rawData.setNewData(upData.getIsNewData() == 1);
        rawData.setDataContent(upData.getDataContent());
        rawData.setCreateDate(LocalDateTime.now());
        this.deviceRawDataLogic.create(rawData);
    }


    public Command_28 doProcess(WorkstationSession session) {
        TransactionTemplate transactionDefinition = new TransactionTemplate(this.dataSourceTransactionManager);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = dataSourceTransactionManager.getTransaction(transactionDefinition);
        try {
            Command_28 result = sessionLogic.processSession(session);
            dataSourceTransactionManager.commit(transactionStatus);//提交
            return result;
        } catch (RuntimeException e) {
            dataSourceTransactionManager.rollback(transactionStatus);

            throw e;
        }
    }

    public DeviceCommand processContent(int isOffLine, int gid, int did, int dataType, LocalDateTime gatherTime, LocalDateTime makeTime, String param1, String param2, String param3) {
        if (isOffLine == 1) {
            return null;
        }
        Workstation workstation = null;
        WorkstationSession session = null;
        Command_28 cmd = null;
        boolean hasError = false;
        try {
            try {
                workstation = this.verifyWorkstation(gid, did);
                session = this.verifySession(workstation, dataType, param1);
                cmd = this.doProcess(session);
                return new DeviceCommand(gid, did, cmd);
            } catch (RuntimeException e) {
                hasError = true;
                if (e instanceof BusinessException) {
                    int template = 4;
                    if (workstation != null) {
                        template = workstation.getDidTemplate();
                    }
                    cmd = Command_28.error(template, e.getMessage());
                } else {
                    String errorId = UUID.randomUUID().toString().replace("-", "");
                    Logger.error("系统出现异常，ID为:" + errorId);
                    cmd = Command_28.error(4, "系统出现异常，ID为:\n" + errorId + "\n请联系管理员");
                }
                Logger.error(e);
                return new DeviceCommand(gid, did, cmd);
            }
        } finally {
            if (session != null) {
                if (session.isCompleted() || !hasError) {
                    sessionLogic.createSessionStep(session, cmd);
                    sessionLogic.saveSession(session);
                }
                sessionLogic.clearSessionCurrentData(session);
                if (session.isCompleted() || (hasError && session.isNewSession())) {
                    sessionList.remove(session.getWorkstationId());
                }
                if (cmd != null) {
                    WorkstationSession theNewSession = (WorkstationSession) cmd.getTag();
                    if (theNewSession != null) {
                        sessionList.put(workstation.getRecordId(), theNewSession);
                        sessionLogic.saveSession(theNewSession);
                    }
                }
            }
        }
    }

    private WorkstationSession verifySession(Workstation workstation, int dataType, String param1) {
        //判断输入数据的类型:1.工卡  2.数量卡   3.外发卡   4.键盘输入
        int reqType;
        Operator operator = null;
        RfidCard card = null;
        if (dataType == ReqDataConstants.KOCHEER_INPUT_TYPE_SINGLE_KEY) {
            reqType = ReqDataConstants.REQ_TYPE_KEY_SINGLE;
        } else if (dataType == ReqDataConstants.KOCHEER_INPUT_TYPE_MULTI_KEY) {
            reqType = ReqDataConstants.REQ_TYPE_KEY_MULTI;
        } else if (dataType == ReqDataConstants.KOCHEER_INPUT_TYPE_CARD) {
            card = rfidCardLogic.getByRfidNo(param1);
            if (card != null) {
                reqType = ReqDataConstants.REQ_TYPE_WIP_CARD;
            } else {
                operator = operatorLogic.getByEmployeeCard(param1);
                if (operator == null) {
                    throw new BusinessException("卡号:" + param1 + "没有注册，请先注册卡");
                } else {
                    reqType = ReqDataConstants.REQ_TYPE_EMPLOYEE_CARD;
                }
            }
        } else {
            throw new BusinessException("无法识别的数据类型:" + dataType);
        }

        //判断session
        WorkstationSession session = sessionList.get(workstation.getRecordId());
        if (session == null || session.isCompleted()) {
            if (session != null) {
                sessionList.remove(workstation.getRecordId());
            }
            session = new WorkstationSession();
            session.setCurrentStep(WorkstationSession.SESSION_STEP_INIT); //确定本Session为新的Session
            session.setGid(workstation.getGid());
            session.setDid(workstation.getDid());
            session.setWorkstation(workstation);
            session.setWorkstationId(workstation.getRecordId());
            session.setCreateTime(LocalDateTime.now());
            session.setNewSession(true);
        } else {
            session.setNewSession(false);
//            if (session.getCreateTime().plusSeconds(30).isAfter(LocalDateTime.now())) {
//                //sessionList.remove(workstation.getRecordId());
//                throw new RuntimeException("前次功能已过30秒有效期,请按取消键返回开机画面再操作。");
//            }
        }

        session.setCurrentReqTime(LocalDateTime.now());
        if (operator != null) {
            if (session.isNewSession()) {
                session.setOperator(operator);
            } else {
                session.setCurrentOperator(operator);
            }
        }
        if (card != null) {
            session.setSessionQtyCard(card);
            Workshop cardWorkshop = workshopLogic.get(card.getWorkshopId());
            card.setWorkshop(cardWorkshop);
        }

        session.setCurrentReqType(reqType);
        session.setCurrentReqData(param1);

        if (session.isNewSession()) {
            sessionList.put(workstation.getRecordId(), session);
        }
        return session;
    }

    private Workstation verifyWorkstation(int gid, int did) {
        Workstation workstation = workstationLogic.getByGidDid(gid, did);
        if (workstation == null) {
            throw new BusinessException("组号:" + gid + ",机号:" + did + "的工位机没注册，请联系管理员注册！");
        }

        Workshop workshop = workshopLogic.get(workstation.getWorkshopId());
        workstation.setWorkshop(workshop);

        return workstation;
    }

    private Hashtable<Long, WorkstationSession> sessionList = new Hashtable<>();
}
