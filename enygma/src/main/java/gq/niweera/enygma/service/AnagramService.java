package gq.niweera.enygma.service;

import gq.niweera.enygma.model.Anagram;
import gq.niweera.enygma.scraper.Scraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnagramService {
    private final Scraper scraper;

    @Autowired
    public AnagramService(Scraper scraper) {
        this.scraper = scraper;
    }

    public Anagram getAnagrams(String letters) {
        return scraper.scrapeSite(letters);
    }
}
