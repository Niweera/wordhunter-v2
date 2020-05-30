package gq.niweera.wordhunterapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WordhunterApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordhunterApiApplication.class, args);
    }

}
