package gq.niweera.wordhunterapi.controller;

import gq.niweera.wordhunterapi.model.DictionaryList;
import gq.niweera.wordhunterapi.service.WordHunterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordHunterController {
    private final WordHunterService wordHunterService;

    @Autowired
    public WordHunterController(WordHunterService wordHunterService) {
        this.wordHunterService = wordHunterService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{letters}")
    public DictionaryList getWordsWithDefinitions(@PathVariable String letters) {
        return wordHunterService.getWordsWithDefinitions(letters);
    }

}
