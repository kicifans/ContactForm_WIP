package com.krists.contactform;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;


import java.io.IOException;
import java.util.Optional;

public class Controller  {

    @FXML
    private TableView<Contact> contactTable;

    @FXML
    private BorderPane mainWindow;

    @FXML
    private Button deleteButton;
    @FXML
    private Button favoriteButton;
    @FXML
    private Button editButton;


    //displays contacts List in table
    //allows to select only by one item at a time
    // enables edit and delete buttons only when item is selected
    public void initialize() {

        contactTable.setItems(ContactData.getContacts());
        contactTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        deleteButton.disableProperty().bind(Bindings.isEmpty(contactTable.getSelectionModel().getSelectedItems()));
        editButton.disableProperty().bind(Bindings.isEmpty(contactTable.getSelectionModel().getSelectedItems()));

    }

    //on shows contact add form when buttons is pressed
    //returns true if item is added
    public void showContactForm() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Add New Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("createContact.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException exception) {
            System.out.println("Couldn't load createContact form");
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ContactFormController controller = fxmlLoader.getController();
            boolean isItemAdded = controller.addItem();
            if(!isItemAdded) {
                showContactForm();
            }
            contactTable.setItems(ContactData.getContacts());
        }
    }

    //// WORK IN PROGRESS
    @FXML
    public void editContact() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainWindow.getScene().getWindow());
        dialog.setTitle("Edit Contact");
        FXMLLoader fxmlLoader = new FXMLLoader();
        int selectedItemIndex = contactTable.getSelectionModel().getSelectedIndex();
        fxmlLoader.setLocation(getClass().getResource("createContact.fxml"));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException exception) {
            System.out.println("Couldn't load createContact form");
        }

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);


        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ContactData.getContacts().remove(selectedItemIndex);
            ContactFormController controller = fxmlLoader.getController();
            controller.addEditedItem(selectedItemIndex);
            System.out.println("Deleted contacts " + ContactData.getContacts().get(selectedItemIndex));
            contactTable.setItems(ContactData.getContacts());
            contactTable.requestFocus();
            contactTable.getSelectionModel().select(selectedItemIndex);
        }
    }

    //Method used to switch between Favorite and Main contacts view
    //new List is made for favorites
    boolean showingFavorite = false;
    public void showFavorites() throws IOException {
        if (!showingFavorite) {
            ObservableList<Contact> favContacts = FXCollections.observableArrayList();
            for (int i = 0; i < ContactData.getContacts().size(); i++) {
                if (ContactData.getContacts().get(i).isFavorite()){
                    favContacts.add(ContactData.getContacts().get(i));
                    System.out.println(ContactData.getContacts().get(i));
                } else {
                    System.out.println("nav favorits");
                }
            }
            favoriteButton.setText("View all contacts");
            contactTable.setItems(favContacts);
            contactTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
            deleteButton.disableProperty().bind(Bindings.isEmpty(contactTable.getSelectionModel().getSelectedItems()));
            showingFavorite = true;
        } else {
            initialize();
            favoriteButton.setText("  View Favorites  ");
            showingFavorite = false;
        }

    }

    //Deletes selected item from Contacts ObservableList
    @FXML
    public void deleteContact() {

        int toRemove = contactTable.getSelectionModel().getSelectedIndex();
        if (toRemove  != -1) {
            System.out.println("Deleted contacts " + ContactData.getContacts().get(toRemove));
            ContactData.getContacts().remove(toRemove);
            contactTable.setItems(ContactData.getContacts());
        } else {
            System.out.println("No items selected");
        }
    }
}
