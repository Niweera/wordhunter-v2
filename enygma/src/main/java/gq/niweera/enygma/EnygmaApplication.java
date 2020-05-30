package gq.niweera.enygma;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EnygmaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EnygmaApplication.class, args);
    }

}
