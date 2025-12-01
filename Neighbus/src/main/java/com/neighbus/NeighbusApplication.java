package com.neighbus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NeighbusApplication {

	public static void main(String[] args) {
		SpringApplication.run(NeighbusApplication.class, args);
		System.out.println("서버 실행되었습니다");
	}
	

}
