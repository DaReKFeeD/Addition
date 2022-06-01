package com.addition;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DoctorDAO {

    private Connection connection;

    public DoctorDAO(Connection connection) {
        this.connection = connection;
    }

    public DoctorDAO (){}

    public ObservableList<Doctor> findAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from doctors");
            ObservableList<Doctor> result = FXCollections.observableArrayList();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("Title");
                result.add(new Doctor(id, title));
            }
            return result;
        } catch (SQLException e) {
            return null;
        }
    }

    public void add(Doctor d){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/myFirstJDB", "root", "0664708747");
            PreparedStatement ps = connection.prepareStatement("insert into doctors (id, title) VALUES (?, ?)");
            ps.setInt(1, d.getId());
            ps.setString(2, d.getTitle());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}