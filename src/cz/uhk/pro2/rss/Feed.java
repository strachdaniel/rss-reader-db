package cz.uhk.pro2.rss;

import java.util.ArrayList;
import java.util.List;

public class Feed {
    private String title;
    private String link;
    private String description;
    private String language;
    private List<Article> articles = new ArrayList<>();

    public Feed(String title, String link, String description, String language) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.language = language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) { this.title = title; }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Article> getArticles() {
        return articles;
    }

    @Override
    public String toString() {
    return title + "\n" + link + "\n" + description + "\n" + language;
    }

    public void articleRead(int index){
        articles.get(index).setRead(true);
    };
}

