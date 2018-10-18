package cz.uhk.pro2.rss;

import cz.uhk.pro2.rss.services.RssXmlReader;

import java.net.MalformedURLException;
import java.net.URL;

public class RsReaderApp {
    public static void main(String[] args) {
        try {
            RssXmlReader r = new RssXmlReader();
            Feed f = r.readFeed(new URL("https://www.novinky.cz/rss2/"));
            System.out.println(f);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
