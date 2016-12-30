package ru.web.beans;

import ru.web.db.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AuthorList {

    private ArrayList<Author> authorList = new ArrayList<>();
    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    /*
    метод, возвращающий список авторов,
    заполненный информацией из базы данных
     */
    private ArrayList<Author> getAuthors() {

        try {
            Database database = new Database();
            conn = database.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from author order by fio");
            while (rs.next()) {
                Author author = new Author();
                author.setId(rs.getLong("id"));
                author.setName(rs.getString("fio"));
                authorList.add(author);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    return authorList;
    }

    public ArrayList<Author> getAuthorList() {
        if(authorList.isEmpty()) {
            return getAuthors();
        } else {
            return authorList;
        }
    }

}
