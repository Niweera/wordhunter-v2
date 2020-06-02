package gq.niweera.wordhound.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import gq.niweera.wordhound.model.Dictionary;
import gq.niweera.wordhound.proxy.GoogleDictProxy;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleDictService {
    private final GoogleDictProxy googleDictProxy;

    @Autowired
    public GoogleDictService(GoogleDictProxy googleDictProxy) {
        this.googleDictProxy = googleDictProxy;
    }

    @HystrixCommand(fallbackMethod = "getFallbackFromGoogleDict",
            commandKey = "googleDictServiceCommand",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "30000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "100"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }, threadPoolKey = "googleDictServicePool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })
    public @Nullable Dictionary getFromGoogleDict(String word) {
        Dictionary definition = googleDictProxy.getGoogleDictDefinition(word);
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
