package gq.niweera.wordhound.service;

import gq.niweera.wordhound.model.Dictionary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordCacheService {
    private final DictionaryRepository dictionaryRepository;

    @Autowired
    public WordCacheService(DictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }

    public void saveDefinitionInWordCache(Dictionary newDefinition) {
        dictionaryRepository.save(newDefinition);
    }

    public Dictionary getDefinitionFormWordCache(String word) {
        return dictionaryRepository.findOneByWord(word);
    }
}
