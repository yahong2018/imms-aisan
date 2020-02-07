package com.zhxh.thirdparty.wyl.mapper;

import com.zhxh.thirdparty.wyl.domain.DeviceState;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DeviceStateMapper {
    List<DeviceState> getAll();
}
