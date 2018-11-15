package cz.uhk.pro2.rss.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class DbUserSettingsStorage implements UserSettingsStorage {
    @Override
    public List<String> loadFeedUrls() {
        return null;
    }

    @Override
    public void saveFeedUrls(List<String> feedUrls) {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://192.168.99.100/rssreader", "root", "my-secret-pw")) {
            con.prepareStatement("INSERT INTO feed_urls VALUES(null, ?)");
        } catch (SQLException e){
            // intentionally swallowed exception -> neni tento pripad
            throw new RuntimeException("Error reading feeds from DB", e);
        }
    }
}
