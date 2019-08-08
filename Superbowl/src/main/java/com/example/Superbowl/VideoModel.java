package com.example.Superbowl;

import org.springframework.ui.Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Useless class that wont be used in this project.
 */

public class VideoModel {


    public String searchMethod(Model model, String searchBar, String genreList, String categoryList) {

        String connstr = "jdbc:sqlserver://localhost;databasename=Superbowl2;user=dbadmin;password=123123";
        List<Video> listOfVideos = new ArrayList<>();
        String name = "", description = "", embeddedUrl = "", companyName = "";
        String[] tags = new String[3];
        String sql = "";

        if (!genreList.equals("Genre") && !categoryList.equals("Category") && !searchBar.equals("")) {
            sql = "SELECT name, Description, embedded_URL, companyName\n" +
                    "FROM Video\n" +
                    "JOIN Company ON company_Id = Company.id\n" +
                    "JOIN Genre on genre_Id = Genre.id\n" +
                    "JOIN category on category_id = category.id\n" +
                    "WHERE Genre = ? AND categoryname = ?\n" +
                    "OR searchtag like ?\n" +
                    "OR searchtag like ?\n" +
                    "OR searchtag like ?";

            String[] splitTags = searchBar.split(" ");

            if (splitTags.length > 0 && !splitTags[0].equals(""))
                tags[0] = splitTags[0];
            if (splitTags.length > 1)
                tags[1] = splitTags[1];
            if (splitTags.length > 2)
                tags[2] = splitTags[2];
            else if (splitTags.length == 0 || splitTags[0].equals("")) {
                tags = new String[]{" ", "", ""};
            }
            try (Connection conn = DriverManager.getConnection(connstr)) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, genreList);
                ps.setString(2, categoryList);
                ps.setString(3, "%" + tags[0] + "%");
                ps.setString(4, "%" + tags[1] + "%");
                ps.setString(5, "%" + tags[2] + "%");
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
        } else if (!genreList.equals("Genre") && !categoryList.equals("Category") && searchBar.equals("")) {
            sql = "SELECT name, Description, embedded_URL, companyName\n" +
                    "FROM Video\n" +
                    "JOIN Company ON company_Id = Company.id\n" +
                    "JOIN Genre on genre_Id = Genre.id\n" +
                    "JOIN category on category_id = category.id\n" +
                    "WHERE Genre = ? AND categoryname = ?\n";


            try (Connection conn = DriverManager.getConnection(connstr)) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, genreList);
                ps.setString(2, categoryList);
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

        } else if (!genreList.equals("Genre") && categoryList.equals("Category")) {
            sql = "SELECT name, Description, embedded_URL, companyName\n" +
                    "FROM Video\n" +
                    "JOIN Company ON company_Id = Company.id\n" +
                    "JOIN Genre on genre_Id = Genre.id\n" +
                    "JOIN category on category_id = category.id\n" +
                    "WHERE Genre = ? " +
                    "OR searchtag like ?\n" +
                    "OR searchtag like ?\n" +
                    "OR searchtag like ?";

            String[] splitTags = searchBar.split(" ");

            tags[0] = splitTags[0];
            if (splitTags.length > 1)
                tags[1] = splitTags[1];
            if (splitTags.length > 2)
                tags[2] = splitTags[2];
            try (Connection conn = DriverManager.getConnection(connstr)) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, genreList);
                ps.setString(2, "%" + tags[0] + "%");
                ps.setString(3, "%" + tags[1] + "%");
                ps.setString(4, "%" + tags[2] + "%");
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
        } else if (genreList.equals("Genre") && !categoryList.equals("Category")) {
            sql = "SELECT name, Description, embedded_URL, companyName\n" +
                    "FROM Video\n" +
                    "JOIN Company ON company_Id = Company.id\n" +
                    "JOIN Genre on genre_Id = Genre.id\n" +
                    "JOIN category on category_id = category.id\n" +
                    "WHERE categoryname = ? " +
                    "OR searchtag like ?\n" +
                    "OR searchtag like ?\n" +
                    "OR searchtag like ?";

            String[] splitTags = searchBar.split(" ");

            tags[0] = splitTags[0];
            if (splitTags.length > 1)
                tags[1] = splitTags[1];
            if (splitTags.length > 2)
                tags[2] = splitTags[2];
            try (Connection conn = DriverManager.getConnection(connstr)) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, categoryList);
                ps.setString(2, "%" + tags[0] + "%");
                ps.setString(3, "%" + tags[1] + "%");
                ps.setString(4, "%" + tags[2] + "%");
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
        } else if (genreList.equals("Genre") && categoryList.equals("Category")) {
            sql = "SELECT name, Description, embedded_URL, companyName\n" +
                    "FROM Video\n" +
                    "JOIN Company ON company_Id = Company.id\n" +
                    "JOIN Genre on genre_Id = Genre.id\n" +
                    "JOIN category on category_id = category.id\n" +
                    "WHERE searchtag like ?\n" +
                    "OR searchtag like ?\n" +
                    "OR searchtag like ?";

            String[] splitTags = searchBar.split(" ");

            tags[0] = splitTags[0];
            if (splitTags.length > 1)
                tags[1] = splitTags[1];
            if (splitTags.length > 2)
                tags[2] = splitTags[2];
            try (Connection conn = DriverManager.getConnection(connstr)) {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + tags[0] + "%");
                ps.setString(2, "%" + tags[1] + "%");
                ps.setString(3, "%" + tags[2] + "%");
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

            model.addAttribute("listOfVideos", listOfVideos);

        }
        return "search";
    }
}
