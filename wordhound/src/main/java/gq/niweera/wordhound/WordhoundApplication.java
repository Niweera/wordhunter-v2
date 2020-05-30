package gq.niweera.wordhound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class WordhoundApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordhoundApplication.class, args);
    }

}
