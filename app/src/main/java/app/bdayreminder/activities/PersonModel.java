package app.bdayreminder.activities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class PersonModel implements Parcelable {

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

    protected PersonModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        surname = in.readString();
        dob = in.readString();
    }

    public static final Creator<PersonModel> CREATOR = new Creator<PersonModel>() {
        @Override
        public PersonModel createFromParcel(Parcel in) {
            return new PersonModel(in);
        }

        @Override
        public PersonModel[] newArray(int size) {
            return new PersonModel[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(dob);
    }
}