package gq.niweera.wordhound.service;

import gq.niweera.wordhound.model.DefaultResponse;
import gq.niweera.wordhound.model.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class WordHoundService {
    private final DictionaryRepository dictionaryRepository;
    private final GoogleDictService googleDictService;


    @Autowired
    public WordHoundService(DictionaryRepository dictionaryRepository, GoogleDictService googleDictService) {
        this.dictionaryRepository = dictionaryRepository;
        this.googleDictService = googleDictService;
    }

    @Cacheable(value = "wordhound", key = "#word")
    public Dictionary getDefinition(String word) {
        Dictionary definitionFromDB = dictionaryRepository.findOneByWord(word);
        if (definitionFromDB != null) {
            return definitionFromDB;
        } else {
            Dictionary definitionFromGoogleDict = googleDictService.getFromGoogleDict(word);
            if (definitionFromGoogleDict != null) {
                saveNewDefinition(definitionFromGoogleDict);
                return definitionFromGoogleDict;
            } else {
                return new Dictionary("not-found-error", "Definition not found");
            }
        }
    }

    private void saveNewDefinition(Dictionary newDefinition) {
        dictionaryRepository.save(newDefinition);
    }

    public DefaultResponse getRootEndpoint() {
        return new DefaultResponse("No words given", "http://wordhound/{word}", "Provide the word to get the definition");
    }
}
