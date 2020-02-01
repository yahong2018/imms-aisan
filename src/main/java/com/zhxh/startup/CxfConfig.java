package com.zhxh.startup;

import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zhxh.si.imms.kocheer.KocheerService;

@Configuration
public class CxfConfig {
	@Autowired
	private Bus bus;

	@Autowired
	private KocheerService KocheerService;

	@Bean
	public Endpoint kocheerServiceEndpoint() {
		EndpointImpl endpoint = new EndpointImpl(bus, KocheerService);
		endpoint.publish("imms/kocheer/KocheerService");
		return endpoint;
	}
}