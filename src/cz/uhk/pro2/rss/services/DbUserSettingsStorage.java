package cz.uhk.pro2.rss.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUserSettingsStorage implements UserSettingsStorage {
    @Override
    public List<String> loadFeedUrls() {
        try (Connection con = DriverManager.getConnection("jdbc:hsqldb:file:database.hsqldb", "root", "my-secret-pw")) {
            PreparedStatement select = con.prepareStatement("SELECT url FROM feed_urls");
            ResultSet res = select.executeQuery();
            ArrayList<String> feeds = new ArrayList<>();
            while(res.next()) {
                feeds.add(res.getString("url"));
            }
            return feeds;
        }catch (SQLException e){
            // intentionally swallowed exception -> neni tento pripad
            throw new RuntimeException("Error writing feeds to DB", e);
        }
    }

    @Override
    public void saveFeedUrls(List<String> feedUrls) {
//        try (Connection con = DriverManager.getConnection("jdbc:mysql://192.168.99.100/rssreader", "root", "my-secret-pw")) {
        try (Connection con = DriverManager.getConnection("jdbc:hsqldb:file:database.hsqldb", "root", "my-secret-pw")) {
           init(con);
            PreparedStatement insert = con.prepareStatement("INSERT INTO feed_urls (url) VALUES(?)");
            for (String url: feedUrls) {
                insert.setString(1, url);
                insert.executeUpdate();
            }
            dump(con);
        } catch (SQLException e){
            // intentionally swallowed exception -> neni tento pripad
            throw new RuntimeException("Error writing feeds to DB", e);
        }
    }

    private void init(Connection con) throws SQLException {
        PreparedStatement dropTable = con.prepareStatement("DROP TABLE IF EXISTS feed_urls");
        PreparedStatement createTable = con.prepareStatement("CREATE TABLE IF NOT EXISTS feed_urls (id INTEGER NOT NULL IDENTITY, url VARCHAR(1000) NOT NULL, PRIMARY KEY(id))");
        dropTable.execute();
        createTable.execute();
    }

    private void dump(Connection con) throws SQLException {
        PreparedStatement select = con.prepareStatement("SELECT id, url FROM feed_urls");
        ResultSet res = select.executeQuery();
        while (res.next()){
            System.out.println(res.getInt("id") + ": " + res.getString("url"));
        }
    }
}
