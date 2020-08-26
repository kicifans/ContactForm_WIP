package com.krists.contactform;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactFormController{

    @FXML
    private TextField nameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private CheckBox favoriteCheckbox;


    // method to set date when Contact is created
    public String creationDate() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d.MM.yyyy. ");
        return dateFormat.format(LocalDate.now());
    }

    //method to get get text from contactForm textfields
    //makes new Contact and adds it to List<Contact> contacts
    //if contact is not acceptable, show error dialog
    public boolean addItem() throws IOException {
        String name = nameField.getText().trim();
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        String lastName = lastNameField.getText().trim();
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        boolean isFavorite = false;
        if (favoriteCheckbox.isSelected()) {
            isFavorite = true;
        }

        String errorMessage = checkContactForm(name, lastName, email, phone);
        if (errorMessage.equals("")) {
            String date = creationDate();
            Contact contact = new Contact(name, lastName, email, phone, date, isFavorite);
            ContactData.getContacts().add(contact);
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Failed to add new Contact");
            alert.setHeaderText(null);
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

    //method checks if name && surname contains only letters
    //if e-mail contains @ and . chars
    //if phone number contains only numbers
    public static String checkContactForm(String name, String surname, String email, String phone) {

        String errorMessage ="";

        if (!name.matches("[a-zA-Z]+") || name.length() == 0) {
            errorMessage += "Name field can't be empty and must contain only letters" + System.lineSeparator() + System.lineSeparator();
        }
        if (!surname.matches("[a-zA-Z]+") || surname.length() == 0) {
            errorMessage += "Surname field can't be empty and must contain only letters" + System.lineSeparator() + System.lineSeparator();
        }

        //checks if e-mail is valid by checking against pattern
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        Matcher matcher = pattern.matcher(email);
        boolean matches = matcher.matches();
        if(!matches || email.length() < 5) {
            errorMessage += "E-mail address is not correct" + System.lineSeparator() + System.lineSeparator();
        }

        if (!phone.matches("[0-9]+") || phone.length() < 5) {
            errorMessage += "Phone number can contain only numbers and must be at least 5 digits long" + System.lineSeparator() + System.lineSeparator();
        }

        return errorMessage;
    }

    //// WORK IN PROGRESS
    public boolean addEditedItem(int selectedItemIndex) throws IOException {
        String name = nameField.getText().trim();
        name = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
        String lastName = lastNameField.getText().trim();
        lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        boolean isFavorite = false;
        if (favoriteCheckbox.isSelected()) {
            isFavorite = true;
        }

        String errorMessage = checkContactForm(name, lastName, email, phone);
        if (errorMessage.equals("")) {
            String date = creationDate();
            Contact contact = new Contact(name, lastName, email, phone, date, isFavorite);
            ContactData.getContacts().add(selectedItemIndex, contact);
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Failed to add new Contact");
            alert.setHeaderText(null);
            alert.setContentText(errorMessage);
            alert.showAndWait();
            return false;
        }
    }

}
