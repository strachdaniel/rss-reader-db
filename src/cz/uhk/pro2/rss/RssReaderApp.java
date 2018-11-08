package cz.uhk.pro2.rss;
import cz.uhk.pro2.rss.services.RssXmlReader;
import cz.uhk.pro2.rss.tableModel.TableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RssReaderApp extends JFrame {
    List<String> feedUrl = new ArrayList<>();
    private Feed f;
    TableModel model;
    JTable table;
    ArrayList<Article> articles = new ArrayList<>();
    JComboBox<String> combo;
    RssXmlReader r = new RssXmlReader();
    JButton btnRemoveFeed;

    public RssReaderApp() {
        feedUrl.add("https://www.novinky.cz/rss2/");
        feedUrl.add("https://ihned.cz/?m=rss");

        model = new TableModel(articles);
        table = new JTable(model);
        loadArticles();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("RSS READER");

        add(new JScrollPane(table), BorderLayout.CENTER);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    openSelectedArticle();
                    e.consume(); // aby se kurzor v tabulce neposunul o radek niz
                }
            }
        });

        JToolBar panel = new JToolBar();

        combo = new JComboBox<>(feedUrl.toArray(new String[0]));
        panel.add(combo);

        btnRemoveFeed = new JButton("Smazat feed");
        btnRemoveFeed.addActionListener(e->removeFeed());

        JButton btnAddFeed = new JButton("Přidat feed");
        btnAddFeed.addActionListener(e->addFeed());

        panel.add(btnAddFeed);
        panel.add(btnRemoveFeed);
        add(panel,BorderLayout.NORTH);

        table.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    openSelectedArticle();
                    e.consume();
                }
            }
        });
        setVisible(true);
        pack();
        model.setChangeHandler(()->model.fireTableDataChanged());


    }

    public void removeFeed(){
        int index = combo.getSelectedIndex();
        if (index >= 0) {
            feedUrl.remove(index);
            combo.removeItemAt(index);
            loadArticles();
        } // ?
        checkRemoveFeedEnabled();
    }

    private void checkRemoveFeedEnabled() {
            btnRemoveFeed.setEnabled(feedUrl.size() > 0);
    }

    public void loadArticles(){
        articles.clear();
        for (String feedUrl : feedUrl) {
            try {
                f = r.readFeed(new URL(feedUrl));
                articles.addAll(f.getArticles());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            model.fireTableDataChanged();
        }
    }

    public void addFeed(){
        String url = JOptionPane.showInputDialog("Zadej URL RSS feedu");
        try {
            f = r.readFeed(new URL(url)); //zkusmo nacteme feed, jestli neni chyba v url a podobne
            feedUrl.add(url);
            combo.addItem(url);
            loadArticles();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,"Chybně zadaná adresa Feedu. Feed se nepodararilo nacist");
        }
        checkRemoveFeedEnabled();
    }

    public void openSelectedArticle(){
        int selectedIndex = table.getSelectedRow();
        Article selected = articles.get(selectedIndex);
        System.out.println(selected.getUrl());
        try {
            Desktop.getDesktop().browse(new URI(selected.getUrl()));
            selected.setRead(true);
            model.fireTableRowsUpdated(selectedIndex,selectedIndex);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    public static void main(String[] args) {


        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RssReaderApp();
            }
        });
    }
}