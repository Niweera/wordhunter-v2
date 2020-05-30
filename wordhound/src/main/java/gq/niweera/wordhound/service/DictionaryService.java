package gq.niweera.wordhound.service;

import gq.niweera.wordhound.model.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class DictionaryService {
    private final DictionaryRepository dictionaryRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public DictionaryService(DictionaryRepository dictionaryRepository, RestTemplate restTemplate) {
        this.dictionaryRepository = dictionaryRepository;
        this.restTemplate = restTemplate;
    }

    public Dictionary getDefinition(String word) {
        Dictionary definitionFromDB = dictionaryRepository.findOneByWord(word);
        if (definitionFromDB != null) {
            return definitionFromDB;
        } else {
            Dictionary definitionFromGoogleDict = this.getFromGoogleDict(word);
            if (definitionFromGoogleDict != null) {
                this.saveNewDefinition(definitionFromGoogleDict);
                return definitionFromGoogleDict;
            } else {
                return new Dictionary("not-found-error", "Definition not found");
            }
        }
    }

    private Dictionary getFromGoogleDict(String word) {
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
}
