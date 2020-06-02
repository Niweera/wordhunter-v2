package gq.niweera.wordhound.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import gq.niweera.wordhound.model.DefaultResponse;
import gq.niweera.wordhound.model.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
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
            System.out.println(definitionFromDB.getWord() + ": " + definitionFromDB.getDefinition());
            return definitionFromDB;
        } else {
            Dictionary definitionFromGoogleDict = googleDictService.getFromGoogleDict(word);
            if (definitionFromGoogleDict != null) {
                System.out.println(definitionFromGoogleDict.getWord() + ": " + definitionFromGoogleDict.getDefinition());
                try {
                    saveNewDefinition(definitionFromGoogleDict);
                    return definitionFromGoogleDict;
                } catch (Exception ignored) {
                    return definitionFromGoogleDict;
                }
            } else {
                System.out.println(word + ": Definition not found");
                return new Dictionary(word, "Definition not found");
            }
        }
    }

    public Dictionary getFallbackDefinition(String word) {
        System.out.println(word + ": Definition not found");
        return new Dictionary(word, "Definition not found");
    }

    private void saveNewDefinition(Dictionary newDefinition) {
        wordCacheService.saveDefinitionInWordCache(newDefinition);
    }

    public DefaultResponse getRootEndpoint() {
        return new DefaultResponse("No words given");
    }
}
