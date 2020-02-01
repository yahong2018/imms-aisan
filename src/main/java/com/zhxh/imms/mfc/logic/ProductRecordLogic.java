package com.zhxh.imms.mfc.logic;

import com.zhxh.imms.material.domain.Bom;
import com.zhxh.imms.mfc.domain.ProductRecord;
import com.zhxh.imms.mfc.domain.RfidCard;
import com.zhxh.imms.mfc.domain.WorkshopReportRecord;
import com.zhxh.imms.mfc.mapper.ProductRecordMapper;
import com.zhxh.imms.org.domain.Workstation;
import com.zhxh.imms.org.logic.WorkstationLogic;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Map;

@Component
public class ProductRecordLogic extends WorkshopReportLogic<ProductRecord> {
    private final WorkstationLogic workstationLogic;
    private final RfidCardLogic cardLogic;

    public ProductRecordLogic(RfidCardLogic cardLogic, WorkstationLogic workstationLogic) {
        this.cardLogic = cardLogic;
        this.workstationLogic = workstationLogic;
    }

    public void verify(ProductRecord productRecord) {
        /*
        1. 验证卡，状态、数量
        2. 车间、工位
        3. RFID卡号
        */
        if (!StringUtils.isEmpty(productRecord.getRfidCardNo())) {
            RfidCard card = this.cardLogic.getByRfidNo(productRecord.getRfidCardNo());
            if (card != null) {
                if (card.getIssueQty() < card.getStockQty() + productRecord.getQty()) {
                    throw new RuntimeException("报工数量错误:报工数量+完工数量>派发数量！");
                }

                productRecord.setRfidCardId(card.getRecordId());
                productRecord.setCardQty(card.getIssueQty());
            } else {
                throw new RuntimeException("RFID卡号：" + productRecord.getRfidCardNo() + "错误！");
            }
        }

        if (productRecord.getWorkshopId() == null || productRecord.getWorkstationId() == null) {
            throw new RuntimeException("车间和工位必须输入!");
        }
    }

    public void reportWip(ProductRecord productRecord) {
        this.verify(productRecord);
        Workstation workstation = workstationLogic.get(productRecord.getWorkstationId());
        int autoReportLevel = 0;
        if (workstation != null) {
            autoReportLevel = workstation.getAutoReportCount();
        }
        this.reportWip(productRecord, autoReportLevel);
    }

    @Override
    public int create(ProductRecord item) throws RuntimeException {
        item.fillWorkShift();
        int result = super.create(item);
        this.updateCardStatus(item);

        return result;
    }

    public Map<String, Integer> getReportedSummary(Long workstationId, LocalDate workDay, Long productionId, Integer shiftId) {
        return ((ProductRecordMapper) this.getMapper()).getReportedSummary(workstationId, workDay, productionId, shiftId);
    }


    @Override
    protected ProductRecord createRecord() {
        return new ProductRecord();
    }

    @Override
    protected ProductRecord buildAutoReportRecord(ProductRecord masterRecord, Bom bom) {
        ProductRecord autoReportRecord = super.buildAutoReportRecord(masterRecord, bom);

        autoReportRecord.setWorkstationId(masterRecord.getWorkstationId());
        autoReportRecord.setWocgCode(masterRecord.getWocgCode());
        autoReportRecord.setOperatorId(masterRecord.getOperatorId());
        autoReportRecord.setGid(masterRecord.getGid());
        autoReportRecord.setDid(masterRecord.getDid());
        autoReportRecord.setRfidCardNo(masterRecord.getRfidCardNo());
        autoReportRecord.setTimeOfOrigin(masterRecord.getTimeOfOrigin());

        autoReportRecord.setRemark("自动报工，原卡号为:" + masterRecord.getRfidCardNo());

        return autoReportRecord;
    }

    private void updateCardStatus(ProductRecord productRecord) {
        RfidCard card = productRecord.getCard();
        if (card == null) {
            if (productRecord.getRfidCardId() == null) {
                return;
            }
            card = cardLogic.get(productRecord.getRfidCardId());
            if (card == null) {
                return;
            }
        }
        card.setCardStatus(RfidCard.CARD_STATUS_REPORTED);
        card.setLastBusinessId(productRecord.getRecordId());
        if (productRecord.getReportType() == WorkshopReportRecord.REPORT_TYPE_WHOLE) {
            card.setStockQty(productRecord.getQty());
        } else {
            card.setStockQty(productRecord.getQty() + card.getStockQty());
        }

        cardLogic.update(card);
    }
}
