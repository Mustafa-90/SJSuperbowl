package com.example.Superbowl;

import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class GenreRepository {

    String connstr = "jdbc:sqlserver://localhost;databasename=Superbowl2;user=dbadmin;password=123123";

    public List<Video> getGenreVideos(String genre) {
        List<Video> listOfVideo = new ArrayList<>();

        List<Video> listOfVideos = new ArrayList<>();
        String name = "", description = "", embeddedUrl = "", companyName = "";

        String sql = "SELECT Name, Description, embedded_URL, companyName\n" +
                "FROM Video\n" +
                "JOIN Company ON company_Id = Company.id\n" +
                "JOIN Genre on genre_Id = Genre.id\n" +
                "JOIN category on category_id = category.id\n" +
                "WHERE Genre.genre = ?";

        try (Connection conn = DriverManager.getConnection(connstr)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, genre);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                name = rs.getString(1);
                description = rs.getString(2);
                embeddedUrl = rs.getString(3);
                companyName = rs.getString(4);
                listOfVideos.add(new Video(name, description, companyName, embeddedUrl));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listOfVideos;
    }

}
