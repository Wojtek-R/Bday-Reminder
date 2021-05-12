package app.bdayreminder.activities;

import java.util.Date;

public class PersonModel {

    private int id;
    private String name;
    private String surname;
    private String dob;

    //constructor
    public PersonModel(int id, String name, String surname, String dob) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
    }

    @Override
    //creating customized string output
    public String toString() {

        return id + ".  " + name + "  " + surname + " " + dob;

//        return "PersonModel{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", surname='" + surname + '\'' +
//                '}';
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDob() {
        return dob;
    }
}