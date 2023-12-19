package com.romei;

import static com.romei.Utils.showErrorDialog;
import static com.romei.Utils.setDateFormat;
import static com.romei.Utils.showInfoDialog;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class UpdUserController {
    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    TextField userName;
    @FXML
    TextField passInput;
    @FXML
    TextField confirmPassInput;

    @FXML
    ComboBox<User> userComboBox;

    @FXML
    Button saveButton;
    @FXML
    Button deleteButton;

    @FXML
    Text actiontarget;

    UserService userService;

    public void initialize() {
        userService = new UserService();
        userComboBox.getItems().addAll(userService.getUsers());

        userComboBox.setOnHidden(e -> {
            User chosenUser = userComboBox.getValue();
            firstName.setText(chosenUser.getFirstName());
            lastName.setText(chosenUser.getLastName());
            userName.setText(chosenUser.getUserName());
            passInput.setText(chosenUser.getPassword());
            confirmPassInput.setText(chosenUser.getPassword());
        });
    }

    @FXML
    private void save() throws IOException {
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

    }

    @FXML
    private void delete() throws IOException {
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

    }
}
