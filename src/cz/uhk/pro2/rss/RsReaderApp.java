package cz.uhk.pro2.rss;
import cz.uhk.pro2.rss.Article;
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

public class RsReaderApp extends JFrame {
    List<String> feddUrls = new ArrayList<>();
    private Feed f;
    TableModel model;
    JTable table;
    ArrayList<Article> articles;

    public RsReaderApp() {
        feddUrls.add("https://www.novinky.cz/rss2/");
        feddUrls.add("https://ihned.cz/?m=rss");

        articles = new ArrayList<>();
        for (String feedUrl : feddUrls) {
            try {
                RssXmlReader r = new RssXmlReader();
                f = r.readFeed(new URL(feedUrl));
                articles.addAll(f.getArticles());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        model = new TableModel(articles);
        table = new JTable(model);
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
                new RsReaderApp();
            }
        });
    }
}
