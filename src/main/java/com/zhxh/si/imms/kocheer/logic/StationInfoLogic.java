package com.zhxh.si.imms.kocheer.logic;

import com.zhxh.admin.domain.SystemUser;
import com.zhxh.admin.logic.SystemUserLogic;
import com.zhxh.data.CrudLogic;
import com.zhxh.data.DbQueryParameter;
import com.zhxh.si.imms.kocheer.domain.StationInfo;
import com.zhxh.si.imms.kocheer.wdto.StationLoginRequestData;
import com.zhxh.web.FilterExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StationInfoLogic extends CrudLogic<StationInfo> {
    @Autowired
    private SystemUserLogic userLogic;

    public StationInfo autoLogin(StationLoginRequestData requestData) {
        StationInfo result = this.assureStation(requestData);
        result.setStationIP(requestData.getStationIP());
        result.setLastLoginTime(LocalDateTime.now());
        result.setSoftWareVersion(requestData.getSoftWareVersion());
        result.setSoftWareName(requestData.getSoftWareName());
        this.update(result);

        return result;
    }

    private synchronized StationInfo assureStation(StationLoginRequestData requestData) {
        SystemUser user = this.userLogic.getByCode(requestData.getLoginName());
        if (user == null) {
            throw new RuntimeException("账号不存在");
        }
        if (!user.getPwd().equalsIgnoreCase(requestData.getLoginPass())) {
            throw new RuntimeException("密码错误!");
        }

        FilterExpression expr = new FilterExpression("stationCode", "=", requestData.getStationCode());
        DbQueryParameter parameter = new DbQueryParameter();
        FilterExpression.fillWhere(StationInfo.class, parameter, expr);

        List<StationInfo> list = this.getAll(parameter);
        StationInfo result;
        if (list.size() == 0) {
            result = this.buildStationByRequest(requestData,user);
        } else {
            result = list.get(0);
            result.setUserID(user.getRecordId());
            result.setLoginName(user.getUserCode());
            result.setUserName(user.getDisplayName());
            result.setUserType(1);
        }
        return result;
    }

    private StationInfo buildStationByRequest(StationLoginRequestData requestData,SystemUser user) {
        StationInfo stationInfo = new StationInfo();
        stationInfo.setStationCode(requestData.getStationCode());
        stationInfo.setStationName("站点1");
        stationInfo.setStationPosition("位置1");
        stationInfo.setSoftWareName(requestData.getSoftWareName());
        stationInfo.setSoftWareVersion(requestData.getSoftWareVersion());
        stationInfo.setStationIP(requestData.getStationIP());
        stationInfo.setStationLoginState(StationInfo.LOGIN_STATE_ONLINE);
        stationInfo.setLoginUserID(user.getRecordId());
        stationInfo.setFirstLoginTime(LocalDateTime.now());
        stationInfo.setLastLoginTime(stationInfo.getFirstLoginTime());
        stationInfo.setLastStateUpdateTime(stationInfo.getFirstLoginTime());
        stationInfo.setIsUse(StationInfo.STATION_IN_USE);

        this.create(stationInfo);
        return stationInfo;
    }
}