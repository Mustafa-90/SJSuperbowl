package com.example.Superbowl;

import java.sql.*;

public class SuperbowlApplication {

    public static void main(String[] args) {
        int count = 0;
        String connstr = "jdbc:sqlserver://localhost;databasename=Superbowl;user=admindb;password=123123";

        try (Connection conn = DriverManager.getConnection(connstr)) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("Select name FROM Video where id = 3");

            while (rs.next()) {
                System.out.println(rs.getString("Name"));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
