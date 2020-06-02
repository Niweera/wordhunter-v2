package gq.niweera.wordhunterapi.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import gq.niweera.wordhunterapi.model.Dictionary;
import gq.niweera.wordhunterapi.proxy.WordHoundProxy;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordHoundService {

    private final WordHoundProxy wordHoundProxy;
    private final WebClient.Builder webClientBuilder;

    @Autowired
    public WordHoundService(WordHoundProxy wordHoundProxy, WebClient.Builder webClientBuilder) {
        this.wordHoundProxy = wordHoundProxy;
        this.webClientBuilder = webClientBuilder;
    }

    @HystrixCommand(fallbackMethod = "getFallbackDefinitions",
            commandKey = "wordHoundServiceCommand",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "100"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000")
            }, threadPoolKey = "wordHoundServicePool",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "20"),
                    @HystrixProperty(name = "maxQueueSize", value = "10")
            })
    public Flux<Dictionary> getDefinitions(@NotNull List<String> anagramsList) {
        try {
            List<Mono<Dictionary>> dictionaryList = anagramsList.stream().map(this::getDefinitionFromWordHound).collect(Collectors.toList());
            return Flux.mergeSequential(dictionaryList);
        } catch (Exception ignored) {
            return null;
        }
    }

    private Mono<Dictionary> getDefinitionFromWordHound(String word) {
        return webClientBuilder.build().get().uri("https://wordhound.herokuapp.com/definition/" + word).retrieve().bodyToMono(Dictionary.class);
    }

    public Flux<Dictionary> getFallbackDefinitions(@NotNull List<String> anagramsList) {
        return null;
    }
}
