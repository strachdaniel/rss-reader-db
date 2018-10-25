package cz.uhk.pro2.rss;

public class Article {
    private String id;
    private boolean read;
    private String url;
    private String title;
    private String description;

    public Article(String id, String url, String title) {
        this.id = id;
        this.url = url;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean getRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescriptio() {
        return description;
    }

    public void setDescription(String descriptio) {
        this.description = descriptio;
    }

    @Override
    public String toString() {
        return title + "\n" + description + "\n" + url + "\n" + id + "\n" + read;
    }

}
