package gq.niweera.wordhound.proxy;

import gq.niweera.wordhound.model.Dictionary;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(value = "googledict", url = "https://googledict.herokuapp.com")
public interface GoogleDictProxy {

    @GetMapping("/definition/{word}")
    Dictionary getGoogleDictDefinition(@PathVariable("word") String word);

}
