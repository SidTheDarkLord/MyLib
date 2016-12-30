package ru.web.beans;

import ru.web.db.Database;
import ru.web.enums.SearchType;

import javax.swing.ImageIcon;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BookList {

    private ArrayList<Book> bookList = new ArrayList<>();
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;
    /*
    метод, возвращающий список книг,
    заполненный информацией из базы данных
     */
    private ArrayList<Book> getBooks(String str) {

        try {
            Database database = new Database();
            conn = database.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(str);
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setName(rs.getString("name"));
                book.setPageCount(rs.getInt("page_count"));
                book.setIsbn(rs.getString("isbn"));
                book.setGenre(rs.getString("genre"));
                book.setAuthor(rs.getString("author"));
                book.setPublishDate(rs.getInt("publish_year"));
                book.setPublisher(rs.getString("publisher"));
                book.setImage(rs.getBytes("image"));
                bookList.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null) {
                    rs.close();
                }
                if(stmt != null) {
                    stmt.close();
                }
                if(conn != null) {
                    conn.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
        return bookList;
    }

    /*
    метод, возвращающий все книги из базы данных
     */
    public ArrayList<Book> getAllBooks() {
        if(bookList.isEmpty()) {
            return getBooks("SELECT b.id, b.name, b.page_count, b.isbn, g.name as genre, a.fio as author, b.publish_year, " +
                    "p.name as publisher, b.image " +
                    "FROM book b " +
                    "INNER JOIN genre g ON b.genre_id = g.id " +
                    "INNER JOIN author a ON b.author_id = a.id " +
                    "INNER JOIN publisher p ON b.publisher_id = p.id " +
                    "ORDER BY b.name");
        } else {
            return bookList;
        }
    }

    /*
    метод, возвращающий книги данного жанра
     */
    public ArrayList<Book> getBooksByGenre(long id) {
        if(id == 0) {
            return getAllBooks();
        } else {
            return getBooks("SELECT b.id, b.name, b.page_count, b.isbn, g.name as genre, a.fio as author, b.publish_year, " +
                    "p.name as publisher, b.image " +
                    "FROM book b " +
                    "INNER JOIN genre g ON b.genre_id = g.id " +
                    "INNER JOIN author a ON b.author_id = a.id " +
                    "INNER JOIN publisher p ON b.publisher_id = p.id " +
                    "where g.id = " + id + " " +
                    "ORDER BY b.name");
        }
    }

    /*
    метод, возвращающий книги, начинающиеся с выбранной буквы
     */
    public ArrayList<Book> getBooksByLetter(String letter) {
        return getBooks("SELECT b.id, b.name, b.page_count, b.isbn, g.name as genre, a.fio as author, b.publish_year, " +
                "p.name as publisher, b.image " +
                "FROM book b " +
                "INNER JOIN genre g ON b.genre_id = g.id " +
                "INNER JOIN author a ON b.author_id = a.id " +
                "INNER JOIN publisher p ON b.publisher_id = p.id " +
                "WHERE substr(b.name,1,1) = '" + letter + "' " +
                "ORDER BY b.name");
    }

    /*
    метод, возвращающий книги согласно запроса в поисковой строке
     */
    public ArrayList<Book> getBooksBySearch(String searchStr, SearchType type) {
        StringBuilder sql = new StringBuilder("SELECT b.id, b.name, b.page_count, b.isbn, g.name as genre, a.fio as author, b.publish_year, " +
                "p.name as publisher, b.image " +
                "FROM book b " +
                "INNER JOIN genre g ON b.genre_id = g.id " +
                "INNER JOIN author a ON b.author_id = a.id " +
                "INNER JOIN publisher p ON b.publisher_id = p.id ");

        if(type == SearchType.AUTHOR) {
            sql.append("WHERE LOWER(a.fio) LIKE '%" + searchStr.toLowerCase() + "%' ORDER BY b.name ");
        } else if (type == SearchType.TYTLE) {
            sql.append("WHERE LOWER(b.name) LIKE '%" + searchStr.toLowerCase() + "%' ORDER BY b.name ");
        }
        sql.append("LIMIT 0,5");

        return getBooks(sql.toString());
    }
}
