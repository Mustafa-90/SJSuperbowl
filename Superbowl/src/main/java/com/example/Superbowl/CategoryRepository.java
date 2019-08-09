package com.example.Superbowl;import org.springframework.stereotype.Service;import java.sql.*;
import java.util.ArrayList;
import java.util.List;@Service
public class CategoryRepository {    String connstr = "jdbc:sqlserver://localhost;databasename=Superbowl2;user=dbadmin;password=123123";    public List<Video> getCategoryVideos(String category) {
    List<Video> listOfVideo = new ArrayList<>();        List<Video> listOfVideos = new ArrayList<>();
    String name = "", description = "", embeddedUrl = "";
    boolean isPK;        String sql = "SELECT Name, Description, embedded_URL, isPK\n" +
            "FROM Video\n" +
            "JOIN Company ON company_Id = Company.id\n" +
            "JOIN Genre on genre_Id = Genre.id\n" +
            "JOIN category on category_id = category.id\n" +
            "WHERE Category.categoryName = ?";        try (Connection conn = DriverManager.getConnection(connstr)) {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, category);
        ResultSet rs = ps.executeQuery();            while (rs.next()) {
            name = rs.getString(1);
            description = rs.getString(2);
            embeddedUrl = rs.getString(3);
            isPK = rs.getBoolean(4);
            listOfVideos.add(new Video(name, description, isPK, embeddedUrl));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }        return listOfVideos;
}}