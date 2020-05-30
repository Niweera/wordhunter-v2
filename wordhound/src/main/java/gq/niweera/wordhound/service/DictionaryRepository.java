package gq.niweera.wordhound.service;

import gq.niweera.wordhound.model.Dictionary;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface DictionaryRepository extends MongoRepository<Dictionary, String> {
    Dictionary findOneByWord(String word);
}
