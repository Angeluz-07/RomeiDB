/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romei;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;

/**
 *
 * @author User
 */
public class MainController {
    @FXML
    private BorderPane parent;

    @FXML
    private void loadRegistersView() throws IOException {
        Parent container = App.loadFXML("register");
        parent.setCenter(container);
    }

    @FXML
    private void loadReportsView() throws IOException {
        Parent container = App.loadFXML("report");
        parent.setCenter(container);
    }

    @FXML
    private void loadAddProductsView() throws IOException {
        Parent container = App.loadFXML("addProduct");
        parent.setCenter(container);
    }

    @FXML
    private void loadUpdProductsView() throws IOException {
        Parent container = App.loadFXML("updProduct");
        parent.setCenter(container);
    }

    @FXML
    private void loadAddUsersView() throws IOException {
        Parent container = App.loadFXML("addUser");
        parent.setCenter(container);
    }

    @FXML
    private void loadUpdUsersView() throws IOException {
        Parent container = App.loadFXML("updUser");
        parent.setCenter(container);
    }

    @FXML
    private void loadAddSuppliersView() throws IOException {
        Parent container = App.loadFXML("addSupplier");
        parent.setCenter(container);

    }

    @FXML
    private void loadUpdSuppliersView() throws IOException {
        Parent container = App.loadFXML("updSupplier");
        parent.setCenter(container);
    }

    @FXML
    private void runExit() throws IOException {
        App.window.close();
    }

    @FXML
    private void runLogout() throws IOException {
        /*
         * Login log=new Login();
         * RomeiDB.sceneLogIn = new Scene(log.getLoginPane(), 820, 600);
         * RomeiDB.sceneLogIn.getStylesheets().add(getClass().getResource("Style.css").
         * toExternalForm());
         * App.window.setScene(RomeiDB.sceneLogIn);
         */
        System.out.println("logout");
    }

}
