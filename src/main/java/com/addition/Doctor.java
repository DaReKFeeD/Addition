package com.addition;

import java.util.Objects;

public class Doctor extends Patient {

    private int id;
    private String Title;

    public Doctor(int id, String Title) {
        this.id = id;
        this.Title = Title;
    }
    public Doctor(String Title) {this.Title = Title;}

    public int getId() {return id;}
    public String getTitle() {return Title;}

    public void setId(int id) {this.id = id;}
    public void setTitle(String Title) {this.Title = Title;}

    @Override
    public String toString() {return this.Title;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Doctor doctor)) return false;
        return id == doctor.id && Objects.equals(Title, doctor.Title);
    }

    @Override
    public int hashCode() {return Objects.hash(id, Title);}
}
