package com.zhxh.imms.data;

import com.zhxh.imms.admin.domain.*;
import com.zhxh.imms.data.domain.SyncData;
import com.zhxh.imms.data.domain.TraceInfo;
import com.zhxh.imms.mes.material.domain.Bom;
import com.zhxh.imms.mes.material.domain.Material;
import com.zhxh.imms.mes.material.domain.MaterialStock;
import com.zhxh.imms.mes.mfc.domain.*;
import com.zhxh.imms.mes.org.domain.Operator;
import com.zhxh.imms.mes.org.domain.Workshop;
import com.zhxh.imms.mes.org.domain.Workstation;
import com.zhxh.imms.si.kocheer.domain.*;

import java.util.HashMap;
import java.util.Map;

public class FieldsMapInitiator {
    private static Map<String, String> createEntityFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "record_id");

        return fieldsMap;
    }

    private static Map<String, String> getTraceInfoFieldsMap() {
        Map<String, String> fieldsMap = createEntityFieldsMap();
        fieldsMap.put("businessId", "business_id");
        fieldsMap.put("className", "class_name");
        fieldsMap.put("operatorId", "operator_id");
        fieldsMap.put("operateTime", "operate_time");
        fieldsMap.put("operateType", "operate_type");
        fieldsMap.put("target", "target");

        return fieldsMap;
    }

    private static Map<String, String> getProgramFieldsMap() {
        Map<String, String> fieldsMap = createEntityFieldsMap();
        fieldsMap.put("programCode", "program_code");
        fieldsMap.put("programName", "program_name");
        fieldsMap.put("url", "url");
        fieldsMap.put("glyph", "glyph");
        fieldsMap.put("showOrder", "show_order");
        fieldsMap.put("parameters", "parameters");
        fieldsMap.put("parentId", "parent_id");
        fieldsMap.put("programStatus", "program_status");
        return fieldsMap;
    }

    private static Map<String, String> getProgramPrivilegeFieldsMap() {
        Map<String, String> fieldsMap = createEntityFieldsMap();
        fieldsMap.put("programId", "program_id");
        fieldsMap.put("privilegeCode", "privilege_code");
        fieldsMap.put("privilegeName", "privilege_name");
        return fieldsMap;
    }

    private static Map<String, String> getAccountFieldsMap() {
        Map<String, String> fieldsMap = createEntityFieldsMap();
        fieldsMap.put("accountStatus", "account_status");
        fieldsMap.put("recordCreationType", "record_creation_type");
        return fieldsMap;
    }

    private static Map<String, String> getSystemRoleFieldsMap() {
        Map<String, String> fieldsMap = getAccountFieldsMap();
        fieldsMap.put("roleCode", "role_code");
        fieldsMap.put("roleName", "role_name");

        return fieldsMap;
    }

    private static Map<String, String> getSystemUserFieldsMap() {
        Map<String, String> fieldsMap = getAccountFieldsMap();
        fieldsMap.put("userCode", "user_code");
        fieldsMap.put("displayName", "display_name");
        fieldsMap.put("pwd", "pwd");
        fieldsMap.put("online", "is_online");
        fieldsMap.put("lastLoginTime", "last_login_time");
        fieldsMap.put("email", "email");

        return fieldsMap;
    }

    private static Map<String, String> getSystemParamFieldsMap() {
        Map<String, String> fieldsMap = createEntityFieldsMap();
        fieldsMap.put("paramType", "param_type");
        fieldsMap.put("paramCode", "param_code");
        fieldsMap.put("paramDescription", "param_description");
        fieldsMap.put("paramValue", "param_value");
        fieldsMap.put("creationType", "creation_type");

        return fieldsMap;
    }

    private static Map<String, String> getWorkshopFieldsMap() {
        Map<String, String> fieldsMap = createEntityFieldsMap();
        fieldsMap.put("workshopCode", "org_code");
        fieldsMap.put("workshopName", "org_name");
        fieldsMap.put("workshopType", "workshop_type");
        fieldsMap.put("description", "description");
        fieldsMap.put("opIndex", "op_index");
        fieldsMap.put("prevIndex", "prev_index");

        return fieldsMap;
    }

    private static Map<String, String> getWorkstationFieldsMap() {
        Map<String, String> fieldsMap = createEntityFieldsMap();
        fieldsMap.put("workstationCode", "org_code");
        fieldsMap.put("workstationName", "org_name");
        fieldsMap.put("workstationType", "workstation_type");
        fieldsMap.put("description", "description");
        fieldsMap.put("workshopId", "parent_id");
        fieldsMap.put("gid", "gid");

        fieldsMap.put("did", "did");
        fieldsMap.put("didTemplate", "did_template");
        fieldsMap.put("wocgCode", "wocg_code");
        fieldsMap.put("autoReportCount", "auto_report_count");
        fieldsMap.put("locCode", "loc_code");

        return fieldsMap;
    }

    private static Map<String, String> getOperatorFieldsMap() {
        Map<String, String> fieldsMap = createEntityFieldsMap();

        fieldsMap.put("employeeId", "opr.employee_id");
        fieldsMap.put("employeeName", "opr.employee_name");
        fieldsMap.put("employeeCardNo", "opr.employee_card_no");
        fieldsMap.put("orgId", "opr.org_id");
        fieldsMap.put("orgCode", "org.org_code");
        fieldsMap.put("orgName", "org.org_name");

        return fieldsMap;
    }

    private static Map<String, String> getMaterialFieldsMap() {
        Map<String, String> fieldsMap = createEntityFieldsMap();

        fieldsMap.put("materialCode", "material_code");
        fieldsMap.put("materialName", "material_name");
        fieldsMap.put("description", "description");
        fieldsMap.put("autoFinishedProgress", "auto_finished_progress");

        return fieldsMap;
    }

    private static Map<String, String> getBomFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "bom.record_id");
        fieldsMap.put("bomNo", "bom.bom_no");
        fieldsMap.put("bomType", "bom.bom_type");
        fieldsMap.put("bomStatus", "bom.bom_status");

        fieldsMap.put("materialId", "bom.material_id");
        fieldsMap.put("materialCode", "m.material_code");
        fieldsMap.put("materialName", "m.material_name");

        fieldsMap.put("componentId", "bom.component_id");
        fieldsMap.put("componentCode", "c.material_code");
        fieldsMap.put("componentName", "c.material_name");

        fieldsMap.put("materialQty", "bom.material_qty");
        fieldsMap.put("componentQty", "bom.component_qty");

        fieldsMap.put("effectDate", "bom.effect_date");

        return fieldsMap;
    }

    private static Map<String, String> getMaterialStockFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "st.record_id");

        fieldsMap.put("storeId", "st.store_id");
        fieldsMap.put("storeCode", "ws.org_code");
        fieldsMap.put("storeName", "ws.org_name");

        fieldsMap.put("materialId", "st.material_id");
        fieldsMap.put("materialCode", "m.material_code");
        fieldsMap.put("materialName", "m.material_name");

        fieldsMap.put("qtyStock", "st.qty_stock");
        fieldsMap.put("qtyMoveIn", "st.qty_move_in");
        fieldsMap.put("qtyBackIn", "st.qty_back_in");
        fieldsMap.put("qtyBackOut", "st.qty_back_out");
        fieldsMap.put("qtyConsumeGood", "st.qty_consume_good");
        fieldsMap.put("qtyConsumeDefect", "st.qty_consume_defect");
        fieldsMap.put("qtyGood", "st.qty_good");
        fieldsMap.put("qtyDefect", "st.qty_defect");
        fieldsMap.put("qtyMoveOut", "st.qty_move_out");

        return fieldsMap;
    }

    private static Map<String, String> getRfidCardFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "rfid.record_id");
        fieldsMap.put("kanbanNo", "rfid.kanbanNo");
        fieldsMap.put("rfidNo", "rfid.rfidNo");
        fieldsMap.put("cardType", "rfid.cardType");
        fieldsMap.put("cardStatus", "rfid.cardStatus");

        fieldsMap.put("productionId", "rfid.productionId");
        fieldsMap.put("productionCode", "m.material_code");
        fieldsMap.put("productionName", "m.material_name");

        fieldsMap.put("workshopId", "rfid.workshop_id");
        fieldsMap.put("workshopCode", "ws.org_code");
        fieldsMap.put("workshopName", "ws.org_name");

        fieldsMap.put("issueQty", "rfid.issue_qty");
        fieldsMap.put("stockQty", "rfid.stock_qty");
        fieldsMap.put("outsourceQty", "rfid.outsource_qty");
        fieldsMap.put("lastBusinessId", "rfid.last_business_id");
        fieldsMap.put("towerNo", "rfid.tower_no");

        return fieldsMap;
    }

    private static Map<String, String> getProductSummaryFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "ps.record_id");
        fieldsMap.put("productDate", "ps.product_date");
        fieldsMap.put("workshopId", "ps.workshop_id");
        fieldsMap.put("productionId", "ps.production_id");
        fieldsMap.put("qtyGood0", "ps.qty_good_0");
        fieldsMap.put("qtyDefect0", "ps.qty_defect_0");
        fieldsMap.put("qtyGood1", "ps.qty_good_1");
        fieldsMap.put("qtyDefect1", "ps.qty_defect_1");

        fieldsMap.put("workshopCode", "ws.org_code");
        fieldsMap.put("workshopName", "ws.org_name");
        fieldsMap.put("productionCode", "m.material_code");
        fieldsMap.put("productionName", "m.material_name");

        return fieldsMap;
    }

    private static Map<String, String> getProductRecordFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "pr.record_id");
        fieldsMap.put("workshopId", "pr.workshop_id");
        fieldsMap.put("workstationId", "pr.workstation_id");
        fieldsMap.put("productionId", "pr.production_id");
        fieldsMap.put("gid", "pr.gid");
        fieldsMap.put("did", "pr.did");
        fieldsMap.put("timeOfOrigin", "pr.time_of_origin");
        fieldsMap.put("timeOfOriginWork", "pr.time_of_origin_work");

        fieldsMap.put("shiftId", "pr.shift_id");
        fieldsMap.put("rfidCardId", "pr.rfid_card_id");
        fieldsMap.put("rfidCardNo", "pr.rfid_card_no");
        fieldsMap.put("reportType", "pr.report_type");

        fieldsMap.put("qty", "pr.qty");
        fieldsMap.put("cardQty", "pr.card_qty");
        fieldsMap.put("operatorId", "pr.operator_id");
        fieldsMap.put("remark", "pr.remark");

        fieldsMap.put("workshopCode", "ws.org_code");
        fieldsMap.put("workshopName", "ws.org_name");

        fieldsMap.put("workstationCode", "wk.org_code");
        fieldsMap.put("workstationName", "wk.org_name");
        fieldsMap.put("wocgCode", "wk.wocg_code");

        fieldsMap.put("productionCode", "m.material_code");
        fieldsMap.put("productionName", "m.material_name");

        fieldsMap.put("employeeId", "o.employee_id");
        fieldsMap.put("employeeName", "o.employee_name");

        return fieldsMap;
    }

    private static Map<String, String> getProductionMovingFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "pmv.record_id");

        fieldsMap.put("productionId", "pmv.production_id");
        fieldsMap.put("productionCode", "m.material_code");
        fieldsMap.put("productionName", "m.material_name");

        fieldsMap.put("rfidNo", "pmv.rfid_no");
        fieldsMap.put("rfidCardId", "pmv.rfidCard_id");

        fieldsMap.put("gid", "pmv.gid");
        fieldsMap.put("did", "pmv.did");

        fieldsMap.put("workshopId", "pmv.workshop_id");
        fieldsMap.put("workshopCode", "ws.org_code");
        fieldsMap.put("workshopName", "ws.org_name");

        fieldsMap.put("workshopIdFrom", "pmv.workshop_id_from");
        fieldsMap.put("workshopCodeFrom", "wsf.org_code");
        fieldsMap.put("workshopNameFrom", "wsf.org_name");

        fieldsMap.put("workstationId", "pmv.workstation_id");
        fieldsMap.put("workstationCode", "wk.org_code");
        fieldsMap.put("workstationName", "wk.org_name");
        fieldsMap.put("wocgCode", "wk.wocg_code");

        fieldsMap.put("operatorId", "pmv.operator_id");
        fieldsMap.put("employeeId", "o.employee_id");
        fieldsMap.put("employeeName", "o.employee_name");

        fieldsMap.put("operatorIdFrom", "pmv.operator_id_from");
        fieldsMap.put("employeeIdFrom", "of.employee_id_from");
        fieldsMap.put("employeeNameFrom", "of.employee_name_From");

        fieldsMap.put("qty", "pmv.qty");
        fieldsMap.put("timeOfOrigin", "pmv.time_of_origin");
        fieldsMap.put("timeOfOriginWork", "pmv.time_of_origin_work");
        fieldsMap.put("shiftId", "pmv.shift_id");

        return fieldsMap;
    }

    private static Map<String, String> getDefectCodeFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "dc.record_id");
        fieldsMap.put("defectCode", "dc.defect_code");
        fieldsMap.put("defectName", "dc.defect_name");

        return fieldsMap;
    }

    private static Map<String, String> getQualityCheckFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "qc.record_id");

        fieldsMap.put("productionId", "qc.production_id");
        fieldsMap.put("productionCode", "m.material_code");
        fieldsMap.put("productionName", "m.material_name");

        fieldsMap.put("workshopId", "qc.workshop_id");
        fieldsMap.put("workshopCode", "ws.org_code");
        fieldsMap.put("workshopName", "ws.org_name");
        fieldsMap.put("wocgCode", "qc.wocg_code");
        fieldsMap.put("locCode", "qc.loc_code");

        fieldsMap.put("defectId", "qc.defect_id");
        fieldsMap.put("defectCode", "dc.defect_code");
        fieldsMap.put("defectName", "dc.defect_name");

        fieldsMap.put("timeOfOriginWork", "qc.time_of_origin_work");
        fieldsMap.put("shiftId", "qc.shift_id");
        fieldsMap.put("qty", "dc.qty");

        return fieldsMap;
    }

    private static Map<String, String> getStationInfoFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "sta.record_id");

        fieldsMap.put("stationCode", "sta.station_code");
        fieldsMap.put("stationName", "sta.station_name");
        fieldsMap.put("stationPosition", "sta.station_position");
        fieldsMap.put("softwareName", "sta.software_name");
        fieldsMap.put("softwareVersion", "sta.software_version");
        fieldsMap.put("stationIP", "sta.station_ip");
        fieldsMap.put("stationLoginState", "sta.station_login_state");
        fieldsMap.put("loginUserID", "sta.login_user_id");
        fieldsMap.put("firstLoginTime", "sta.first_login_time");
        fieldsMap.put("lastLoginTime", "sta.last_login_time");
        fieldsMap.put("lastLogoutTime", "sta.last_logout_time");
        fieldsMap.put("lastStateUpdateTime", "sta.last_state_update_time");
        fieldsMap.put("isUse", "sta.is_use");
        fieldsMap.put("loginName", "u.user_code");
        fieldsMap.put("userName", "u.display_name");

        return fieldsMap;
    }

    private static Map<String, String> getConterInfoFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "c.record_id");

        fieldsMap.put("stationID", "c.station_id");
        fieldsMap.put("GID", "c.gid");
        fieldsMap.put("conterName", "c.conter_name");
        fieldsMap.put("startDID", "c.start_did");
        fieldsMap.put("endDID", "c.end_did");
        fieldsMap.put("IP", "c.ip");
        fieldsMap.put("port", "c.port");
        fieldsMap.put("position", "c.position");
        fieldsMap.put("isUse", "c.is_use");
        fieldsMap.put("wiressPower", "c.wiress_power");
        fieldsMap.put("remark", "c.remark");

        return fieldsMap;
    }

    private static Map<String, String> getWorkstationBindFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "wb.record_id");

        fieldsMap.put("outsourceCardId", "wb.outsource_card_id");
        fieldsMap.put("workstationId", "wb.workstation_id");
        fieldsMap.put("workshopId", "wb.workshop_id");
        fieldsMap.put("attachTime", "wb.attach_time");
        fieldsMap.put("outTime", "wb.out_time");
        fieldsMap.put("backTime", "wb.back_time");
        fieldsMap.put("bindStatus", "wb.bind_status");

        return fieldsMap;
    }

    private static Map<String, String> getCardBindFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "cb.record_id");

        fieldsMap.put("outsourceCardId", "cb.outsource_card_id");
        fieldsMap.put("qtyReportId", "cb.qty_report_id");
        fieldsMap.put("qtyCardId", "cb.qty_card_id");
        fieldsMap.put("attachTime", "cb.attach_time");
        fieldsMap.put("workstationBindId", "cb.workstation_bind_id");

        return fieldsMap;
    }

    private static Map<String, String> getDeviceRawDataFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "raw.record_id");

        fieldsMap.put("stationId", "raw.station_id");
        fieldsMap.put("gid", "raw.gid");
        fieldsMap.put("did", "raw.did");
        fieldsMap.put("newData", "raw.is_new_data");
        fieldsMap.put("dataContent", "raw.data_content");
        fieldsMap.put("createDate", "raw.create_date");

        return fieldsMap;
    }

    private static Map<String, String> getWorkstationSessionFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "s.record_id");

        fieldsMap.put("workstationId", "raw.station_id");
        fieldsMap.put("sessionType", "raw.session_type");
        fieldsMap.put("currentStep", "raw.current_step");
        fieldsMap.put("operatorId", "raw.operator_id");
        fieldsMap.put("gid", "raw.gid");
        fieldsMap.put("did", "raw.did");
        fieldsMap.put("create_time", "raw.create_time");
        fieldsMap.put("last_process_time", "raw.last_process_time");

        return fieldsMap;
    }

    private static Map<String, String> getWorkstationSessionStepFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "st.record_id");

        fieldsMap.put("workstationSessionId", "st.workstation_session_id");
        fieldsMap.put("step", "st.step");
        fieldsMap.put("reqTime", "st.req_time");
        fieldsMap.put("reqDataType", "st.req_data_type");
        fieldsMap.put("reqData", "st.req_data");
        fieldsMap.put("respData", "st.resp_data");
        fieldsMap.put("respTime", "st.resp_time");

        return fieldsMap;
    }

    private static Map<String, String> getSyncDataFieldsMap() {
        Map<String, String> fieldsMap = new HashMap<>();
        fieldsMap.put("recordId", "sync.record_id");
        fieldsMap.put("classType", "sync.class_Type");
        fieldsMap.put("businessId", "sync.business_id");
        fieldsMap.put("syncTime", "sync.sync_time");

        return fieldsMap;
    }

    public static void initFieldsMap() {
        //系统管理
        CrudLogic.fillFieldsMap(TraceInfo.class, FieldsMapInitiator.getTraceInfoFieldsMap());
        CrudLogic.fillFieldsMap(SystemProgram.class, FieldsMapInitiator.getProgramFieldsMap());
        CrudLogic.fillFieldsMap(ProgramPrivilege.class, FieldsMapInitiator.getProgramPrivilegeFieldsMap());
        CrudLogic.fillFieldsMap(SystemRole.class, FieldsMapInitiator.getSystemRoleFieldsMap());
        CrudLogic.fillFieldsMap(SystemUser.class, FieldsMapInitiator.getSystemUserFieldsMap());
        CrudLogic.fillFieldsMap(SystemParam.class, FieldsMapInitiator.getSystemParamFieldsMap());

        //人员与组织结构
        CrudLogic.fillFieldsMap(Workshop.class, FieldsMapInitiator.getWorkshopFieldsMap());
        CrudLogic.fillFieldsMap(Workstation.class, FieldsMapInitiator.getWorkstationFieldsMap());
        CrudLogic.fillFieldsMap(Operator.class, FieldsMapInitiator.getOperatorFieldsMap());

        //物料
        CrudLogic.fillFieldsMap(Material.class, FieldsMapInitiator.getMaterialFieldsMap());
        CrudLogic.fillFieldsMap(Bom.class, FieldsMapInitiator.getBomFieldsMap());
        CrudLogic.fillFieldsMap(MaterialStock.class, FieldsMapInitiator.getMaterialStockFieldsMap());

        //生产
        CrudLogic.fillFieldsMap(RfidCard.class, FieldsMapInitiator.getRfidCardFieldsMap());
        CrudLogic.fillFieldsMap(ProductSummary.class, FieldsMapInitiator.getProductSummaryFieldsMap());
        CrudLogic.fillFieldsMap(ProductRecord.class, FieldsMapInitiator.getProductRecordFieldsMap());
        CrudLogic.fillFieldsMap(ProductionMoving.class, FieldsMapInitiator.getProductionMovingFieldsMap());
        CrudLogic.fillFieldsMap(DefectCode.class, FieldsMapInitiator.getDefectCodeFieldsMap());
        CrudLogic.fillFieldsMap(QualityCheck.class, FieldsMapInitiator.getQualityCheckFieldsMap());
        CrudLogic.fillFieldsMap(WorkstationBind.class, FieldsMapInitiator.getWorkstationBindFieldsMap());
        CrudLogic.fillFieldsMap(CardBind.class, FieldsMapInitiator.getCardBindFieldsMap());

        //工位机:科启奥
        CrudLogic.fillFieldsMap(StationInfo.class, FieldsMapInitiator.getStationInfoFieldsMap());
        CrudLogic.fillFieldsMap(ConterInfo.class, FieldsMapInitiator.getConterInfoFieldsMap());
        CrudLogic.fillFieldsMap(DeviceRawData.class, FieldsMapInitiator.getDeviceRawDataFieldsMap());
        CrudLogic.fillFieldsMap(WorkstationSession.class, FieldsMapInitiator.getWorkstationSessionFieldsMap());
        CrudLogic.fillFieldsMap(WorkstationSessionStep.class, FieldsMapInitiator.getWorkstationSessionStepFieldsMap());

        //系统同步
        CrudLogic.fillFieldsMap(SyncData.class, FieldsMapInitiator.getSyncDataFieldsMap());
    }
}
