package com.addition;

import java.sql.*;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class Controller {

    @FXML private RadioButton patientRB;
    @FXML private RadioButton diagnoseRB;
    @FXML private RadioButton doctorRB;

    @FXML private RadioButton task1RB;
    @FXML private RadioButton task2RB;
    @FXML private RadioButton task3RB;

    @FXML private TableView<Patient> tablePatients = new TableView<>();
    @FXML private TableColumn<Patient, Integer> idPat;
    @FXML private TableColumn<Patient, String> surnPat;
    @FXML private TableColumn<Patient, String> namePat;
    @FXML private TableColumn<Patient, String> adresPat;
    @FXML private TableColumn<Patient, Integer> telPat;
    @FXML private TableColumn<Patient, Integer> medcardPat;
    @FXML private TableColumn<Patient, Diagnos> diagPat;
    @FXML private TableColumn<Patient, Doctor> doctPat;

    @FXML private TableView<Doctor> tableDoctors = new TableView<>();
    @FXML private TableColumn<Doctor, Integer> idDoct;
    @FXML private TableColumn<Doctor, String> titleDoct;

    @FXML private TableView<Diagnos> tableDiagnoses = new TableView<>();
    @FXML private TableColumn<Diagnos, Integer> idDiagn;
    @FXML private TableColumn<Diagnos, String> titleDiagn;

    @FXML private GridPane tableLayout;

    @FXML private Button addBtn;
    @FXML private Button delBtn;
    @FXML private Button searchBtn;
    @FXML private Button showDiagnBtn;
    @FXML private Button solveTaskBtn;

    @FXML private TextField searchProperty;
    @FXML private TextField property1;
    @FXML private TextField property2;

    private ObservableList<Patient> allPatients = FXCollections.observableArrayList();
    private ObservableList<Diagnos> allDiagnoses = FXCollections.observableArrayList();
    private ObservableList<Doctor> allDoctors = FXCollections.observableArrayList();
    private PatientDAO patientDAO = new PatientDAO();
    private DiagnosDAO diagnosDAO = new DiagnosDAO();
    private DoctorDAO doctorDAO = new DoctorDAO();
    private final Logic logic = new Logic();
    private final View view = new View();

    @FXML void initialize() {
        try{
            Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/myFirstJDB", "root", "0664708747");
            patientDAO = new PatientDAO(connection);
            doctorDAO = new DoctorDAO(connection);
            diagnosDAO = new DiagnosDAO(connection);
            allPatients = patientDAO.findAll();
            allDoctors = doctorDAO.findAll();
            allDiagnoses = diagnosDAO.findAll();
        }catch (SQLException e){e.printStackTrace();}

        //region Searching max IDs
        ArrayList<Integer> ids1 = new ArrayList<>();
        allPatients.forEach(patient -> ids1.add(patient.getId()));
        final int[] maxIdPatients = {ids1.stream().max(Integer::compareTo).get()};

        ArrayList<Integer> ids2 = new ArrayList<>();
        allDiagnoses.forEach(patient -> ids2.add(patient.getId()));
        final int[] maxIdDiagnoses = {ids2.stream().max(Integer::compareTo).get()};

        ArrayList<Integer> ids3 = new ArrayList<>();
        allDoctors.forEach(patient -> ids3.add(patient.getId()));
        final int[] maxIdDoctors = {ids3.stream().max(Integer::compareTo).get()};
        //endregion

        elementsFunctional(maxIdPatients, maxIdDiagnoses, maxIdDoctors);

        tableSettings();
    }

    private void tableSettings(){
        idPat.setCellValueFactory(new PropertyValueFactory<>("id"));
        surnPat.setCellValueFactory(new PropertyValueFactory<>("surname"));
        namePat.setCellValueFactory(new PropertyValueFactory<>("name"));
        adresPat.setCellValueFactory(new PropertyValueFactory<>("address"));
        telPat.setCellValueFactory(new PropertyValueFactory<>("tel_num"));
        medcardPat.setCellValueFactory(new PropertyValueFactory<>("num_med_card"));
        diagPat.setCellValueFactory(new PropertyValueFactory<>("diagnos"));
        doctPat.setCellValueFactory(new PropertyValueFactory<>("doctor"));

        idDoct.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleDoct.setCellValueFactory(new PropertyValueFactory<>("title"));

        idDiagn.setCellValueFactory(new PropertyValueFactory<>("id"));
        titleDiagn.setCellValueFactory(new PropertyValueFactory<>("title"));

        tablePatients.setItems(allPatients);
        tableDiagnoses.setItems(allDiagnoses);
        tableDoctors.setItems(allDoctors);
    }

    private void elementsFunctional(final int[]maxIdPatients, final int[]maxIdDiagnoses,final int[]maxIdDoctors){
        patientRB.setOnAction(actionEvent -> {
            view.radioButtonModify(searchProperty,property2,delBtn,true,true,true,"Введіть номер мед. картки", "");
            tableLayout.getChildren().clear();
        });
        diagnoseRB.setOnAction(actionEvent -> {
            view.radioButtonModify(searchProperty,property2,delBtn,true,true,false,"Введіть ID діагнозу", "");
            tableLayout.getChildren().clear();
        });
        doctorRB.setOnAction(actionEvent -> {
            view.radioButtonModify(searchProperty,property2,delBtn,true,true,false,"Введіть ID лікаря", "");
            tableLayout.getChildren().clear();
        });

        task1RB.setOnAction(actionEvent -> {
            view.radioButtonModify(property1,property2,delBtn,true,false,true,"Ввдіть діагноз", "");
            tableLayout.getChildren().clear();
        });
        task2RB.setOnAction(actionEvent -> {
            view.radioButtonModify(property1,property2,delBtn,true,true, true,"Введіть початок", "Введіть кінець");
            tableLayout.getChildren().clear();
        });
        task3RB.setOnAction(actionEvent -> {
            view.radioButtonModify(property1,property2,delBtn,true,false, true,"Введіть першу цифру тел. номера", "");
            tableLayout.getChildren().clear();
        });

        addBtn.setOnAction(actionEvent -> {
            if(patientRB.isSelected()){
                view.addPatientDialog(allDoctors,allDiagnoses,allPatients,maxIdPatients);
            }
            else if(diagnoseRB.isSelected()){
                view.addDiagnosDialog(allDiagnoses,maxIdDiagnoses);
            }
            else if(doctorRB.isSelected()){
                view.addDoctorDialog(allDoctors,maxIdDoctors);
            }
        });
        delBtn.setOnAction(actionEvent -> {
            tableLayout.getChildren().clear();
            if(patientRB.isSelected()) {
                allPatients.removeIf(patient -> patient.getNum_med_card() == Integer.parseInt(searchProperty.getText()));
                PatientDAO patientDAO = new PatientDAO();
                patientDAO.delete(Integer.parseInt(searchProperty.getText()));
                Label tempLabel = new Label("Видалено успішно!");
                tableLayout.add(tempLabel,0,0);
            }
            else {Label tempLabel = new Label("Помилка!");
                tableLayout.add(tempLabel,0,0);}
        });
        searchBtn.setOnAction(actionEvent -> {
            tableLayout.getChildren().clear();
            if(patientRB.isSelected()){
                Optional<Patient> p = allPatients.stream().filter(x->x.getNum_med_card()==Integer.parseInt(searchProperty.getText())).findFirst();
                Label tempLabel = new Label(p.toString());
                tableLayout.add(tempLabel,0,0);
            }
            else if(diagnoseRB.isSelected()){
                Optional<Diagnos> diag = allDiagnoses.stream().filter(x->x.getId()==Integer.parseInt(searchProperty.getText())).findFirst();
                Label tempLabel = new Label(diag.toString());
                tableLayout.add(tempLabel,0,0);
            }
            else if(doctorRB.isSelected()){
                Optional<Doctor> diag = allDoctors.stream().filter(x->x.getId()==Integer.parseInt(searchProperty.getText())).findFirst();
                Label tempLabel = new Label(diag.toString());
                tableLayout.add(tempLabel,0,0);
            }
        });
        solveTaskBtn.setOnAction(actionEvent -> {
            tableLayout.getChildren().clear();
            if(task1RB.isSelected())
                view.updateLayoutTable(tableLayout, logic.getPatientsByDiagnos(property1.getText(), allPatients));
            else if(task2RB.isSelected())
                view.updateLayoutTable(tableLayout, logic.getPatientsByNumbsOfMC(Integer.parseInt(property1.getText()),
                        Integer.parseInt(property2.getText()), allPatients));
            else if(task3RB.isSelected()) {
                view.updateLayoutTable(tableLayout, logic.getPatientsByFirstLet(Integer.parseInt(property1.getText()), allPatients));
            }});
        showDiagnBtn.setOnAction(actionEvent -> {
            view.showAllDiagnoses(tableLayout,logic.getDiagnoses(allPatients));
        });
    }
}