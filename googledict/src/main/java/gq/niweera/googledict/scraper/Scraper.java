package gq.niweera.googledict.scraper;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import gq.niweera.googledict.model.Dictionary;
import org.springframework.stereotype.Service;

@Service
public class Scraper {
    private static final String baseUrl = "https://www.lexico.com/en/definition/";

    public Scraper() {

    }

    public Dictionary scrapeSite(String word) {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.getOptions().setPrintContentOnFailingStatusCode(false);

        final String url = baseUrl.concat(word);

        try {
            HtmlPage page = client.getPage(url);
            HtmlElement result = page.getFirstByXPath("//p//*[contains(concat( \" \", @class, \" \" ), concat( \" \", \"ind\", \" \" ))]");

            if (result != null) {
                return new Dictionary(word, result.asText());
            } else {
                return new Dictionary("not-found-error", "Definition not found");
            }

        } catch (Exception ignored) {
            return new Dictionary("not-found-error", "Definition not found");
        }
    }

}
