package com.zhxh.startup;

import com.zhxh.imms.backSservice.ServiceManager;
import com.zhxh.imms.data.FieldsMapInitiator;
import com.zhxh.imms.si.wdb.WdbSyncService;
import com.zhxh.imms.utils.converter.DateTimeDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Configuration
public class ApplicationConfig {
    @Autowired
    private ServiceManager serviceManager;

    @Autowired
    private WdbSyncService wdbSyncService;


    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;


    @PostConstruct
    public void init() {
        this.initFieldsMap();
        this.initInterceptor();
        this.initDateConverter();
        this.initServiceManager();
    }

    private void initFieldsMap() {
        FieldsMapInitiator.initFieldsMap();
    }

//    @Bean
//    public ConfigurationCustomizer configurationCustomizer(){
//        return configuration -> {
//            configuration.addInterceptor(resultTypeInterceptor);
//            configuration.addInterceptor(updateInterceptor);
//        };
//    }


    private void initInterceptor() {
    }

    private void initDateConverter() {
        ConfigurableWebBindingInitializer initializer = (ConfigurableWebBindingInitializer) handlerAdapter.getWebBindingInitializer();
        if (initializer.getConversionService() != null) {
            GenericConversionService genericConversionService = (GenericConversionService) initializer.getConversionService();
            genericConversionService.addConverter(new DateTimeDeserializer<Date>(Date.class){});
            genericConversionService.addConverter(new DateTimeDeserializer<Timestamp>(Timestamp.class){});
            genericConversionService.addConverter(new DateTimeDeserializer<LocalDate>(LocalDate.class){});
            genericConversionService.addConverter(new DateTimeDeserializer<LocalDateTime>(LocalDateTime.class){});
            genericConversionService.addConverter(new DateTimeDeserializer<LocalTime>(LocalTime.class){});
        }
    }

    private void initServiceManager() {
        this.serviceManager.getServiceList().add(wdbSyncService);

        this.serviceManager.start();
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
