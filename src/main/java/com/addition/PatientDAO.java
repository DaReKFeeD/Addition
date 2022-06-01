package com.addition;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.HashSet;

public class PatientDAO {

    private Connection connection;

    public PatientDAO(Connection connection) {
        this.connection = connection;
    }

    public PatientDAO() {}

    public ObservableList<Patient> findAll() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from patients p join diagnoses d on d.id = p.DiagnID join doctors d2 on p.DoctorID = d2.id");
            ObservableList<Patient> patients = FXCollections.observableArrayList();
            HashSet<Diagnos> diagnoses = new HashSet<>();
            HashSet<Doctor> doctors = new HashSet<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String Surname = resultSet.getString("Surname");
                String Name = resultSet.getString("Name");
                String Address = resultSet.getString("Address");
                int TelNum = resultSet.getInt("TelNum");
                int NumMedCard = resultSet.getInt("NumMedCard");
                int DiagnID = resultSet.getInt("d.id");
                String TitleDiag = resultSet.getString("d.Title");
                int DoctorID = resultSet.getInt("d2.id");
                String TitleDoct = resultSet.getString("d2.Title");

                Diagnos diagnos = new Diagnos(DiagnID,TitleDiag);
                diagnoses.add(diagnos);
                diagnos = diagnoses.stream().filter(g->g.getId()==DiagnID).findFirst().get();

                Doctor doctor = new Doctor(DoctorID, TitleDoct);
                doctors.add(doctor);
                doctor = doctors.stream().filter(g->g.getId()==DoctorID).findFirst().get();

                patients.add(new Patient(id,Surname,Name,Address,TelNum,NumMedCard,diagnos, doctor));
            }
            return patients;
        } catch (SQLException ex) {
            return null;
        }
    }

    public void add(Patient p){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/myFirstJDB", "root", "0664708747");
            PreparedStatement ps = connection.prepareStatement("insert into patients (id, Surname, Name, Address, TelNum, NumMedCard, DiagnID, DoctorID) VALUES (?,?,?,?,?,?,?,?)");
            ps.setInt(1,p.getId());
            ps.setString(2,p.getSurname());
            ps.setString(3,p.getName());
            ps.setString(4,p.getAddress());
            ps.setInt(5,p.getTel_num());
            ps.setInt(6,p.getNum_med_card());
            ps.setInt(7,p.getDiagnos().getId());
            ps.setInt(8,p.getDoctor().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int num_med_card) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/myFirstJDB", "root", "0664708747");
            PreparedStatement ps = connection.prepareStatement("delete from patients where NumMedCard = ?");
            ps.setInt(1, num_med_card);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}