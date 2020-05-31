package gq.niweera.wordhound.model;


import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "dictionary")
public class Dictionary implements Serializable {
    private static final long serialVersionUID = 9156526077883281623L;
    @Indexed(unique = true)
    private String word;
    private String definition;

    public Dictionary() {

    }

    public Dictionary(String word, String definition) {
        this.word = word;
        this.definition = definition;
    }


    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
}
