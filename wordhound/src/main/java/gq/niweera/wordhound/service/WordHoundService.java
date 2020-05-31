package gq.niweera.wordhound.service;

import gq.niweera.wordhound.model.DefaultResponse;
import gq.niweera.wordhound.model.Dictionary;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class WordHoundService {
    private final DictionaryRepository dictionaryRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public WordHoundService(DictionaryRepository dictionaryRepository, RestTemplate restTemplate) {
        this.dictionaryRepository = dictionaryRepository;
        this.restTemplate = restTemplate;
    }

    @Cacheable("wordhound")
    public Dictionary getDefinition(String word) {
        Dictionary definitionFromDB = dictionaryRepository.findOneByWord(word);
        if (definitionFromDB != null) {
            return definitionFromDB;
        } else {
            Dictionary definitionFromGoogleDict = getFromGoogleDict(word);
            if (definitionFromGoogleDict != null) {
                saveNewDefinition(definitionFromGoogleDict);
                return definitionFromGoogleDict;
            } else {
                return new Dictionary("not-found-error", "Definition not found");
            }
        }
    }

    private @Nullable Dictionary getFromGoogleDict(String word) {
        Dictionary definition = restTemplate.getForObject("http://googledict/" + word, Dictionary.class);
        if (definition != null && !definition.getWord().equals("not-found-error")) {
            return definition;
        } else {
            return null;
        }
    }

    private void saveNewDefinition(Dictionary newDefinition) {
        dictionaryRepository.save(newDefinition);
    }

    public DefaultResponse getRootEndpoint() {
        return new DefaultResponse("No words given", "http://wordhound/{word}", "Provide the word to get the definition");
    }
}
