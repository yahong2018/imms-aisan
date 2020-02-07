package com.zhxh.thirdParty.wyl.mapper;

import com.zhxh.thirdParty.wyl.domain.DeviceState;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeviceStateMapper {
    List<DeviceState> getAll();
}
