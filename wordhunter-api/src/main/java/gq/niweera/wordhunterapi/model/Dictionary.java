package gq.niweera.wordhunterapi.model;

import java.io.Serializable;

public class Dictionary implements Serializable {
    private static final long serialVersionUID = 7256526436883281623L;
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
