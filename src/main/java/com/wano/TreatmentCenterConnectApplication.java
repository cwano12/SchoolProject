package com.wano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages ={"com.wano"})
public class TreatmentCenterConnectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TreatmentCenterConnectApplication.class, args);
	}
}
