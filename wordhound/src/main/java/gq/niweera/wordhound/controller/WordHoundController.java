package gq.niweera.wordhound.controller;

import gq.niweera.wordhound.model.DefaultResponse;
import gq.niweera.wordhound.model.Dictionary;
import gq.niweera.wordhound.service.WordHoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class WordHoundController {

    private final WordHoundService wordHoundService;

    @Autowired
    public WordHoundController(WordHoundService wordHoundService) {
        this.wordHoundService = wordHoundService;
    }

    @GetMapping("/")
    public DefaultResponse getRootEndpoint() {
        try {
            return wordHoundService.getRootEndpoint();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @GetMapping("/definition/{word}")
    public Dictionary getDefinition(@PathVariable("word") String word) {
        try {
            return wordHoundService.getDefinition(word);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

}
