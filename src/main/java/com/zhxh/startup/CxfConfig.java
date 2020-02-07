package com.zhxh.startup;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhxh.imms.si.kocheer.KocheerService;

@Configuration
public class CxfConfig {
	private final Bus bus;
	private final KocheerService KocheerService;
	public CxfConfig(Bus bus, KocheerService KocheerService) {
		this.bus = bus;
		this.KocheerService = KocheerService;
	}

	@Bean
	public Endpoint kocheerServiceEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, KocheerService);
		endpoint.publish("imms/kocheer/KocheerService");
		return endpoint;
	}
}