package com.thanthu.orgservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@RefreshScope
@SpringBootApplication
public class OrgServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrgServiceApplication.class, args);
	}

}
