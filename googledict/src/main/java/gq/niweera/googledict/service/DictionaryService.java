package gq.niweera.googledict.service;


import gq.niweera.googledict.model.Dictionary;
import gq.niweera.googledict.scraper.Scraper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DictionaryService {

    private final Scraper scraper;

    @Autowired
    public DictionaryService(Scraper scraper) {
        this.scraper = scraper;
    }

    @Cacheable(value = "googledict", key = "#word")
    public Dictionary getDictionaryEntry(String word) {
        return scraper.scrapeSite(word);
    }

}
