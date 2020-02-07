package com.zhxh.imms.si.kocheer.domain;

import com.zhxh.imms.data.BusinessException;
import com.zhxh.imms.data.domain.Entity;
import com.zhxh.imms.mes.mfc.domain.ProductionMoving;
import com.zhxh.imms.mes.mfc.domain.RfidCard;
import com.zhxh.imms.mes.org.domain.Operator;
import com.zhxh.imms.mes.org.domain.Workstation;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class WorkstationSession extends Entity {
    private Long workstationId;
    private Integer sessionType = SESSION_TYPE_UNKNOWN;
    private Integer currentStep = SESSION_STEP_INIT;
    private Long operatorId;
    private Integer gid;
    private Integer did;
    private LocalDateTime createTime = LocalDateTime.now();
    private LocalDateTime lastProcessTime;

    //以下不需要保存数据
    private Workstation workstation;
    private Operator operator;
    private List<WorkstationSessionStep> steps = new ArrayList<>();

    //以下为每次交互都更新的数据
    private RfidCard sessionQtyCard;
    private Operator currentOperator;
    private Integer currentReqType;
    private String currentReqData;
    private boolean newSession;
    private LocalDateTime currentReqTime;

    public WorkstationSession startNewSession(int step, String message) {
        WorkstationSession theNewSession = new WorkstationSession();
        theNewSession.setCurrentStep(step); //确定本Session为新的Session
        theNewSession.setGid(this.getGid());
        theNewSession.setDid(this.getDid());
        theNewSession.setWorkstation(this.getWorkstation());
        theNewSession.setWorkstationId(this.getWorkstationId());
        theNewSession.setOperatorId(this.getOperatorId());
        theNewSession.setOperator(this.getOperator());
        theNewSession.setNewSession(true);
        theNewSession.setCreateTime(LocalDateTime.now());
        theNewSession.setLastProcessTime(LocalDateTime.now());
        theNewSession.setSessionType(this.getSessionType());
        theNewSession.setCurrentReqType(this.getCurrentReqType());

        WorkstationSessionStep sessionStep = new WorkstationSessionStep();
        sessionStep.setStep(step);
        sessionStep.setReqData(this.getCurrentReqData());
        sessionStep.setReqTime(this.getCurrentReqTime());
        sessionStep.setReqDataType(this.getCurrentReqType());
        sessionStep.setRespTime(LocalDateTime.now());
        sessionStep.setRespData(message);

        theNewSession.getSteps().add(sessionStep);
        return theNewSession;
    }

    public ProductionMoving buildProductionMoving() {
        RfidCard card = this.getSessionQtyCard();
        ProductionMoving moving = new ProductionMoving();
        moving.setProductionId(card.getProductionId());
        moving.setRfidCardId(card.getRecordId());
        moving.setRfidNo(card.getRfidNo());
        moving.setWorkshopId(this.getWorkstation().getWorkshopId());
        moving.setGid(this.getGid());
        moving.setDid(this.getDid());
        moving.setWorkstationId(this.getWorkstationId());
        moving.setTimeOfOrigin(LocalDateTime.now());
        if (this.getCurrentOperator() != null) {
            moving.setOperatorId(this.getCurrentOperator().getRecordId());
        }
        moving.setOperatorIdFrom(this.getOperatorId());
        moving.setWorkshopIdFrom(card.getWorkshopId());

        return moving;
    }

    public int getQtyFromReqData(String reqData) {
        if (StringUtils.isEmpty(reqData)) {
            return this.getSessionQtyCard().getIssueQty();
        } else {
            try {
                return Integer.parseInt(reqData);
            } catch (Exception e) {
                throw new BusinessException(reqData + "不是整数，请输入整数");
            }
        }

    }

    public boolean isCompleted() {
        return SESSION_STEP_FINISHED == this.currentStep;
    }

    public void setCompleted() {
        this.currentStep = SESSION_STEP_FINISHED;
    }

    public WorkstationSessionStep getStep(int stepIndex) {
        for (WorkstationSessionStep step : this.steps) {
            if (step.getStep().equals(stepIndex)) {
                return step;
            }
        }
        return null;
    }

    public WorkstationSessionStep getCurrentSessionStep() {
        for (WorkstationSessionStep step : this.steps) {
            if (step.getStep().equals(this.currentStep)) {
                return step;
            }
        }
        return null;
    }

    public final static int SESSION_STEP_FINISHED = 255;
    public final static int SESSION_STEP_INIT = 0;

    /*
     * SessionType 主要用于区分多个步骤，单步骤的session意义不大
     * */
    public final static int SESSION_TYPE_UNKNOWN = -1;   //未知
    public final static int SESSION_TYPE_BACK = 1;  //工件退还
    public final static int SESSION_TYPE_ISSUE = 2; //给前工程发卡
    public final static int SESSION_TYPE_PARTIAL_REPORT = 3; //尾数报工
    public final static int SESSION_TYPE_WIP = 4; //整数报工、外发、移库、绑卡
}
