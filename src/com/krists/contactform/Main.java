package com.krists.contactform;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Contacts");
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        Scene scene = new Scene(root,663, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        scene.getStylesheets().add("style.css");
    }

    public static void main(String[] args) {
        launch(args);
    }

    //Overrided method which calls loadContacts method on program startup
    //load Contacts in Contact ArrayList from ContactList.txt when program starts
    @Override
    public void init() throws Exception {
        try {
            ContactData.loadContacts();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // Calls storeContacts method to save Contacts in file, when program is closed
    @Override
    public void stop() throws Exception {
        try {
            ContactData.storeContacts();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }
}

