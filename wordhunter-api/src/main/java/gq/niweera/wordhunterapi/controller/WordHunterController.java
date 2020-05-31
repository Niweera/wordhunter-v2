package gq.niweera.wordhunterapi.controller;

import gq.niweera.wordhunterapi.model.DefaultResponse;
import gq.niweera.wordhunterapi.model.DictionaryList;
import gq.niweera.wordhunterapi.service.WordHunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordHunterController {
    private final WordHunterService wordHunterService;

    @Autowired
    public WordHunterController(WordHunterService wordHunterService) {
        this.wordHunterService = wordHunterService;
    }

    @GetMapping("/")
    public DefaultResponse getRootEndpoint() {
        return wordHunterService.getRootEndpoint();
    }

    @GetMapping("/{letters}")
    public DictionaryList getWordsWithDefinitions(@PathVariable String letters) {
        return wordHunterService.getWordsWithDefinitions(letters);
    }
}
