package com.zhxh.imms.si.wdb;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhxh.imms.admin.domain.SystemParam;
import com.zhxh.imms.admin.logic.SystemParamLogic;
import com.zhxh.imms.backSservice.ThreadService;
import com.zhxh.imms.data.BusinessException;
import com.zhxh.imms.data.DbQueryParameter;
import com.zhxh.imms.data.domain.Entity;
import com.zhxh.imms.data.domain.SyncData;
import com.zhxh.imms.data.logic.SyncDataLogic;
import com.zhxh.imms.mes.material.domain.Bom;
import com.zhxh.imms.mes.material.domain.Material;
import com.zhxh.imms.mes.material.logic.BomLogic;
import com.zhxh.imms.mes.material.logic.MaterialLogic;
import com.zhxh.imms.mes.mfc.domain.ProductRecord;
import com.zhxh.imms.mes.mfc.domain.ProductionMoving;
import com.zhxh.imms.mes.mfc.domain.QualityCheck;
import com.zhxh.imms.mes.mfc.logic.ProductRecordLogic;
import com.zhxh.imms.mes.mfc.logic.ProductionMovingLogic;
import com.zhxh.imms.mes.mfc.logic.QualityCheckLogic;
import com.zhxh.imms.mes.org.domain.Workshop;
import com.zhxh.imms.mes.org.domain.Workstation;
import com.zhxh.imms.mes.org.logic.WorkshopLogic;
import com.zhxh.imms.mes.org.logic.WorkstationLogic;
import com.zhxh.imms.si.wdb.wdto.*;
import com.zhxh.imms.utils.Logger;
import com.zhxh.imms.web.FilterExpression;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class WdbSyncService extends ThreadService {
    private final SystemParamLogic systemParamLogic;
    private final SyncDataLogic syncDataLogic;
    private final WorkshopLogic workshopLogic;
    private final WorkstationLogic workstationLogic;

    private final ProductRecordLogic productRecordLogic;
    private final ProductionMovingLogic productionMovingLogic;
    private final QualityCheckLogic qualityCheckLogic;
    private final MaterialLogic materialLogic;
    private final BomLogic bomLogic;

    public WdbSyncService(SystemParamLogic systemParamLogic, SyncDataLogic syncDataLogic, WorkshopLogic workshopLogic, ProductRecordLogic productRecordLogic, ProductionMovingLogic productionMovingLogic, QualityCheckLogic qualityCheckLogic, WorkstationLogic workstationLogic, MaterialLogic materialLogic, BomLogic bomLogic) {
        this.setName("万达宝同步服务");

        this.systemParamLogic = systemParamLogic;
        this.syncDataLogic = syncDataLogic;
        this.workshopLogic = workshopLogic;
        this.productRecordLogic = productRecordLogic;
        this.productionMovingLogic = productionMovingLogic;
        this.qualityCheckLogic = qualityCheckLogic;
        this.workstationLogic = workstationLogic;
        this.materialLogic = materialLogic;
        this.bomLogic = bomLogic;
    }

    @Override
    protected void doInternalRun() {
        if (this.nextRunTime.isAfter(LocalDateTime.now())) {
            return;
        }

        int cycleTime = this.getCycleMinute();
        if (cycleTime <= 0) {
            this.nextRunTime = this.nextRunTime.plusMinutes(defaultCycle);
            return;
        }
        this.nextRunTime = this.nextRunTime.plusMinutes(cycleTime);
        this.syncData();
    }

    public synchronized void syncData() {
        try {
            initParameter();
            getData();
            if (!getLoginToken()) {
                return;
            }
            try {
                pull();
            } catch (Exception e) {
                Logger.error(e);
            }

            push();
        } catch (Exception e) {
            Logger.error(e);
            throw e;
        }
    }

    private void pull() {
        Logger.info("开始同步BOM");
        String bomGetUrl = this.syncParameters.get("server_host") + "/" + this.syncParameters.get("bom_get_url");
        List<Material> materials = this.materialLogic.getMaterialsHasNoBom();
        for (Material material : materials) {
            String url = bomGetUrl + "/" + material.getMaterialCode();
            this.syncBomFromErp(url);
        }

        Logger.info("BOM同步结束");
    }

    private void syncBomFromErp(String url) {
        HttpHeaders headers = this.getHeaders();
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestBody = new HttpEntity<>(null, headers);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestBody, String.class);
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            Logger.error("同步BOM失败，http 状态码为:" + responseEntity.getStatusCodeValue());
            return;
        }
        String bomResultString = responseEntity.getBody();
        Logger.info("同步结果如下：" + bomResultString);
        try {
            if (bomResultString.startsWith("\"")) {
                bomResultString = this.getGson().fromJson(bomResultString, String.class);
            }
            BomSyncData bomSyncData = this.getGson().fromJson(bomResultString, BomSyncData.class);
            importBom(bomSyncData);
        } catch (Exception e) {
            Logger.error("解析BOM同步结果错误");
            Logger.error(e);
        }
    }

    private void importBom(BomSyncData bomSyncData) {
        for (BomSyncItem item : bomSyncData.getValues()) {
            Material material = materialLogic.getByCode(item.getProCode());
            if (material == null) {
                Logger.error("导入物料" + item.getProCode() + "的BOM数据错误:物料编号不存在");
                continue;
            }
            Material component = materialLogic.getByCode(item.getMatCode());
            if (component == null) {
                Logger.error("导入物料" + item.getMatCode() + "的BOM数据错误:组件编号不存在");
                continue;
            }

            Bom bom = new Bom();
            bom.setBomNo(item.getBomCode());
            bom.setBomStatus(1);
            bom.setBomType(1);
            LocalDate date = LocalDate.parse(item.getTDate(), formatter);
            bom.setEffectDate(date);
            bom.setMaterialId(material.getRecordId());
            bom.setComponentId(component.getRecordId());
            bom.setMaterialQty(1);
            bom.setComponentQty((int) item.getAQty());

            bomLogic.create(bom);
        }
    }


    private void remarkSyncSuccess(TookPlaceTimeRecord record) {
        String recordClass = "";
        Long recordId = ((Entity) record).getRecordId();
        if (record instanceof ProductRecord) {
            recordClass = ProductRecord.class.getCanonicalName();
        } else if (record instanceof ProductionMoving) {
            recordClass = ProductionMoving.class.getCanonicalName();
        } else if (record instanceof QualityCheck) {
            recordClass = QualityCheck.class.getCanonicalName();
        }

        SyncData syncData = new SyncData();
        syncData.setBusinessId(recordId);
        syncData.setClassType(recordClass);
        syncData.setSyncTime(LocalDateTime.now());
        this.syncDataLogic.create(syncData);
    }

    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("authorization", "Bearer " + this.loginResult.getAccess_token());
        headers.add("client_id", this.syncParameters.get("client_id"));
        headers.add("cache-control", "no-cache");
        headers.add("content-type", "application/json");
        return headers;
    }

    private void pushToErp(String url, Object data) {
        HttpHeaders headers = this.getHeaders();
        String json = this.getGson().toJson(data);

        Logger.info("----------------------------------------------------------");
        Logger.info("准备向万达宝服务器发送如下数据：");
        Logger.info(json);
        Logger.info("----------------------------------------------------------");

        HttpEntity<String> requestBody = new HttpEntity<>(json, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestBody, String.class);
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            throw new BusinessException("向万达宝服务器推送数据失败，Http 状态码为:" + responseEntity.getStatusCodeValue());
        }
        String syncResult = responseEntity.getBody();

        Logger.info("----------------------------------------------------------");
        Logger.info("同步结果如下:\n" + syncResult);
        Logger.info("----------------------------------------------------------");

        if (syncResult.startsWith("\"")) {
            syncResult = this.getGson().fromJson(syncResult, String.class);
        }

        WDBSyncResponse response = this.getGson().fromJson(syncResult, WDBSyncResponse.class);
        if (!response.isStatus()) {
            throw new BusinessException("向万达宝服务器同步数据失败");
        }

        Logger.info("同步数据成功");
    }

    private int getBeId() {
        return Integer.parseInt(this.syncParameters.get("account_id"));
    }

    private void pushQc(QualityCheck record) {
        String url = this.syncParameters.get("server_host") + "/" + this.syncParameters.get("qualitycheck_report_url");

        QualitySyncData syncData = new QualitySyncData();
        syncData.setBeId(this.getBeId());
        syncData.setDoctypeId(4); //固定为4

        QualitySyncItem item = new QualitySyncItem();
        item.setProcode(record.getProductionCode());
        item.setQty(record.getQty());
        item.setUnitcode("pcs");
        item.setWcgcode(record.getWocgCode());
        item.setLoccode(record.getWorkshopCode() + "_BAD");
        item.setUdfblbm(record.getLocCode());
        item.setUdfbldm(record.getDefectCode());

        syncData.setProdpwt(new QualitySyncItem[]{item});

        this.pushToErp(url, syncData);
    }

    private void pushMoving(ProductionMoving record) {
        String url = this.syncParameters.get("server_host") + "/" + this.syncParameters.get("moving_report_url");
        MoveSyncData move = new MoveSyncData();
        move.setBeId(this.getBeId());

        MoveSyncItem item = new MoveSyncItem();
        item.setQty(record.getQty());
        item.setUnitcode("pcs");
        item.setProcode(record.getProductionCode());
        item.setLoccode(record.getWorkshopCodeFrom());
        item.setAloccode(record.getWorkshopCode());

        move.setMovet(new MoveSyncItem[]{item});

        this.pushToErp(url, move);
    }

    private void pushInstore(ProductRecord record, List<Workshop> workshopList) {
        Workshop workshop = workshopList.stream().filter(x -> x.getRecordId().equals(record.getWorkshopId())).findFirst().get();
        String url = this.syncParameters.get("server_host") + "/" + this.syncParameters.get("progress_report_url");
        Object data;
        if (workshop.getWorkshopType() == Workshop.WORKSHOP_OUTSOURCE) { //委外入库
            Logger.info("开始同步委外入库数据");
            data = this.buildWWData(record);
        } else {
            Logger.info("开始同步内部入库数据");
            data = this.buildInstoreData(record);
        }
        this.pushToErp(url, data);
    }

    private InstoreSyncData buildInstoreData(ProductRecord record) {
        Workstation workstation = workstationList.stream().filter(x -> x.getRecordId().equals(record.getWorkstationId())).findFirst().get();

        InstoreSyncData result = new InstoreSyncData();
        result.setBeId(this.getBeId());

        InstoreSyncItem item = new InstoreSyncItem();
        item.setProcode(record.getProductionCode());
        item.setUnitcode("pcs");
        item.setQty(record.getQty());
        item.setWcgcode(record.getWocgCode());
        item.setLoccode(workstation.getLocCode());

        result.setProdpwt(new InstoreSyncItem[]{item});

        return result;
    }


    private InstoreSyncDataWW buildWWData(ProductRecord record) {
        Workstation workstation = workstationList.stream().filter(x -> x.getRecordId().equals(record.getWorkstationId())).findFirst().get();

        InstoreSyncDataWW result = new InstoreSyncDataWW();
        result.setBeId(this.getBeId());

        InstoreSyncItemWW item = new InstoreSyncItemWW();
        item.setProcode(record.getProductionCode());
        item.setUnitcode("pcs");
        item.setQty(record.getQty());
        item.setLoccode(workstation.getLocCode());

        result.setPdcorespwt(new InstoreSyncItemWW[]{item});

        return result;
    }

    private void push() {
        for (TookPlaceTimeRecord record : this.dataListTobeSync) {
            if (record instanceof ProductRecord) {
                pushInstore((ProductRecord) record, workshopList);
            } else if (record instanceof ProductionMoving) {
                Logger.info("开始同步移库数据");
                pushMoving((ProductionMoving) record);
            } else if (record instanceof QualityCheck) {
                Logger.info("开始同步不良数据");
                pushQc((QualityCheck) record);
            }
            this.remarkSyncSuccess(record);
        }
    }

    private boolean getLoginToken() {
        Map<String, String> map = this.syncParameters;
        String url = map.get("server_host") + "/" + map.get("login_url") +
                "?grant_type=" + map.get("grant_type") +
                "&client_id=" + map.get("client_id") +
                "&client_secret=" + map.get("client_secret") +
                "&username=" + map.get("username") +
                "&password=" + map.get("password");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            Logger.error("登录万达宝服务器失败，http 状态码为:" + responseEntity.getStatusCodeValue());
            return false;
        }
        String loginResultString = responseEntity.getBody();
        Logger.info("登录万达宝服务器，返回结果如下：" + loginResultString);
        try {
            this.loginResult = this.getGson().fromJson(loginResultString, WDBLoginResult.class);
            return true;
        } catch (Exception e) {
            Logger.error("解析登录结果错误");
            Logger.error(e);
            return false;
        }
    }

    private void initParameter() {
        FilterExpression expr = this.createParamTypeFilter();
        DbQueryParameter query = new DbQueryParameter(SystemParam.class, expr);
        List<SystemParam> paramList = this.systemParamLogic.getAll(query);
        paramList.forEach(x -> {
            syncParameters.put(x.getParamCode(), x.getParamValue());
        });
    }

    private void getData() {
        this.dataListTobeSync = new ArrayList<>();

        List<ProductRecord> instoreList = this.getInstoreList();
        this.dataListTobeSync.addAll(instoreList);
        List<ProductionMoving> movingList = this.getMovingList();
        this.dataListTobeSync.addAll(movingList);
        List<QualityCheck> checkList = this.getQCList();
        this.dataListTobeSync.addAll(checkList);

        this.workshopList = workshopLogic.getAll();
        this.workstationList = workstationLogic.getAll();

        //按照业务发生的先后顺序对数据进行排序，从而确保数据的准确性
        this.dataListTobeSync.sort(Comparator.comparing(TookPlaceTimeRecord::getTookPlaceTime));
    }

    private List<ProductRecord> getInstoreList() {
        String where = "pr.record_id not in(select business_id from zhxh_sync_data where class_type = 'com.zhxh.imms.mfc.domain.ProductRecord')";
        DbQueryParameter query = new DbQueryParameter();
        query.setWhere(where);

        return this.productRecordLogic.getAll(query);
    }

    private List<ProductionMoving> getMovingList() {
        String where = "pmv.record_id not in(select business_id from zhxh_sync_data where class_type = 'com.zhxh.imms.mfc.domain.ProductionMoving')";
        DbQueryParameter query = new DbQueryParameter();
        query.setWhere(where);

        return this.productionMovingLogic.getAll(query);
    }

    private List<QualityCheck> getQCList() {
        String where = "qc.record_id not in(select business_id from zhxh_sync_data where class_type = 'com.zhxh.imms.mfc.domain.QualityCheck')";
        DbQueryParameter query = new DbQueryParameter();
        query.setWhere(where);

        return this.qualityCheckLogic.getAll(query);
    }

    private int getCycleMinute() {
        FilterExpression[] expressions = new FilterExpression[2];
        expressions[0] = this.createParamTypeFilter();
        expressions[1] = new FilterExpression("paramCode", "=", "sync_cycle_minutes", "and");
        DbQueryParameter query = new DbQueryParameter(SystemParam.class, expressions);

        SystemParam param = this.systemParamLogic.get(query);
        if (param == null) {
            return -1;
        }
        return Integer.parseInt(param.getParamValue());
    }

    private FilterExpression createParamTypeFilter() {
        return new FilterExpression("paramType", "=", paramType);
    }

    private Gson getGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY).create();
    }

    private LocalDateTime nextRunTime = LocalDateTime.now().plusMinutes(defaultCycle);
    private final static String paramType = "B003";
    private final static int defaultCycle = 5;

    private Map<String, String> syncParameters = new HashMap<>();
    private WDBLoginResult loginResult;
    private List<TookPlaceTimeRecord> dataListTobeSync;
    private List<Workshop> workshopList;
    private List<Workstation> workstationList;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected boolean doInternalInit() {
        return true;
    }

    @Override
    public void clean() {
    }
}
