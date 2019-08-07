package com.example.Superbowl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.print.Book;
import java.sql.*;

@Controller
public class VideoController {

    Tag tagSearch = new Tag();
    String connstr = "jdbc:sqlserver://localhost;databasename=Superbowl;user=dbadmin;password=123123";

    @GetMapping("/Cindy")
    public String test(Model model) {
        String result;
        try (Connection conn = DriverManager.getConnection(connstr)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select name FROM Video where id = 3");
            while (rs.next()) {
                result = rs.getString("Name");
                model.addAttribute("result", result);
                System.out.println(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "test";
    }

    @GetMapping("/")
    public String form(Model model) {
        return "form";
    }

    @PostMapping("/search")
    public String goSearch(Model model, @RequestParam String searchBar) {
        String name = "", description = "", embeddedUrl = "", companyName = "";

        String sql = "SELECT name, Description, embedded_URL, companyName\n" +
                "FROM Video\n" +
                "JOIN Company ON company_Id = Company.id\n" +
                "JOIN Genre on genre_Id = Genre.id\n" +
                "JOIN category on category_id = category.id\n" +
                "WHERE Description LIKE ?\n";

        try (Connection conn = DriverManager.getConnection(connstr)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + searchBar + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                name = rs.getString(1);
                description = rs.getString(2);
                embeddedUrl = rs.getString(3);
                companyName = rs.getString(4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        model.addAttribute("name", name);
        model.addAttribute("description", description);
        model.addAttribute("embedded", embeddedUrl);
        model.addAttribute("companyname", companyName);

        return "search";
    }

}
