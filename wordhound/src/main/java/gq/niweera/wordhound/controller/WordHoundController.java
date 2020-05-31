package gq.niweera.wordhound.controller;

import gq.niweera.wordhound.model.DefaultResponse;
import gq.niweera.wordhound.model.Dictionary;
import gq.niweera.wordhound.service.WordHoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class WordHoundController {

    private final WordHoundService wordHoundService;

    @Autowired
    public WordHoundController(WordHoundService wordHoundService) {
        this.wordHoundService = wordHoundService;
    }

    @GetMapping("/")
    public DefaultResponse getRootEndpoint() {
        return wordHoundService.getRootEndpoint();
    }

    @GetMapping("/{word}")
    public Dictionary getDefinition(@PathVariable("word") String word) {
        return wordHoundService.getDefinition(word);
    }

}
