package com.zhxh.imms.mes.mfc.logic;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.mes.material.domain.Bom;
import com.zhxh.imms.mes.material.domain.Material;
import com.zhxh.imms.mes.material.domain.MaterialStock;
import com.zhxh.imms.mes.material.logic.BomLogic;
import com.zhxh.imms.mes.material.logic.MaterialLogic;
import com.zhxh.imms.mes.material.logic.MaterialStockLogic;
import com.zhxh.imms.mes.mfc.domain.ProductSummary;
import com.zhxh.imms.mes.mfc.domain.WorkshopReportRecord;
import com.zhxh.imms.mes.org.domain.Workshop;
import com.zhxh.imms.mes.org.logic.WorkshopLogic;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public abstract class WorkshopReportLogic<T extends WorkshopReportRecord> extends CrudLogic<T> {
    @Autowired
    private ProductSummaryLogic productSummaryLogic;
    @Autowired
    private MaterialStockLogic stockLogic;
    @Autowired
    private BomLogic bomLogic;
    @Autowired
    private MaterialLogic materialLogic;
    @Autowired
    private WorkshopLogic workshopLogic;

    protected abstract T createRecord();

    public synchronized int reportWip(T record, int autoReportLevel)  {
        /*
            1.报工记录新增
            2.生产库存调整
            3.生产进度更新
            4.卡的状态更新
            5.处理自动报工
        */
        int result = this.create(record);
        List<Bom> bomList = this.adjustStock(record);
        this.updateProductProgress(record);
        if (record.getReportType() != WorkshopReportRecord.REPORT_TYPE_QC) {
            //品质报工，则不自动进行报工
            this.processAutoReport(record, bomList, autoReportLevel);
        }
        return result;
    }

    private void processAutoReport(T record, List<Bom> bomList, int autoReportLevel) {
        for (Bom component : bomList) {
            Material material = materialLogic.get(component.getComponentId());
            if (!material.isAutoFinishedProgress() && autoReportLevel <= 0) {
                continue;
            }
            T autoReportRecord = this.buildAutoReportRecord(record, component);
            this.reportWip(autoReportRecord, autoReportLevel - 1);  //进行自动报工
        }
    }

    protected T buildAutoReportRecord(T masterRecord, Bom bom) {
        T autoReportRecord = this.createRecord();
        int reportQty = masterRecord.getQty() * bom.getComponentQty() / bom.getMaterialQty();
        autoReportRecord.setQty(reportQty);
        autoReportRecord.setProductionId(bom.getComponentId());
        autoReportRecord.setWorkshopId(masterRecord.getWorkshopId());
        autoReportRecord.setReportType(WorkshopReportRecord.REPORT_TYPE_AUTO); //自动报工
        //autoReportRecord.setRfidCardId(masterRecord.getRfidCardId()); --自动报工的只记录卡号，不修改与卡相关的数据

        autoReportRecord.setShiftId(masterRecord.getShiftId());
        autoReportRecord.setTimeOfOriginWork(masterRecord.getTimeOfOriginWork());

        return autoReportRecord;
    }

    private void updateProductProgress(WorkshopReportRecord record) {
        ProductSummary productSummary = productSummaryLogic.assureProductSummary(record.getProductionId(), record.getWorkshopId(), record.getTimeOfOriginWork());
        if (record.getReportType() != WorkshopReportRecord.REPORT_TYPE_QC) {
            if (record.getShiftId() == 0) {
                productSummary.setQtyGood0(productSummary.getQtyGood0() + record.getQty());
            } else {
                productSummary.setQtyGood1(productSummary.getQtyGood1() + record.getQty());
            }
        } else {
            if (record.getShiftId() == 0) {
                productSummary.setQtyDefect0(productSummary.getQtyDefect0() + record.getQty());
            } else {
                productSummary.setQtyDefect1(productSummary.getQtyDefect1() + record.getQty());
            }
        }
        productSummaryLogic.update(productSummary);
    }

    //
    //库存调整：
    // 1.调整本工序的qtyStock(增加)、qtyGood
    // 2.根据Bom调整上工序部品的qtyStock（减少）、qtyConsume
    //
    private List<Bom> adjustStock(WorkshopReportRecord record)  {
        this.adjustOutputStock(record);
        Workshop workshop = record.getWorkshop();
        if(workshop==null){
            workshop = workshopLogic.get(record.getWorkshopId());
        }
        boolean isWhole = workshop.getOpIndex() == -1;
        List<Bom> bomList = bomLogic.getBom(record.getProductionId(),isWhole);
        for (Bom bom : bomList) {
            this.adjustConsumeStock(record, bom.getComponentId());
        }
        return bomList;
    }

    private void adjustConsumeStock(WorkshopReportRecord record, Long materialId){
        MaterialStock stock = stockLogic.assureStock(record.getWorkshopId(), materialId);
        int qtyStock = stock.getQtyStock() - record.getQty();
        int qtyConsumeGood = stock.getQtyConsumeGood();
        int qtyConsumeDefect = stock.getQtyConsumeDefect();
        if (WorkshopReportRecord.REPORT_TYPE_QC == record.getReportType()) {
            qtyConsumeDefect += record.getQty();
        } else {
            qtyConsumeGood += record.getQty();
        }
        stock.setQtyStock(qtyStock);
        stock.setQtyConsumeGood(qtyConsumeGood);
        stock.setQtyConsumeDefect(qtyConsumeDefect);

        stockLogic.update(stock);
    }

    private void adjustOutputStock(WorkshopReportRecord reportRecord)  {
        MaterialStock stock = stockLogic.assureStock(reportRecord.getWorkshopId(), reportRecord.getProductionId());
        int qtyStock = stock.getQtyStock() + reportRecord.getQty();
        int qtyGood = stock.getQtyGood();
        int qtyDefect = stock.getQtyDefect();
        if (WorkshopReportRecord.REPORT_TYPE_QC == reportRecord.getReportType()) {
            qtyDefect += reportRecord.getQty();
        } else {
            qtyGood += reportRecord.getQty();
        }
        stock.setQtyDefect(qtyDefect);
        stock.setQtyGood(qtyGood);
        stock.setQtyStock(qtyStock);

        stockLogic.update(stock);
    }
}
