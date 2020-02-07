package com.zhxh.thirdparty.wyl;

import com.zhxh.thirdparty.wyl.domain.DeviceState;
import com.zhxh.thirdparty.wyl.mapper.DeviceStateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DeviceStateLogic {
    private final DeviceStateMapper mapper;

    public DeviceStateLogic(DeviceStateMapper mapper) {
        this.mapper = mapper;
    }

    public List<DeviceState> getAll(){
        return this.mapper.getAll();
    }
}
