package com.neticon.neticon;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.neticon.neticon.repository.mybatis.mapper")
public class NetIconApplication {

	public static void main(String[] args) {
		SpringApplication.run(NetIconApplication.class, args);
	}

}
