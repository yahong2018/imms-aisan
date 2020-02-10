package com.zhxh.imms.si.kocheer.controller;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.si.kocheer.domain.StationInfo;
import com.zhxh.imms.si.kocheer.logic.StationInfoLogic;
import com.zhxh.imms.web.CrudController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/imms/si/kocheer/station")
public class StationController extends CrudController<StationInfo> {
    private final StationInfoLogic stationInfoLogic;

    public StationController(StationInfoLogic stationInfoLogic) {
        this.stationInfoLogic = stationInfoLogic;
    }

    @Override
    protected CrudLogic<StationInfo> getLogic() {
        return this.stationInfoLogic;
    }
}
