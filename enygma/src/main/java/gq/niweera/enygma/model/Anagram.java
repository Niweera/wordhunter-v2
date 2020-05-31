package gq.niweera.enygma.model;

import java.io.Serializable;
import java.util.List;

public class Anagram implements Serializable {
    private static final long serialVersionUID = 7156526077883281623L;
    private List<String> anagrams;

    public Anagram() {
    }

    public Anagram(List<String> anagrams) {
        this.anagrams = anagrams;
    }

    public List<String> getAnagrams() {
        return anagrams;
    }

    public void setAnagrams(List<String> anagrams) {
        this.anagrams = anagrams;
    }
}
