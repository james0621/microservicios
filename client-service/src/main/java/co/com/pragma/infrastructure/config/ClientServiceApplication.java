package co.com.pragma.infrastructure.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(basePackages = "co.com.pragma.infrastructure.feign")
@SpringBootApplication(scanBasePackages = "co.com.pragma.infrastructure")
@EntityScan(basePackages = "co.com.pragma.domain")
public class ClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientServiceApplication.class, args);
	}

}
