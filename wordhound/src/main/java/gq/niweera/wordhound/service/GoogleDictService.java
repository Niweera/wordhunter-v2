package gq.niweera.wordhound.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import gq.niweera.wordhound.model.Dictionary;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GoogleDictService {
    private final RestTemplate restTemplate;

    public GoogleDictService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getFallbackFromGoogleDict",
            commandKey = "googleDictServiceCommand",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "100"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }, threadPoolKey = "googleDictServicePool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })
    public @Nullable Dictionary getFromGoogleDict(String word) {
        Dictionary definition = restTemplate.getForObject("http://googledict/" + word, Dictionary.class);
        if (definition != null && !definition.getWord().equals("not-found-error")) {
            return definition;
        } else {
            return null;
        }
    }

    public @Nullable Dictionary getFallbackFromGoogleDict(String word) {
        return null;
    }
}
