package cz.uhk.pro2.rss.services;

import java.util.List;

public interface UserSettingsStorage {
    /**
     * Nacte z uloziste seznam URL feedu, ktere uzivatel odebira.
     * @return
     */
    List<String> loadFeedUrls();

    /**
     * Ulozi do uloziste seznam URL feedu, ktere uzivatel odebira.
     * @param feedUrls
     */
    void saveFeedUrls(List<String> feedUrls);
}
