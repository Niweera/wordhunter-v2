package gq.niweera.wordhunterapi.model;

import java.util.List;

public class DictionaryList {
    private List<Dictionary> definitions;

    public DictionaryList() {
    }

    public DictionaryList(List<Dictionary> definitions) {
        this.definitions = definitions;
    }

    public List<Dictionary> getDefinitions() {
        return definitions;
    }

    public void setDefinitions(List<Dictionary> definitions) {
        this.definitions = definitions;
    }
}
