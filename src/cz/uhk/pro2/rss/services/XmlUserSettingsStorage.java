package cz.uhk.pro2.rss.services;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XmlUserSettingsStorage implements UserSettingsStorage {

    private String filename;

    public XmlUserSettingsStorage(String filename) {
        this.filename = filename;
    }

    @Override
    public List<String> loadFeedUrls() {
        SAXReader saxReader = new SAXReader();
        try {
            Document doc = saxReader.read(filename);
            Element channel = doc.getRootElement();
            List<Element> urls = channel.elements("feedUrl");
            List<String> out = new ArrayList<>();
            for (Element url : urls) {
                String textUrl = url.getText();
                out.add(textUrl);
            }
            return out;
        } catch (DocumentException e) {
            throw new RuntimeException("Error while loading feeds");
        }

    }

    @Override
    public void saveFeedUrls(List<String> feedUrls) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("rssreader");
        for (String url : feedUrls) {
            root.addElement("feedUrl").addText(url);
        }
        try (FileWriter out = new FileWriter(filename)) {
            document.write(out);
        } catch (IOException e) {
            throw new RuntimeException("Error while saving feeds", e);
        }

    }
}
