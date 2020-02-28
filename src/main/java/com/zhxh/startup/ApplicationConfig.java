package com.zhxh.startup;

import com.zhxh.imms.backSservice.ServiceManager;
import com.zhxh.imms.data.FieldsMapInitiator;
import com.zhxh.imms.si.wdb.WdbSyncService;
import com.zhxh.imms.utils.converter.DateConverter;
import com.zhxh.imms.utils.converter.LocalDateConverter;
import com.zhxh.imms.utils.converter.LocalDateTimeConverter;
import com.zhxh.imms.utils.converter.TimestampConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class ApplicationConfig {
    private final WdbSyncService wdbSyncService;
    private final ServiceManager serviceManager;
    private final RequestMappingHandlerAdapter handlerAdapter;

    public ApplicationConfig(RequestMappingHandlerAdapter handlerAdapter, WdbSyncService wdbSyncService, ServiceManager serviceManager) {
        this.handlerAdapter = handlerAdapter;
        this.wdbSyncService = wdbSyncService;
        this.serviceManager = serviceManager;
    }

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
            List<String> datePatterns = new ArrayList<>();
            datePatterns.add("yyyy/MM/dd HH:mm:ss");
            datePatterns.add("yyyy/MM/dd HH:mm");
            datePatterns.add("yyyy/MM/dd");
            datePatterns.add("yyyy-MM-dd HH:mm:ss");
            datePatterns.add("yyyy-MM-dd HH:mm");
            datePatterns.add("yyyy-MM-dd");

            DateConverter dateConverter = new DateConverter();
            dateConverter.setPatternList(datePatterns);
            genericConversionService.addConverter(dateConverter);

            TimestampConverter timestampConverter = new TimestampConverter();
            timestampConverter.setPatternList(datePatterns);
            genericConversionService.addConverter(timestampConverter);

            LocalDateConverter localDateConverter = new LocalDateConverter();
            localDateConverter.setPatternList(datePatterns);
            genericConversionService.addConverter(localDateConverter);

            LocalDateTimeConverter localDateTimeConverter = new LocalDateTimeConverter();
            localDateTimeConverter.setPatternList(datePatterns);
            genericConversionService.addConverter(localDateTimeConverter);
        }
    }

    private void initServiceManager() {
        this.serviceManager.getServiceList().add(this.wdbSyncService);
        this.serviceManager.start();
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
