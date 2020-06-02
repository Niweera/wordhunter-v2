package gq.niweera.enygma.scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import gq.niweera.enygma.model.Anagram;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class Scraper {
    private static final String baseUrl = "https://www.wordplays.com/anagram-solver/";

    public Scraper() {

    }

    @Cacheable(value = "anagrams", key = "#letters")
    public Anagram scrapeSite(String letters) {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.getOptions().setPrintContentOnFailingStatusCode(false);

        final String url = baseUrl.concat(letters);

        try {
            HtmlPage page = client.getPage(url);
            List<HtmlElement> results = page.getByXPath("//*[(@id = \"wordwrap\")]//a");
            List<String> anagrams = new ArrayList<>();
            if (!results.isEmpty()) {
                results.forEach(item -> anagrams.add(item.asText()));
            }
            log.info(anagrams.toString());
            return new Anagram(anagrams);
        } catch (Exception ignored) {
            return new Anagram(new ArrayList<>());
        }
    }
}
