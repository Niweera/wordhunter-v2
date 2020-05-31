package gq.niweera.wordhunterapi.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import gq.niweera.wordhunterapi.model.Dictionary;
import gq.niweera.wordhunterapi.model.DictionaryList;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordHoundService {
    private final RestTemplate restTemplate;

    public WordHoundService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getFallbackDefinitions",
            commandKey = "wordHoundServiceCommand",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1200000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "100"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }, threadPoolKey = "wordHoundServicePool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })
    public List<Dictionary> getDefinitions(@NotNull List<String> anagramsList) {
        List<Dictionary> dictionaryList = anagramsList.stream().map(word -> restTemplate.getForObject("http://wordhound/" + word, Dictionary.class)).collect(Collectors.toList());
        DictionaryList rawDefinitions = new DictionaryList(dictionaryList);
        return rawDefinitions.getDefinitions().stream().filter(item -> !item.getWord().equals("not-found-error")).collect(Collectors.toList());
    }

    public List<Dictionary> getFallbackDefinitions(@NotNull List<String> anagramsList) {
        return Collections.singletonList(new Dictionary(anagramsList.get(0), "We all can be only who we are, no more, no less."));
    }
}
