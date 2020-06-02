package gq.niweera.wordhunterapi.controller;

import gq.niweera.wordhunterapi.model.DefaultResponse;
import gq.niweera.wordhunterapi.model.Dictionary;
import gq.niweera.wordhunterapi.service.WordHunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;

@RestController
public class WordHunterController {
    private final WordHunterService wordHunterService;

    @Autowired
    public WordHunterController(WordHunterService wordHunterService) {
        this.wordHunterService = wordHunterService;
    }

    @GetMapping("/")
    public DefaultResponse getRootEndpoint() {
        try {
            return wordHunterService.getRootEndpoint();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @GetMapping("/anagrams/{letters}")
    public Flux<Dictionary> getWordsWithDefinitions(@PathVariable String letters) {
        try {
            return wordHunterService.getWordsWithDefinitions(letters);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }
}
