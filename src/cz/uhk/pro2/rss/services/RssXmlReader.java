package cz.uhk.pro2.rss.services;

import cz.uhk.pro2.rss.Feed;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.List;

public class RssXmlReader {


    /**
     * Nacte RSS feed (XML) z dane URL
     * @param url adresa RSS feedu
     * @return instance Feed včetně navazanych clanku (Article)
     * @throws RuntimeException v případě problémů při čtení feedu
     */
    public Feed readFeed(URL url) {
        try {

            SAXReader sax = new SAXReader();
            Document doc = sax.read(url);
            Element el = doc.getRootElement().element("channel");
            String title = el.elementText("title");
            String description = el.elementText("description");
            String link = el.elementText("link");
            String language = el.elementText("language");
            Feed f = new Feed(title, link,description,language);
            return f;
        } catch (DocumentException e) {
            throw new RuntimeException("Error reading RSS feed", e);
        }

    }
}
