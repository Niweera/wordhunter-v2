package gq.niweera.wordhound.service;

import gq.niweera.wordhound.model.DefaultResponse;
import gq.niweera.wordhound.model.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;


@Service
public class WordHoundService {
    private final GoogleDictService googleDictService;
    private final WordCacheService wordCacheService;

    @Autowired
    public WordHoundService(GoogleDictService googleDictService, WordCacheService wordCacheService) {
        this.googleDictService = googleDictService;
        this.wordCacheService = wordCacheService;
    }

    @Cacheable(value = "wordhound", key = "#word")
    public Dictionary getDefinition(String word) {
        Dictionary definitionFromDB;
        try {
            definitionFromDB = wordCacheService.getDefinitionFormWordCache(word);
        } catch (Exception ignored) {
            definitionFromDB = null;
        }
        if (definitionFromDB != null) {
            return definitionFromDB;
        } else {
            Dictionary definitionFromGoogleDict = googleDictService.getFromGoogleDict(word);
            if (definitionFromGoogleDict != null) {
                try {
                    saveNewDefinition(definitionFromGoogleDict);
                    return definitionFromGoogleDict;
                } catch (Exception ignored) {
                    return definitionFromGoogleDict;
                }
            } else {
                return new Dictionary(word, "Definition not found");
            }
        }
    }

    private void saveNewDefinition(Dictionary newDefinition) {
        wordCacheService.saveDefinitionInWordCache(newDefinition);
    }

    public DefaultResponse getRootEndpoint() {
        return new DefaultResponse("No words given");
    }
}
