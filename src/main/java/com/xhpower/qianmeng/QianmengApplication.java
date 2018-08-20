package com.xhpower.qianmeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.xhpower.qianmeng*"})
public class QianmengApplication {

	public static void main(String[] args) {
		SpringApplication.run(QianmengApplication.class, args);
	}
}
