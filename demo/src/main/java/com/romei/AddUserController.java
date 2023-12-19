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

public class AddUserController {
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
    Button saveButton;
    @FXML
    Text actiontarget;

    UserService userService;

    public void initialize() {
        userService = new UserService();
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
                showErrorDialog("El usuario ya esta registrado");
                return;
            }

            Boolean addedSuccessfully = userService.addUser(firstName.getText(), lastName.getText(), userName.getText(),
                    passInput.getText());
            if (addedSuccessfully) {
                showInfoDialog("La operacion se realizo con exito!");
                return;
            } else {
                showErrorDialog("Un error ocurrio durante la transaccion");
                return;
            }

    }

}
