package gq.niweera.wordhunterapi.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import gq.niweera.wordhunterapi.model.Anagram;
import gq.niweera.wordhunterapi.proxy.EnygmaProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class EnygmaService {
    private final EnygmaProxy enygmaProxy;

    @Autowired
    public EnygmaService(EnygmaProxy enygmaProxy) {
        this.enygmaProxy = enygmaProxy;
    }

    @HystrixCommand(fallbackMethod = "getFallbackAnagramsList", commandKey = "enygmaServiceCommand", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000"),
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "100"),
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
    }, threadPoolKey = "enygmaServicePool", threadPoolProperties = {
            @HystrixProperty(name = "coreSize", value = "20"),
            @HystrixProperty(name = "maxQueueSize", value = "10")
    })
    public List<String> getAnagramsList(String letters) {
        Anagram anagrams = enygmaProxy.getAnagramsFromEnygma(letters);
        return anagrams != null ? anagrams.getAnagrams() : Collections.emptyList();
    }

    public List<String> getFallbackAnagramsList(String letters) {
        return Collections.singletonList(letters);
    }
}
