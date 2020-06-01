package gq.niweera.wordhunterapi.proxy;

import gq.niweera.wordhunterapi.model.Anagram;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "enygma")
@RibbonClient(name = "enygma")
public interface EnygmaProxy {
    @GetMapping("/anagrams/{letters}")
    Anagram getAnagramsFromEnygma(@PathVariable("letters") String letters);
}
