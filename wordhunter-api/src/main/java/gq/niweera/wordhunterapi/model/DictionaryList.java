package gq.niweera.wordhunterapi.model;

import java.io.Serializable;
import java.util.List;

public class DictionaryList implements Serializable {
    private static final long serialVersionUID = 7256526077883281623L;

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
