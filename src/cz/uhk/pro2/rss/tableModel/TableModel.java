package cz.uhk.pro2.rss.tableModel;

import cz.uhk.pro2.rss.Article;
import cz.uhk.pro2.rss.Feed;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModel extends AbstractTableModel {

    private List<Article> feeds;
    private Runnable changeHandler;

    public TableModel(List<Article> feeds) {
        this.feeds = feeds;
    }

    @Override
    public int getRowCount() {
        return feeds.size();
    }

    @Override
    public int getColumnCount() {
        return 6;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Article article = feeds.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rowIndex + 1;
            case 1:
                return article.getId();
            case 2:
                return article.getUrl();
            case 3:
                return article.getTitle();
            case 4:
                return article.getDescriptio();
            case 5:
                return article.getRead();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {

            case 0:
                return "číslo";
            case 1:
                return "ID";
            case 2:
                return "URL";
            case 3:
                return "Titulek";
            case 4:
                return "Obsah";
            case 5:
                return "Přečteno";
        }
        return null;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            case 4:
                return String.class;
            case 5:
                return Boolean.class;


        }
        return Object.class;
    }

   @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex == 5);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        System.out.println(aValue + " " + rowIndex + " " + columnIndex);
        if ((boolean) aValue == true) {
            feeds.get(rowIndex).setRead(true);
        } else feeds.get(rowIndex).setRead(false);

    }

    public void setChangeHandler(Runnable changeHandler) {
        this.changeHandler = changeHandler;
    }
}
