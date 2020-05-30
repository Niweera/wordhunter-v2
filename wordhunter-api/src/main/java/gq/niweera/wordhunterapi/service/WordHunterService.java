package gq.niweera.wordhunterapi.service;

import gq.niweera.wordhunterapi.model.Anagram;
import gq.niweera.wordhunterapi.model.Dictionary;
import gq.niweera.wordhunterapi.model.DictionaryList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WordHunterService {

    private final RestTemplate restTemplate;

    @Autowired
    public WordHunterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public DictionaryList getWordsWithDefinitions(String letters) {
        Anagram anagrams = restTemplate.getForObject("http://enygma/" + letters, Anagram.class);
        List<String> anagramsList = anagrams != null ? anagrams.getAnagrams() : Collections.emptyList();
        if (!anagramsList.isEmpty()) {
            DictionaryList rawDefinitions = new DictionaryList(anagramsList.stream().map(word -> restTemplate.getForObject("http://wordhound/" + word, Dictionary.class)).collect(Collectors.toList()));

            return new DictionaryList(rawDefinitions.getDefinitions().stream().filter(item -> !item.getWord().equals("not-found-error")).collect(Collectors.toList()));
        } else {
            return new DictionaryList(Collections.emptyList());
        }
    }
}
