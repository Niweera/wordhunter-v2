package gq.niweera.wordhunterapi.proxy;

import gq.niweera.wordhunterapi.model.Dictionary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Service
@FeignClient(value = "word-hound", url = "http://localhost:8002")
public interface WordHoundProxy {
    @GetMapping("/definition/{word}")
    Dictionary getDefinitionFromWordHound(@PathVariable("word") String word);
}
