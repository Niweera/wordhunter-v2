package gq.niweera.wordhound.proxy;

import gq.niweera.wordhound.model.Dictionary;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "googledict")
@RibbonClient(name = "googledict")
public interface GoogleDictProxy {

    @GetMapping("/{word}")
    Dictionary getFGoogleDictDefinition(@PathVariable("word") String word);

}
