package gq.niweera.wordhunterapi.service;

import gq.niweera.wordhunterapi.model.DefaultResponse;
import gq.niweera.wordhunterapi.model.DictionaryList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class WordHunterService {
    private final WordHoundService wordHoundService;
    private final EnygmaService enygmaService;

    @Autowired
    public WordHunterService(WordHoundService wordHoundService, EnygmaService enygmaService) {
        this.wordHoundService = wordHoundService;
        this.enygmaService = enygmaService;
    }

    @Cacheable(value = "wordhunterapi", key = "#letters")
    public DictionaryList getWordsWithDefinitions(String letters) {
        List<String> anagramsList = enygmaService.getAnagramsList(letters);

        if (!anagramsList.isEmpty()) {
            return new DictionaryList(wordHoundService.getDefinitions(anagramsList));
        } else {
            return new DictionaryList(Collections.emptyList());
        }
    }

    public DefaultResponse getRootEndpoint() {
        return new DefaultResponse("No characters are given!", "http://wordhunter-api/anagrams/{letters}", "Just enter the letters. No need for spaces, commas etc.");
    }
}
