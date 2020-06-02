package gq.niweera.wordhunterapi.config;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {
    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

    @Bean
    public WebClient.Builder getWebClientBuilder() {
        return WebClient.builder();
    }
}
