package com.zhxh.startup;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan("com.zhxh.**")
//@MapperScan("com.zhxh.**.mapper.**")
//@EnableTransactionManagement(proxyTargetClass = true)
public class ZhxhApplication {
	public static void main(String[] args) {
		SpringApplication.run(ZhxhApplication.class, args);
	}
}
