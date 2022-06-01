package com.addition;

import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import java.util.*;

public class View {

    public void radioButtonModify(TextField property1, TextField property2, Button btn,
                                  boolean visible1, boolean visible2,boolean visible3,
                                  String promptText1, String promptText2) {
        property1.setVisible(visible1);
        property2.setVisible(visible2);
        property1.setPromptText(promptText1);
        property2.setPromptText(promptText2);
        property1.setText("");
        property2.setText("");
        btn.setVisible(visible3);
    }

    public void updateLayoutTable(GridPane layoutTable, ObservableList<Patient> patients) {
        layoutTable.getChildren().clear();
        int temp_id = 0;
        for (Patient p : patients) {
            Label tempLabel = new Label(p.toString());
            layoutTable.add(tempLabel, 0, temp_id);
            temp_id++;
        }
    }

    public void showAllDiagnoses(GridPane grid, HashMap<String, Integer> map) {
        grid.getChildren().clear();
        int temp_id = 0;
        for (Map.Entry<String, Integer> item : map.entrySet()) {
            Label tempLabel = new Label("Diagnose " + item.getKey() + " " + item.getValue() + " patients\n");
            grid.add(tempLabel, 0, temp_id);
            temp_id++;
        }
    }

    public void addPatientDialog(ObservableList<Doctor> allDoctors, ObservableList<Diagnos> allDiagnoses,
                                 ObservableList<Patient> allPatients, final int[] maxId) {
        Dialog<Patient> dialog = new Dialog<>();
        dialog.setTitle("Пацієнт");
        dialog.setHeaderText("Введіть дані про працієнта та \n" +
                "натисніть кнопку ОК для додавання до бази.");
        dialog.setResizable(true);

        Label label1 = new Label("Прізвище: ");
        Label label2 = new Label("Ім'я: ");
        Label label3 = new Label("Адреса: ");
        Label label4 = new Label("Тел. номер: ");
        Label label5 = new Label("Номер мед. картки: ");
        Label label6 = new Label("Діагноз");
        Label label7 = new Label("Лікар");
        TextField text1 = new TextField();
        TextField text2 = new TextField();
        TextField text3 = new TextField();
        TextField text4 = new TextField();
        TextField text5 = new TextField();
        ChoiceBox<Diagnos> choiceDiagn = new ChoiceBox<>();
        choiceDiagn.setItems(allDiagnoses);
        ChoiceBox<Doctor> choiceDoct = new ChoiceBox<>();
        choiceDoct.setItems(allDoctors);

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 2);
        grid.add(text2, 2, 2);
        grid.add(label3, 1, 3);
        grid.add(text3, 2, 3);
        grid.add(label4, 1, 4);
        grid.add(text4, 2, 4);
        grid.add(label5, 1, 5);
        grid.add(text5, 2, 5);
        grid.add(label6, 1, 6);
        grid.add(choiceDiagn, 2, 6);
        grid.add(label7, 1, 7);
        grid.add(choiceDoct, 2, 7);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("ОК", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Patient>() {
            @Override
            public Patient call(ButtonType b) {
                if (b == buttonTypeOk) {
                    Patient temp = new Patient(maxId[0] + 1, text1.getText(), text2.getText(), text3.getText(), Integer.parseInt(text4.getText()),
                            Integer.parseInt(text5.getText()), choiceDiagn.getValue(), choiceDoct.getValue());
                    allPatients.add(temp);
                    PatientDAO patientDAO = new PatientDAO();
                    patientDAO.add(temp);
                    maxId[0] = maxId[0] + 1;
                    return temp;
                }
                return null;
            }
        });
        Optional<Patient> result = dialog.showAndWait();
    }

    public void addDiagnosDialog(ObservableList<Diagnos> allDiagnoses, final int[] maxId) {
        Dialog<Diagnos> dialog = new Dialog<>();
        dialog.setTitle("Діагноз");
        dialog.setHeaderText("Введіть дані про діагноз та \n" +
                "натисніть кнопку ОК для додавання до бази.");
        dialog.setResizable(true);

        Label label1 = new Label("Назва діагнозу: ");
        TextField text1 = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("ОК", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Diagnos>() {
            @Override
            public Diagnos call(ButtonType b) {
                if (b == buttonTypeOk) {
                    Diagnos temp = new Diagnos(maxId[0] + 1, text1.getText());
                    allDiagnoses.add(temp);
                    DiagnosDAO diagnosDAO = new DiagnosDAO();
                    diagnosDAO.add(temp);
                    maxId[0] = maxId[0] + 1;
                    return temp;
                }
                return null;
            }
        });
        Optional<Diagnos> result = dialog.showAndWait();
    }

    public void addDoctorDialog(ObservableList<Doctor> allDoctors, final int[] maxId) {
        Dialog<Doctor> dialog = new Dialog<>();
        dialog.setTitle("Лікар");
        dialog.setHeaderText("Введіть дані про лікаря та \n" +
                "натисніть кнопку ОК для додавання до бази.");
        dialog.setResizable(true);

        Label label1 = new Label("ПІБ лікаря: ");
        TextField text1 = new TextField();

        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);

        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("ОК", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);

        dialog.setResultConverter(new Callback<ButtonType, Doctor>() {
            @Override
            public Doctor call(ButtonType b) {
                if (b == buttonTypeOk) {
                    Doctor temp = new Doctor(maxId[0] + 1, text1.getText());
                    allDoctors.add(temp);
                    DoctorDAO doctorDAO = new DoctorDAO();
                    doctorDAO.add(temp);
                    maxId[0] = maxId[0] + 1;
                    return temp;
                }
                return null;
            }
        });
        Optional<Doctor> result = dialog.showAndWait();
    }
}