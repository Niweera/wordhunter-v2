package gq.niweera.enygma.controller;

import gq.niweera.enygma.model.Anagram;
import gq.niweera.enygma.model.DefaultResponse;
import gq.niweera.enygma.service.AnagramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AnagramController {
    private final AnagramService anagramService;

    @Autowired
    public AnagramController(AnagramService anagramService) {
        this.anagramService = anagramService;
    }

    @GetMapping("/")
    public DefaultResponse getRootEndpoint() {
        try {
            return anagramService.getRootEndpoint();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/anagrams/{letters}")
    public Anagram getAnagrams(@PathVariable("letters") String letters) {
        try {
            return anagramService.getAnagrams(letters);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
    }
}
