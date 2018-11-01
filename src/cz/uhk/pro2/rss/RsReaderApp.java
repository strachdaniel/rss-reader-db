package cz.uhk.pro2.rss;
import cz.uhk.pro2.rss.Article;
import cz.uhk.pro2.rss.services.RssXmlReader;
import cz.uhk.pro2.rss.tableModel.TableModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

public class RsReaderApp extends JFrame {
    private Feed f;

    public RsReaderApp() {
        try {
            RssXmlReader r = new RssXmlReader();
            f = r.readFeed(new URL("https://www.novinky.cz/rss2/"));
            System.out.println(f);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        TableModel model = new TableModel(f.getArticles());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("RSS READER");

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2){
                    int selectedIndex = table.getSelectedRow();
                    Article selected = f.getArticles().get(selectedIndex);
                    System.out.println(selected.getUrl());
                    try {
                        Desktop.getDesktop().browse(new URI(selected.getUrl()));
                        selected.setRead(true);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (URISyntaxException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        setVisible(true);
        pack();
        model.setChangeHandler(()->model.fireTableDataChanged());


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
