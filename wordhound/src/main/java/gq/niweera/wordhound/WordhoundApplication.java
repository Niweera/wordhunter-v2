package gq.niweera.wordhound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableCaching
@EnableFeignClients("gq.niweera.wordhound.proxy")
@EnableDiscoveryClient
public class WordhoundApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordhoundApplication.class, args);
    }

}
