package com.krists.contactform;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

public class ContactData {

    public static String contactFile = "ContactList.txt";
    private static ObservableList<Contact> contacts;

    public static ObservableList<Contact> getContacts() {
        return contacts;
    }

    //Creates or opens ContactList.txt file
    //method goes through each List<Contact> contacts and saves them in ContactList.txt,
    // formatting them and separating with tabs
    public static void storeContacts() throws IOException {
        Path path = Paths.get(contactFile);
        BufferedWriter bufferedWriter = Files.newBufferedWriter(path);

        try {
            Iterator<Contact> iter = contacts.iterator();
            while (iter.hasNext()) {
                Contact contact = iter.next();
                bufferedWriter.write(String.format("%s\t%s\t%s\t%s\t%s\t%s",
                        contact.getName(),
                        contact.getLastName(),
                        contact.getEmail(),
                        contact.getPhoneNumber(),
                        contact.getDate(),
                        contact.isFavorite()));
                bufferedWriter.newLine();
            }
        } finally {
            if(bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    //Reads contactList.txt file
    //Each line is separated in smaller parts as Strings in String[]
    // those parts are used to make new Contact
    //contact are added to List<Contact> contacts

    public static void loadContacts() throws IOException {
        contacts = FXCollections.observableArrayList();
        Path path = Paths.get(contactFile);
        BufferedReader bufferedReader = Files.newBufferedReader(path);

        String input;

        while ((input = bufferedReader.readLine()) != null) {
            String[] contactInformation = input.split("\t");

            String name = contactInformation[0];
            String surname = contactInformation[1];
            String email = contactInformation[2];
            String phoneNumber = contactInformation[3];
            String date = contactInformation[4];
            boolean isFavorite = Boolean.parseBoolean(contactInformation[5]);

            Contact contact = new Contact(name, surname, email, phoneNumber, date, isFavorite);
            contacts.add(contact);
        }
    }

}

