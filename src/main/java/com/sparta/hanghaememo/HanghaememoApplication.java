package com.sparta.hanghaememo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //스프링 JPA상에서 자동으로 데이터를 집어 넣으면 자동으로 데이터가 생성
@SpringBootApplication
public class HanghaememoApplication {

	public static void main(String[] args) {
		SpringApplication.run(HanghaememoApplication.class, args);
	}

}
