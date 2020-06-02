package gq.niweera.wordhunterapi.proxy;

import gq.niweera.wordhunterapi.model.Anagram;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(value = "enygma", url = "https://enygma.herokuapp.com")
public interface EnygmaProxy {
    @GetMapping("/anagrams/{letters}")
    Anagram getAnagramsFromEnygma(@PathVariable("letters") String letters);
}
