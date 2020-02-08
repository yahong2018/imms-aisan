package com.zhxh.imms.si.kocheer.logic;

import com.zhxh.imms.admin.domain.SystemUser;
import com.zhxh.imms.admin.logic.SystemUserLogic;
import com.zhxh.imms.data.BusinessException;
import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.data.DbQueryParameter;
import com.zhxh.imms.si.kocheer.domain.StationInfo;
import com.zhxh.imms.si.kocheer.wdto.StationLoginRequestData;
import com.zhxh.imms.web.FilterExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StationInfoLogic extends CrudLogic<StationInfo> {
    private final SystemUserLogic userLogic;

    public StationInfoLogic(SystemUserLogic userLogic) {
        this.userLogic = userLogic;
    }

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
            throw new BusinessException("账号不存在");
        }
        if (!user.getPwd().equalsIgnoreCase(requestData.getLoginPass())) {
            throw new BusinessException("密码错误!");
        }

        FilterExpression expr = new FilterExpression("stationCode", "=", requestData.getStationCode());
        DbQueryParameter parameter = new DbQueryParameter();
        FilterExpression.fillWhere(StationInfo.class, parameter, expr);

        StationInfo result = this.get(parameter);

        if (result == null) {
            result = this.buildStationByRequest(requestData, user);
        } else {
            result.setUserID(user.getRecordId());
            result.setLoginName(user.getUserCode());
            result.setUserName(user.getDisplayName());
            result.setUserType(1);
        }
        return result;
    }

    private StationInfo buildStationByRequest(StationLoginRequestData requestData, SystemUser user) {
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
