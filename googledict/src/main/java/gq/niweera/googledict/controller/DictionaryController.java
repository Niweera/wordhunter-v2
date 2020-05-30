package gq.niweera.googledict.controller;

import gq.niweera.googledict.model.Dictionary;
import gq.niweera.googledict.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{word}")
    public Dictionary getDictionaryEntry(@PathVariable("word") String word) {
        return dictionaryService.getDictionaryEntry(word);
    }

}
