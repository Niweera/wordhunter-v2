package gq.niweera.wordhound.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import gq.niweera.wordhound.model.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordCacheService {
    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public WordCacheService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    public void saveDefinitionInWordCache(Dictionary newDefinition) {
        dictionaryRepository.save(newDefinition);
    }

    @HystrixCommand(fallbackMethod = "getFallbackDefinitionFormWordCache",
            commandKey = "wordCacheServiceCommand",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "100"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }, threadPoolKey = "wordCacheServicePool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })
    public Dictionary getDefinitionFormWordCache(String word) {
        return dictionaryRepository.findOneByWord(word);
    }

    public Dictionary getFallbackDefinitionFormWordCache(String word) {
        return null;
    }
}
