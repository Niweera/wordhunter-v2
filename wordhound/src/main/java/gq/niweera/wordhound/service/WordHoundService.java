package gq.niweera.wordhound.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import gq.niweera.wordhound.model.DefaultResponse;
import gq.niweera.wordhound.model.Dictionary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class WordHoundService {
    private final GoogleDictService googleDictService;
    private final WordCacheService wordCacheService;

    @Autowired
    public WordHoundService(GoogleDictService googleDictService, WordCacheService wordCacheService) {
        this.googleDictService = googleDictService;
        this.wordCacheService = wordCacheService;
    }

    @HystrixCommand(fallbackMethod = "getFallbackDefinition",
            commandKey = "wordHoundServiceCommand",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "25000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "100"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }, threadPoolKey = "wordHoundServicePool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })
    @Cacheable(value = "wordhound", key = "#word")
    public Dictionary getDefinition(String word) {
        Dictionary definitionFromDB;
        try {
            definitionFromDB = wordCacheService.getDefinitionFormWordCache(word);
        } catch (Exception ignored) {
            definitionFromDB = null;
        }
        if (definitionFromDB != null) {
            log.warn(word + " is not in the WordCacheDB");
            return definitionFromDB;
        } else {
            Dictionary definitionFromGoogleDict = googleDictService.getFromGoogleDict(word);
            if (definitionFromGoogleDict != null) {
                log.info(word + " is available in GoogleDict");
                try {
                    saveNewDefinition(definitionFromGoogleDict);
                    return definitionFromGoogleDict;
                } catch (Exception ignored) {
                    return definitionFromGoogleDict;
                }
            } else {
                log.warn(word + " is not in the GoogleDict");
                return new Dictionary(word, "Definition not found");
            }
        }
    }

    public Dictionary getFallbackDefinition(String word) {
        return new Dictionary(word, "Definition not found");
    }

    private void saveNewDefinition(Dictionary newDefinition) {
        wordCacheService.saveDefinitionInWordCache(newDefinition);
    }

    public DefaultResponse getRootEndpoint() {
        return new DefaultResponse("No words given");
    }
}
