package gq.niweera.googledict;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class GoogledictApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoogledictApplication.class, args);
	}

}
