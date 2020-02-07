package com.zhxh.imms.backSservice;


import com.zhxh.imms.utils.Logger;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceManager {
    public void start() {
        this.init();
        for (BaseService service : this.getServiceList()) {
            service.start();
        }
        Logger.info("系统服务已全部启动...");
    }

    public void stop() {
        for (int i = this.getServiceList().size(); i > 0; i--) {
            BaseService service = this.getServiceList().get(i - 1);
            service.stop();
        }
        /*
        for (BaseService service : this.getServiceList()) {
            service.stop();
        }
        */
        Logger.info("系统服务已全部停止...");
    }

    public void init() {
        for (BaseService service : this.getServiceList()) {
            service.init();
        }
        Logger.info("系统服务已全部初始化...");
    }

    public void clean() {
        for (BaseService service : this.getServiceList()) {
            service.clean();
        }
        Logger.info("系统服务已全部清理...");
    }

    private List<BaseService> serviceList=new ArrayList<>();
    public List<BaseService> getServiceList() {
        return serviceList;
    }
}
