package gq.niweera.wordhound.service;

import gq.niweera.wordhound.model.Dictionary;
import gq.niweera.wordhound.proxy.GoogleDictProxy;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GoogleDictService {
    private final GoogleDictProxy googleDictProxy;

    @Autowired
    public GoogleDictService(GoogleDictProxy googleDictProxy) {
        this.googleDictProxy = googleDictProxy;
    }

    public @Nullable Dictionary getFromGoogleDict(String word) {
        Dictionary definition = googleDictProxy.getGoogleDictDefinition(word);
        if (definition != null && !definition.getWord().equals("not-found-error")) {
            return definition;
        } else {
            return null;
        }
    }
}
