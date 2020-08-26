package com.krists.contactform;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class Contact {

    private SimpleStringProperty name = new SimpleStringProperty("");
    private SimpleStringProperty lastName = new SimpleStringProperty("");
    private SimpleStringProperty email = new SimpleStringProperty("");
    private SimpleStringProperty phoneNumber = new SimpleStringProperty("");
    private SimpleStringProperty date = new SimpleStringProperty("");
    private SimpleBooleanProperty favorite  = new SimpleBooleanProperty(false);

    public Contact(String name, String lastName, String email, String phoneNumber, String date, boolean favorite){
        this.name.set(name);
        this.lastName.set(lastName);
        this.email.set(email);
        this.phoneNumber.set(phoneNumber);
        this.date.set(date);
        this.favorite.set(favorite);
    }

    public String getName() {
        return name.get();
    }


    public String getLastName() {
        return lastName.get();
    }


    public String getEmail() {
        return email.get();
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public String getDate() {
        return date.get();
    }

    public boolean isFavorite() {
        return favorite.get();
    }

    @Override
    public String toString() {
        return "Contact{" +
                "name=" + name +
                ", lastName=" + lastName +
                ", email=" + email +
                ", phoneNumber=" + phoneNumber +
                ", date=" + date +
                ", favorite=" + favorite +
                '}';
    }
}



