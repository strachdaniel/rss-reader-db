package cz.uhk.pro2.rss.services;

import cz.uhk.pro2.rss.Article;
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
            Element channel = doc.getRootElement().element("channel");
            String title = channel.elementText("title");
            String description = channel.elementText("description");
            String link = channel.elementText("link");
            String language = channel.elementText("language");
            Feed f = new Feed(title, link,description,language);
            List<Element> items = channel.elements("item");

            for (Element item : items){
                String itemTitle = item.elementText("title");
                String itemDescription = item.elementText("description");
                String itemLink = item.elementText("link");
                String itemId = item.elementText("guid");

                Article article = new Article(itemId,itemLink,itemTitle);
                article.setDescription(itemDescription);
                f.getArticles().add(article);


            }
            return f;


        } catch (DocumentException e) {
            throw new RuntimeException("Error reading RSS feed", e);
        }

    }
}
