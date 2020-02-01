package com.zhxh.si.imms.kocheer.logic;

import com.zhxh.data.CrudLogic;
import com.zhxh.si.imms.kocheer.ReqDataConstants;
import com.zhxh.si.imms.kocheer.command.Command_28;
import com.zhxh.si.imms.kocheer.domain.WorkstationSession;
import com.zhxh.si.imms.kocheer.domain.WorkstationSessionStep;
import com.zhxh.si.imms.kocheer.session.*;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class WorkstationSessionLogic extends CrudLogic<WorkstationSession> {
    private final WorkstationSessionStepLogic stepLogic;
    private final WipSessionService wipSessionService;
    private final BackWipToPreService backWipToPreService;
    private final IssueCardService issueCardService;
    private final PartialReportService partialReportService;

    public WorkstationSessionLogic(WorkstationSessionStepLogic stepLogic, WipSessionService wipSessionService, BackWipToPreService backWipToPreService, IssueCardService issueCardService, PartialReportService partialReportService) {
        this.stepLogic = stepLogic;
        this.wipSessionService = wipSessionService;
        this.backWipToPreService = backWipToPreService;
        this.issueCardService = issueCardService;
        this.partialReportService = partialReportService;
    }

    public Command_28 processSession(WorkstationSession session) {
        if (ReqDataConstants.REQ_TYPE_KEY_SINGLE == session.getCurrentReqType() && ReqDataConstants.KEY_CANCEL.equalsIgnoreCase(session.getCurrentReqData())) {
            //按下ESC键，结束Session,返回到主画面。
            return this.backToDesktop(session);
        }
        if (session.getSessionType() == WorkstationSession.SESSION_TYPE_UNKNOWN && ReqDataConstants.REQ_TYPE_EMPLOYEE_CARD == session.getCurrentReqType()) {
            //刷工卡开启新session,显示选择菜单
            return this.displaySelectionMenu(session);
        }

        SessionStepService sessionStepService;
        if (!session.isNewSession()) {
            //还未确定SessionType的情况下，只能按1.2.3
            String[] menuList = new String[]{"1", "2", "3"};
            if (WorkstationSession.SESSION_TYPE_UNKNOWN == session.getSessionType()) {
                if (Arrays.stream(menuList).anyMatch(x -> x.equalsIgnoreCase(session.getCurrentReqData()))) {
                    session.setSessionType(Integer.parseInt(session.getCurrentReqData()));
                } else {
                    return this.displaySelectionMenu(session);
                }
            }
            if (WorkstationSession.SESSION_TYPE_BACK == session.getSessionType()) {
                sessionStepService = backWipToPreService;
            } else if (WorkstationSession.SESSION_TYPE_ISSUE == session.getSessionType()) {
                sessionStepService = issueCardService;
            } else {
                sessionStepService = partialReportService;
            }
        } else if (ReqDataConstants.REQ_TYPE_WIP_CARD == session.getCurrentReqType()) {
            //刷看板开启新session，则进行报工、移库、绑卡、外发、回厂这5个动作
            session.setSessionType(WorkstationSession.SESSION_TYPE_WIP);
            sessionStepService = wipSessionService;
        } else {
            throw new RuntimeException("请刷工卡或者看板!");
        }
        return sessionStepService.processSession(session);
    }

    private Command_28 displaySelectionMenu(WorkstationSession session) {
        Command_28 result = Command_28.menu(session.getWorkstation().getDidTemplate());
        if (!session.isNewSession()) {
            session.setCurrentStep(WorkstationSession.SESSION_STEP_FINISHED);
            WorkstationSession newSession = session.startNewSession(WorkstationSession.SESSION_STEP_INIT, result.toString());
            result.setTag(newSession);
        }
        return result;
    }

    private Command_28 backToDesktop(WorkstationSession session) {
        session.setCurrentStep(WorkstationSession.SESSION_STEP_FINISHED);
        Command_28 result = Command_28.desktop();
        this.createSessionStep(session, result);

        return result;
    }

    public void createSessionStep(WorkstationSession session, Command_28 cmd) {
        WorkstationSessionStep sessionStep = new WorkstationSessionStep();
        if (!session.isNewSession()) {
            session.setCurrentStep(session.getCurrentStep() + 1);
        }

        sessionStep.setStep(session.getCurrentStep());
        sessionStep.setReqData(session.getCurrentReqData());
        sessionStep.setReqTime(session.getCurrentReqTime());
        sessionStep.setRespTime(LocalDateTime.now());
        sessionStep.setRespData(cmd.toString());

        session.setLastProcessTime(LocalDateTime.now());
        session.getSteps().add(sessionStep);
    }

    public void clearSessionCurrentData(WorkstationSession session) {
        session.setCurrentOperator(null);
        session.setCurrentReqData(null);
        session.setCurrentReqData(null);
        session.setCurrentReqType(ReqDataConstants.REQ_TYPE_UNKNOWN);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void saveSession(WorkstationSession session) {
        if (session.isNewSession()) {
            if (session.getCurrentOperator() != null) {
                session.setOperatorId(session.getCurrentOperator().getRecordId());
            }
            this.create(session);
        } else {
            session.setLastProcessTime(LocalDateTime.now());
            this.update(session);
        }
        WorkstationSessionStep step = session.getCurrentSessionStep();
        step.setWorkstationSessionId(session.getRecordId());
        step.setReqDataType(session.getCurrentReqType());
        stepLogic.create(step);
    }
}
