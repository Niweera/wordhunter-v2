package gq.niweera.wordhunterapi.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import gq.niweera.wordhunterapi.model.Anagram;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class EnygmaService {
    private final RestTemplate restTemplate;

    public EnygmaService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getFallbackAnagramsList", commandKey = "enygmaServiceCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "100"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    }, threadPoolKey = "enygmaServicePool", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "20"),
            @HystrixProperty(name = "maxQueueSize", value = "10")
    })
    public List<String> getAnagramsList(String letters) {
        Anagram anagrams = restTemplate.getForObject("http://enygma/" + letters, Anagram.class);
        return anagrams != null ? anagrams.getAnagrams() : Collections.emptyList();
    }

    public List<String> getFallbackAnagramsList(String letters) {
        return Collections.singletonList(letters);
    }
}
