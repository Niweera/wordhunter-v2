package gq.niweera.enygma.controller;

import gq.niweera.enygma.model.Anagram;
import gq.niweera.enygma.service.AnagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnagramController {
    private final AnagramService anagramService;

    @Autowired
    public AnagramController(AnagramService anagramService) {
        this.anagramService = anagramService;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{letters}")
    public Anagram getAnagrams(@PathVariable("letters") String letters) {
        return anagramService.getAnagrams(letters);
    }
}
