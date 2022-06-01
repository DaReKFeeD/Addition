package com.addition;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.*;
import java.util.stream.Collectors;

public class Logic {

    //////////////
    public ObservableList<Patient> getPatientsByDiagnos(String diagnos, ObservableList<Patient> allPatients) {
        return FXCollections.observableList(allPatients.stream().filter(p -> Objects.equals(p.getDiagnos().getTitle(), diagnos))
                .sorted(Comparator.comparing(Patient::getNum_med_card))
                .collect(Collectors.toList()));}
    //////////////
    public ObservableList<Patient> getPatientsByNumbsOfMC(int numb_1, int numb_2, ObservableList<Patient> allPatients) {
        return FXCollections.observableList(allPatients.stream()
                .filter(p->p.getNum_med_card() > numb_1 && p.getNum_med_card() < numb_2)
                .collect(Collectors.toList()));}
    //////////////
    public ObservableList<Patient> getPatientsByFirstLet(int num, ObservableList<Patient> allPatients){
        char n = Integer.toString(num).charAt(0);
        return FXCollections.observableList(allPatients.stream()
                .filter(p->Objects.equals(Integer.toString(p.getTel_num()).charAt(0), n))
                .collect(Collectors.toList()));}
    //////////////
    public HashMap<String, Integer> getDiagnoses(ObservableList<Patient> allPatients) {
        HashMap<String, Integer> mp = new HashMap<>();
        for (Patient patient : allPatients)
            if (mp.containsKey(patient.getDiagnos().getTitle()))
                mp.put(patient.getDiagnos().getTitle(), mp.get(patient.getDiagnos().getTitle()) + 1);
            else mp.put(patient.getDiagnos().getTitle(), 1);
        return mp.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));}
}