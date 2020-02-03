package com.zhxh.si.imms.wdb.controller;

import com.zhxh.data.BusinessException;
import com.zhxh.imms.material.domain.Material;
import com.zhxh.imms.material.logic.MaterialLogic;
import com.zhxh.si.imms.wdb.wdto.MaterialSyncItem;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MaterialSyncController {
    private final MaterialLogic materialLogic;
    public MaterialSyncController(MaterialLogic materialLogic) {
        this.materialLogic = materialLogic;
    }

    @PostMapping("api/imms/material/material/syncByErp")
    public int syncByErp(@RequestBody MaterialSyncItem syncItem) {
        if (syncItem.getDataType() != MaterialSyncItem.DATA_TYPE_INSERT
                && syncItem.getDataType() != MaterialSyncItem.DATA_TYPE_UPDATE
                && syncItem.getDataType() != MaterialSyncItem.DATA_TYPE_DELETE) {
            throw new BusinessException("记录类型DataType错误，必须是'0(新增)'、'1(修改)'、'2(删除)'三者之一");
        }

        Material material = new Material();
        material.setMaterialCode(syncItem.getMaterialCode());
        material.setMaterialName(syncItem.getMaterialName());
        material.setDescription(syncItem.getDescription());

        if (syncItem.getDataType() == MaterialSyncItem.DATA_TYPE_INSERT) {
            return this.materialLogic.create(material);
        } else {
            Material dbItem = materialLogic.getByCode(syncItem.getMaterialCode());
            if (dbItem == null) {
                throw new BusinessException("系统不存在materialCode=" + syncItem.getMaterialCode() + "的产品或物料");
            }
            if (syncItem.getDataType() == MaterialSyncItem.DATA_TYPE_UPDATE) {
                dbItem.setMaterialName(syncItem.getMaterialName());
                dbItem.setMaterialCode(syncItem.getDescription());
                return materialLogic.update(dbItem);
            } else {
                return materialLogic.delete(dbItem.getRecordId());
            }
        }
    }
}
