package cz.uhk.pro2.rss;

public class Article {
    private String id;
    private double read;
    private String url;
    private String title;
    private String descriptio;

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

    public double getRead() {
        return read;
    }

    public void setRead(double read) {
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
        return descriptio;
    }

    public void setDescriptio(String descriptio) {
        this.descriptio = descriptio;
    }

    @Override
    public String toString() {
        return title + "\n" + descriptio + "\n" + url + "\n" + id + "\n" + read;
    }

}
