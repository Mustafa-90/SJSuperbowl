package com.example.Superbowl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class VideoController {

    //VideoModel vm = new VideoModel();
    String connstr = "jdbc:sqlserver://localhost;databasename=Superbowl2;user=dbadmin;password=123123";


    @GetMapping("/login")
    public String getLogin(HttpSession session) {
        String checkSession = (String) session.getAttribute("username");
        if (checkSession != null) {
            return "search";
        } else {
            return "login";
        }
    }

    @PostMapping("/login")
    public String postLogin(HttpSession session, @RequestParam String username, @RequestParam String password) {
        String userLogin = "";
        String userPassword = "";
        String sql = "SELECT username, password\n" +
                "FROM UserAccount\n" +
                "WHERE username = ?\n" +
                "AND password = ?";

        try (Connection conn = DriverManager.getConnection(connstr)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userLogin = rs.getString(1);
                userPassword = rs.getString(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (userLogin.equals(username.toLowerCase()) && userPassword.equals(password)) {
            session.setAttribute("username", username);
            return "SBindex";
        } else {
            return "login";
        }
    }

    @GetMapping("/")
    public String getHomepage(HttpSession session) {
        String checkSession = (String) session.getAttribute("username");
        if (checkSession != null) {
            return "search";
        } else {
            return "login";
        }
    }

    @GetMapping("/search")
    public String getSearch(HttpSession session) {
        String checkSession = (String) session.getAttribute("username");
        if (checkSession != null) {
            return "search";
        } else {
            return "login";
        }
    }

    @PostMapping("/search")
    public String postSearch(Model model, @RequestParam String searchBar) {

        List<Video> listOfVideos = new ArrayList<>();
        String name = "", description = "", embeddedUrl = "", companyName = "";
        String[] tags = new String[3];
        String[] splitTags = searchBar.split(" ");

        tags[0] = splitTags[0];
        if (splitTags.length > 1)
            tags[1] = splitTags[1];
        if (splitTags.length > 2)
            tags[2] = splitTags[2];


        String sql = "SELECT name, Description, embedded_URL, companyName\n" +
                "FROM Video\n" +
                "JOIN Company ON company_Id = Company.id\n" +
                "JOIN Genre on genre_Id = Genre.id\n" +
                "JOIN category on category_id = category.id\n" +
                "WHERE searchtag like ?\n" +
                "OR searchtag like ?\n" +
                "OR searchtag like ?";

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
        return "search";
    }

    @PostMapping("/logout")
    public String getLogout(HttpSession session) {
        session.invalidate();
        return "login";
    }
}
