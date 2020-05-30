package gq.niweera.enygma.model;

import java.util.List;

public class Anagram {
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
