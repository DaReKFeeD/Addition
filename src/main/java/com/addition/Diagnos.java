package com.addition;

import java.util.Objects;

public class Diagnos extends Patient {

    private int id;
    private String Title;

    public Diagnos(int id, String Title) {
        this.id = id;
        this.Title = Title;
    }
    public Diagnos(String Title) {this.Title = Title;}

    public int getId() {return id;}
    public String getTitle() {return Title;}

    public void setId(int id) {this.id = id;}
    public void setTitle(String Title) {this.Title = Title;}

    @Override
    public String toString() {return this.Title;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Diagnos diagnos)) return false;
        return id == diagnos.id && Objects.equals(Title, diagnos.Title);
    }

    @Override
    public int hashCode() {return Objects.hash(id, Title);}
}