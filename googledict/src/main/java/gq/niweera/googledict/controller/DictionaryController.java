package gq.niweera.googledict.controller;

import gq.niweera.googledict.model.DefaultResponse;
import gq.niweera.googledict.model.Dictionary;
import gq.niweera.googledict.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DictionaryController {
    private final DictionaryService dictionaryService;

    @Autowired
    public DictionaryController(DictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }

    @GetMapping("/")
    public DefaultResponse getRootEndpoint() {
        try {
            return dictionaryService.getRootEndpoint();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/definition/{word}")
    public Dictionary getDictionaryEntry(@PathVariable("word") String word) {
        try {
            return dictionaryService.getDictionaryEntry(word);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

}
