package com.zhxh.si.imms.wdb.controller;

import com.zhxh.si.imms.wdb.WdbSyncService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PushToErpController {
    private final WdbSyncService wdbSyncService;

    public PushToErpController(WdbSyncService wdbSyncService) {
        this.wdbSyncService = wdbSyncService;
    }

    @RequestMapping("api/admin/systemParam/sync_wdb")
    public int syncWb() {
        this.wdbSyncService.syncData();
        return 0;
    }
}
