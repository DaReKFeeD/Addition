package com.addition;

import java.util.Objects;

public class Patient {

    private int id;
    private String name;
    private String surname;
    private String address;
    private int tel_num;
    private int num_med_card;
    private Diagnos diagnos;
    private Doctor doctor;

    public Patient(int id, String name, String surname, String address, int tel_num, int num_med_card, Diagnos diagnos, Doctor doctor){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.tel_num = tel_num;
        this.num_med_card = num_med_card;
        this.diagnos = diagnos;
        this.doctor=doctor;
    }

    public Patient() {};

    public void setName(String name) {this.name = name;}
    public void setSurname(String surname) {this.surname = surname;}
    public void setAddress(String address) {this.address = address;}
    public void setTel_num(int tel_num) {this.tel_num = tel_num;}
    public void setNum_med_card(int num_med_card) {this.num_med_card = num_med_card;}
    public void setId(int id) {this.id = id;}
    public void setDiagnos(Diagnos diagnos) {this.diagnos = diagnos;}
    public void setDoctor(Doctor doctor) {this.doctor = doctor;}

    public String getName() {return this.name;}
    public String getSurname() {return this.surname;}
    public String getAddress() {return this.address;}
    public int getTel_num() {return this.tel_num;}
    public int getNum_med_card() {return this.num_med_card;}
    public int getId() {return this.id;}
    public Diagnos getDiagnos() {return this.diagnos;}
    public Doctor getDoctor() {return this.doctor;}

    @Override
    public String toString() {
        return  " ID: " + id +
                ", Прізвище: " + surname +
                ", Ім'я: " + name +
                ", Адреса: " + address +
                ",\nТел. номер: " + tel_num +
                ", номер мед. картки: " + num_med_card +
                ", діагноз: " + diagnos +
                ", лікар: " + doctor +
                ';';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id && tel_num == patient.tel_num && num_med_card == patient.num_med_card && Objects.equals(name, patient.name) && Objects.equals(surname, patient.surname) && Objects.equals(address, patient.address) && Objects.equals(diagnos, patient.diagnos)&& Objects.equals(doctor, patient.doctor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, address, tel_num, num_med_card, diagnos, doctor);
    }
}