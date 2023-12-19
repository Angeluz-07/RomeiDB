/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.romei;

import static com.romei.Utils.setDateFormat;
import static com.romei.Utils.showErrorDialog;
import static com.romei.Utils.showInfoDialog;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.Parent;

/**
 *
 * @author User
 */
public class MainController {
    @FXML
    private BorderPane parent;

    static ArrayList<Object> data;
    static ComboBox<Product> productComboBox;
    static ArrayList<Object> fields;
    static Text actiontarget;

    static ArrayList<Product> products = new ArrayList();
    static TextField productName;
    static TextField price;
    static TextField supplierName;
    static ArrayList<User> users = new ArrayList();

    static TextField firstName;
    static TextField lastName;
    static TextField userName;

    static TextField passInput;
    static TextField confirmPassInput;
    static ArrayList<Supplier> suppliers = new ArrayList();

    static TextField contactName;
    static TextField phone;
    @FXML
    private RegisterController registerController;
      
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
        // GridPane with 10px padding around edge
        VBox container = new VBox();
        // container.setAlignment(Pos.TOP_CENTER);
        HBox hbTitle = new HBox();
        hbTitle.getChildren().add(new Text("Ingrese los datos"));
        hbTitle.setAlignment(Pos.CENTER);
        container.getChildren().add(hbTitle);

        GridPane addProductPane = new GridPane();
        addProductPane.setAlignment(Pos.CENTER);
        // addProductPane.setGridLinesVisible(true);
        addProductPane.setPadding(new Insets(10, 10, 10, 10));
        addProductPane.setVgap(10);
        addProductPane.setHgap(10);

        ComboBox<Product> productComboBox = new ComboBox<>();
        productComboBox.setPromptText("Elija un producto");
        // the next line should be replaced with a db access

        ProductService productService = new ProductService();
        productComboBox.getItems().addAll(productService.getProducts());
        GridPane.setConstraints(productComboBox, 3, 1);

        // productName Label - constrains use (child, column, row)
        Label productNameLabel = new Label("Nombre del Producto:");
        GridPane.setConstraints(productNameLabel, 0, 1);
        // productName Input
        TextField productName = new TextField();
        GridPane.setConstraints(productName, 1, 1);

        // price Label - constrains use (child, column, row)
        Label priceLabel = new Label("Precio:");
        GridPane.setConstraints(priceLabel, 0, 2);
        // price Input
        TextField price = new TextField();
        GridPane.setConstraints(price, 1, 2);

        // supplierName Label - constrains use (child, column, row)
        Label supplierNameLabel = new Label("Nombre de Proveedor:");
        GridPane.setConstraints(supplierNameLabel, 0, 3);
        // userName Input
        TextField supplierName = new TextField();
        GridPane.setConstraints(supplierName, 1, 3);

        productComboBox.setOnHidden(e -> {
            Product chosenProduct = (Product) productComboBox.getValue();
            productName.setText(chosenProduct.getName());
            price.setText(Double.toString(chosenProduct.getPrice()));
            supplierName.setText(chosenProduct.getSupplier().getContactName());
        });

        Button deleteButton = new Button("Borrar");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(deleteButton);

        SupplierService supplierService = new SupplierService();
        deleteButton.setOnAction(e -> {
            List<Object> inputs = Arrays.asList(
                    productName.getText(),
                    price.getText(),
                    supplierName.getText());
            Boolean emptyInputs = inputs.stream().map(s -> s.toString()).anyMatch(s -> s.isEmpty());
            if (emptyInputs) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Por favor ingrese todos los campos");
                return;
            }

            // todo: this check could removed
            Boolean supplierExists = supplierService.suppliertExists(supplierName.getText());
            if (!supplierExists) {
                showErrorDialog("El Proveedor no esta registrado");
                return;
            }

            Boolean productExists = productService.productExists(productName.getText(), price.getText());
            if (!productExists) {
                showErrorDialog("El producto no existe.");
                return;
            }
            Boolean removedSuccessfully = productService.removeProduct(
                    productComboBox.getValue().getProductPriceID(),
                    productComboBox.getValue().getProductID());

            if (removedSuccessfully) {
                showInfoDialog("La operacion se realizo con exito!");
            } else {
                showErrorDialog("Un error ocurrio durante la transaccion");
            }
        });
        // save
        Button saveButton = new Button("Guardar");
        hbBtn.getChildren().add(saveButton);
        GridPane.setConstraints(hbBtn, 1, 4);
        saveButton.setOnAction(e -> {

            List<Object> inputs = Arrays.asList(
                    productName.getText(),
                    price.getText(),
                    supplierName.getText());
            Boolean emptyInputs = inputs.stream().map(s -> s.toString()).anyMatch(s -> s.isEmpty());
            if (emptyInputs) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Por favor ingrese todos los campos");
                return;
            }

            Boolean productExists = productService.productExists(productName.getText(), price.getText());
            if (productExists) {
                showErrorDialog("El producto ya existe.");
                return;
            }

            Boolean supplierExists = supplierService.suppliertExists(supplierName.getText());
            if (!supplierExists) {
                showErrorDialog("El Proveedor no esta registrado");
                return;
            }

            Boolean updatedSuccessfully = productService.updateProduct(
                    supplierName.getText(),
                    productName.getText(),
                    Double.parseDouble(price.getText()),
                    productComboBox.getValue().getProductID(),
                    productComboBox.getValue().getProductPriceID());

            if (updatedSuccessfully) {
                showInfoDialog("La operacion se realizo con exito!");
            } else {
                showErrorDialog("Un error ocurrio durante la transaccion");
            }

        });

        actiontarget = new Text();
        GridPane.setConstraints(actiontarget, 1, 5);

        // Add everything to grid
        addProductPane.getChildren().addAll(productNameLabel, priceLabel, supplierNameLabel);
        addProductPane.getChildren().addAll(productName, price, supplierName);
        addProductPane.getChildren().addAll(hbBtn, actiontarget);
        addProductPane.getChildren().add(productComboBox);
        addProductPane.setAlignment(Pos.CENTER);

        container.getChildren().add(addProductPane);
        parent.setCenter(container);
    }

    @FXML
    private void loadAddUsersView() throws IOException {
        Parent container = App.loadFXML("addUser");
        parent.setCenter(container);
    }

    @FXML
    private void loadUpdUsersView() throws IOException {
        // GridPane with 10px padding around edge
        VBox container = new VBox();

        HBox hbTitle = new HBox();
        hbTitle.getChildren().add(new Text("Ingrese los datos"));
        hbTitle.setAlignment(Pos.CENTER);
        container.getChildren().add(hbTitle);

        GridPane addUserPane = new GridPane();
        // addUserPane.setGridLinesVisible(true);
        addUserPane.setPadding(new Insets(10, 10, 10, 10));
        addUserPane.setVgap(10);
        addUserPane.setHgap(10);

        ComboBox<User> userComboBox = new ComboBox<>();
        userComboBox.setPromptText("Elija un usuario");

        UserService userService = new UserService();
        userComboBox.getItems().addAll(userService.getUsers());

        userComboBox.setOnHidden(e -> {
            User chosenUser = userComboBox.getValue();
            firstName.setText(chosenUser.getFirstName());
            lastName.setText(chosenUser.getLastName());
            userName.setText(chosenUser.getUserName());
            passInput.setText(chosenUser.getPassword());
            confirmPassInput.setText(chosenUser.getPassword());
        });
        GridPane.setConstraints(userComboBox, 3, 1);

        // firstName Label - constrains use (child, column, row)
        Label firstNameLabel = new Label("Nombre:");
        GridPane.setConstraints(firstNameLabel, 0, 1);
        // firstName Input
        firstName = new TextField();
        GridPane.setConstraints(firstName, 1, 1);

        // lastName Label - constrains use (child, column, row)
        Label lastNameLabel = new Label("Apellido:");
        GridPane.setConstraints(lastNameLabel, 0, 2);
        // lastName Input
        lastName = new TextField();
        GridPane.setConstraints(lastName, 1, 2);

        // userName Label - constrains use (child, column, row)
        Label userNameLabel = new Label("Nombre de Usuario:");
        GridPane.setConstraints(userNameLabel, 0, 3);
        // userName Input
        userName = new TextField();
        GridPane.setConstraints(userName, 1, 3);

        // Password Label
        Label passLabel = new Label("Nueva Contraseña:");
        GridPane.setConstraints(passLabel, 0, 4);
        // Password Input
        passInput = new PasswordField();
        passInput.setPromptText("contraseña");
        GridPane.setConstraints(passInput, 1, 4);

        // confirm Password Label
        Label confirmPassLabel = new Label("Confirmar Contraseña:");
        GridPane.setConstraints(confirmPassLabel, 0, 5);
        // confirm Password Input
        confirmPassInput = new PasswordField();
        confirmPassInput.setPromptText("repita contraseña");
        GridPane.setConstraints(confirmPassInput, 1, 5);

        Button deleteButton = new Button("Borrar");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(deleteButton);

        deleteButton.setOnAction(e -> {
            List<Object> inputs = Arrays.asList(
                    firstName.getText(),
                    lastName.getText(),
                    userName.getText(),
                    passInput.getText(),
                    confirmPassInput.getText());
            Boolean emptyInputs = inputs.stream().map(s -> s.toString()).anyMatch(s -> s.isEmpty());
            if (emptyInputs) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Por favor ingrese todos los campos");
                return;
            }
            Boolean userExists = userService.userExists(firstName.getText(), lastName.getText(), userName.getText());
            if (!userExists) {
                showErrorDialog("El usuario no existe");
                return;
            }

            Boolean removedSuccesfully = userService.removeUser(userComboBox.getValue().getUserID());
            if (removedSuccesfully) {
                showInfoDialog("La operacion se realizo con exito!");
            } else {
                showErrorDialog("Un error ocurrio durante la transaccion");
            }

        });
        // save
        Button saveButton = new Button("Guardar");
        hbBtn.getChildren().add(saveButton);
        GridPane.setConstraints(hbBtn, 1, 6);
        saveButton.setOnAction(e -> {
            List<Object> inputs = Arrays.asList(
                    firstName.getText(),
                    lastName.getText(),
                    userName.getText(),
                    passInput.getText(),
                    confirmPassInput.getText());
            Boolean emptyInputs = inputs.stream().map(s -> s.toString()).anyMatch(s -> s.isEmpty());
            if (emptyInputs) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Por favor ingrese todos los campos");
                return;
            }

            Boolean bothFieldsEqual = passInput.getText().equals(confirmPassInput.getText());
            if (!bothFieldsEqual) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Las contraseñas no coinciden");
                return;
            }

            Boolean userExists = userService.userExists(firstName.getText(), lastName.getText(), userName.getText());
            if (userExists) {
                showErrorDialog("El usuario ya existe");
                return;
            }

            Boolean updatedSuccessfully = userService.updateUser(firstName.getText(),
                    lastName.getText(),
                    userName.getText(),
                    passInput.getText(),
                    userComboBox.getValue().getUserID());
            if (updatedSuccessfully) {
                showInfoDialog("La operacion se realizo con exito!");
            } else {
                showErrorDialog("Un error ocurrio durante la transaccion");
            }

        });

        actiontarget = new Text();
        GridPane.setConstraints(actiontarget, 1, 8);

        // Add everything to grid
        addUserPane.getChildren().addAll(firstNameLabel, lastNameLabel, userNameLabel);
        addUserPane.getChildren().addAll(firstName, lastName, userName);
        addUserPane.getChildren().addAll(passLabel, confirmPassLabel);
        addUserPane.getChildren().addAll(passInput, confirmPassInput, hbBtn, actiontarget);
        addUserPane.getChildren().add(userComboBox);
        addUserPane.setAlignment(Pos.CENTER);

        container.getChildren().add(addUserPane);
        parent.setCenter(container);
    }

    @FXML
    private void loadAddSuppliersView() throws IOException {
        // GridPane with 10px padding around edge
        VBox container = new VBox();

        HBox hbTitle = new HBox();
        hbTitle.getChildren().add(new Text("Ingrese los datos"));
        hbTitle.setAlignment(Pos.CENTER);
        container.getChildren().add(hbTitle);

        GridPane addSupplierPane = new GridPane();
        // addUserPane.setGridLinesVisible(true);
        addSupplierPane.setPadding(new Insets(10, 10, 10, 10));
        addSupplierPane.setVgap(10);
        addSupplierPane.setHgap(10);

        // ContactName Label - constrains use (child, column, row)
        Label contactNameLabel = new Label("Nombre de Contacto:");
        GridPane.setConstraints(contactNameLabel, 0, 1);
        // firstName Input
        TextField contactName = new TextField();
        GridPane.setConstraints(contactName, 1, 1);

        // phone Label - constrains use (child, column, row)
        Label phoneLabel = new Label("Telefono:");
        GridPane.setConstraints(phoneLabel, 0, 2);
        // lastName Input
        TextField phone = new TextField();
        GridPane.setConstraints(phone, 1, 2);

        // save
        Button saveButton = new Button("Guardar");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(saveButton);
        GridPane.setConstraints(hbBtn, 1, 3);
        SupplierService supplierService = new SupplierService();
        saveButton.setOnAction(e -> {
            List<Object> inputs = Arrays.asList(
                    contactName.getText(),
                    phone.getText());
            Boolean emptyInputs = inputs.stream().map(s -> s.toString()).anyMatch(s -> s.isEmpty());
            if (emptyInputs) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Por favor ingrese todos los campos");
                return;
            }

            Boolean supplierExists = supplierService.suppliertExists(contactName.getText());
            if (supplierExists) {
                showErrorDialog("El Proveedor ya esta registrado");
                return;
            }
            Boolean addedSuccessfully = supplierService.addSupplier(contactName.getText(),
                    phone.getText());
            if (addedSuccessfully) {
                showInfoDialog("La operacion se realizo con exito!");
            } else {
                showErrorDialog("Un error ocurrio durante la transaccion.");
            }

        });

        actiontarget = new Text();
        GridPane.setConstraints(actiontarget, 1, 4);

        // Add everything to grid
        addSupplierPane.getChildren().addAll(contactNameLabel, phoneLabel);
        addSupplierPane.getChildren().addAll(contactName, phone);
        addSupplierPane.getChildren().addAll(hbBtn, actiontarget);
        addSupplierPane.setAlignment(Pos.CENTER);

        container.getChildren().add(addSupplierPane);
        parent.setCenter(container);

    }

    @FXML
    private void loadUpdSuppliersView() throws IOException {
        // GridPane with 10px padding around edge
        VBox container = new VBox();

        HBox hbTitle = new HBox();
        hbTitle.getChildren().add(new Text("Ingrese los datos"));
        hbTitle.setAlignment(Pos.CENTER);
        container.getChildren().add(hbTitle);

        GridPane addSupplierPane = new GridPane();
        // addUserPane.setGridLinesVisible(true);
        addSupplierPane.setPadding(new Insets(10, 10, 10, 10));
        addSupplierPane.setVgap(10);
        addSupplierPane.setHgap(10);

        ComboBox<Supplier> supplierComboBox = new ComboBox<>();
        supplierComboBox.setPromptText("Elija un proveedor");

        SupplierService supplierService = new SupplierService();
        supplierComboBox.getItems().addAll(supplierService.getSuppliers());

        supplierComboBox.setOnHidden(e -> {
            Supplier chosenSupplier = supplierComboBox.getValue();
            contactName.setText(chosenSupplier.getContactName());
            phone.setText(chosenSupplier.getPhone());
        });
        GridPane.setConstraints(supplierComboBox, 3, 1);

        // ContactName Label - constrains use (child, column, row)
        Label contactNameLabel = new Label("Nombre de Contacto:");
        GridPane.setConstraints(contactNameLabel, 0, 1);
        // firstName Input
        contactName = new TextField();
        GridPane.setConstraints(contactName, 1, 1);

        // phone Label - constrains use (child, column, row)
        Label phoneLabel = new Label("Telefono:");
        GridPane.setConstraints(phoneLabel, 0, 2);
        // lastName Input
        phone = new TextField();
        GridPane.setConstraints(phone, 1, 2);

        Button deleteButton = new Button("Borrar");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(deleteButton);
        deleteButton.setOnAction(e -> {
            List<Object> inputs = Arrays.asList(
                    contactName.getText(),
                    phone.getText());
            Boolean emptyInputs = inputs.stream().map(s -> s.toString()).anyMatch(s -> s.isEmpty());
            if (emptyInputs) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Por favor ingrese todos los campos");
                return;
            }
            Boolean supplierExists = supplierService.suppliertExists(contactName.getText(), phone.getText());
            if (!supplierExists) {
                showErrorDialog("El Proveedor no existe");
                return;
            }
            Boolean removedSuccessfully = supplierService.removeSupplier(supplierComboBox.getValue().getSupplierID());
            if (removedSuccessfully) {
                showInfoDialog("La operacion se realizo con exito!");
                supplierComboBox.getItems().clear();
                supplierComboBox.getItems().addAll(supplierService.getSuppliers());
                supplierComboBox.getSelectionModel().selectFirst();
            } else {
                showErrorDialog("Un error ocurrio durante la transaccion.");
            }
        });

        // save
        Button saveButton = new Button("Guardar");
        hbBtn.getChildren().add(saveButton);
        GridPane.setConstraints(hbBtn, 1, 3);
        saveButton.setOnAction(e -> {
            List<Object> inputs = Arrays.asList(
                    contactName.getText(),
                    phone.getText());
            Boolean emptyInputs = inputs.stream().map(s -> s.toString()).anyMatch(s -> s.isEmpty());
            if (emptyInputs) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Por favor ingrese todos los campos");
                return;
            }
            Boolean supplierExists = supplierService.suppliertExists(contactName.getText(), phone.getText());
            if (supplierExists) {
                showErrorDialog("El Proveedor ya existe");
                return;
            }
            Boolean updatedSuccessfully = supplierService.updateSupplier(contactName.getText(),
                    phone.getText(),
                    supplierComboBox.getValue().getSupplierID());
            if (updatedSuccessfully) {
                showInfoDialog("La operacion se realizo con exito!");
                supplierComboBox.getItems().clear();
                supplierComboBox.getItems().addAll(supplierService.getSuppliers());
                supplierComboBox.getSelectionModel().selectFirst();
            } else {
                showErrorDialog("Un error ocurrio durante la transaccion.");
            }

        });

        actiontarget = new Text();
        GridPane.setConstraints(actiontarget, 1, 4);

        // Add everything to grid
        addSupplierPane.getChildren().addAll(contactNameLabel, phoneLabel);
        addSupplierPane.getChildren().addAll(contactName, phone);
        addSupplierPane.getChildren().addAll(hbBtn, actiontarget);
        addSupplierPane.getChildren().add(supplierComboBox);
        addSupplierPane.setAlignment(Pos.CENTER);

        container.getChildren().add(addSupplierPane);
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
