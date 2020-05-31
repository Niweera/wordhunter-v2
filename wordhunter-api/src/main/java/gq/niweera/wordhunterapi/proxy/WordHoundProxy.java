package gq.niweera.wordhunterapi.proxy;

import gq.niweera.wordhunterapi.model.Dictionary;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Service
@FeignClient(name = "wordhound")
@RibbonClient(name = "wordhound")
public interface WordHoundProxy {
    @GetMapping("/{word}")
    Dictionary getDefinitionFromWordHound(@PathVariable("word") String word);
}
