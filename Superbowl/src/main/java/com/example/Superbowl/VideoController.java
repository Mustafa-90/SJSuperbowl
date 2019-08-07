package com.example.Superbowl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.sql.*;

@Controller
public class VideoController {


    @GetMapping("/Cindy")
    public String test(Model model) {
        String result;
        String connstr = "jdbc:sqlserver://localhost;databasename=Superbowl;user=admindb;password=123123";
        try (Connection conn = DriverManager.getConnection(connstr)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select name FROM Video where id = 3");
            while (rs.next()) {
                result = rs.getString("Name");
                model.addAttribute("result", result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "test";
    }

}
