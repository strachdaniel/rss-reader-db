package cz.uhk.pro2.rss.services;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class XmlUserSettingsStorage implements UserSettingsStorage{

    private String filename;

    public XmlUserSettingsStorage(String filename) {
        this.filename = filename;
    }

    @Override
    public List<String> loadFeedUrls() {
        return null;
        //TODO domaci ukol
    }

    @Override
    public void saveFeedUrls(List<String> feedUrls) {
        Document document = DocumentHelper.createDocument();
        Element root = document.addElement("rssreader");
        for (String url: feedUrls) {
            root.addElement("feedUrl").addText(url);
        }
        try (FileWriter out = new FileWriter(filename)) {
            document.write(out);
        } catch (IOException e){
            throw new RuntimeException("Error while saving feeds", e);
        }

    }
    }
