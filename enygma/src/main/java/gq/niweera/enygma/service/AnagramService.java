package gq.niweera.enygma.service;

import gq.niweera.enygma.model.Anagram;
import gq.niweera.enygma.model.DefaultResponse;
import gq.niweera.enygma.scraper.Scraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class AnagramService {
    private final Scraper scraper;

    @Autowired
    public AnagramService(Scraper scraper) {
        this.scraper = scraper;
    }

    @Cacheable(value = "anagrams", key = "#letters")
    public Anagram getAnagrams(String letters) {
        return scraper.scrapeSite(letters);
    }

    public DefaultResponse getRootEndpoint() {
        return new DefaultResponse("No letters given", "http://enygma/anagrams/{letters}", "Provide the letters to get the anagrams");
    }
}
