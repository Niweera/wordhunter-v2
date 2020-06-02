package gq.niweera.wordhunterapi.service;

import gq.niweera.wordhunterapi.model.DefaultResponse;
import gq.niweera.wordhunterapi.model.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

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

    public Flux<Dictionary> getWordsWithDefinitions(String letters) {
        List<String> anagramsList = enygmaService.getAnagramsList(letters);
        if (!anagramsList.isEmpty()) {
            return wordHoundService.getDefinitions(anagramsList);
        } else {
            return null;
        }
    }

    public DefaultResponse getRootEndpoint() {
        return new DefaultResponse("No characters are given!");
    }
}
