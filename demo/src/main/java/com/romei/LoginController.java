package com.romei;

import static com.romei.MySqlUtil.dataIsInDB;
import static com.romei.Utils.showErrorDialog;
import static com.romei.Utils.thereAreEmptyFields;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.Scene;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class LoginController {
    @FXML private TextField userNameInput;
    @FXML private PasswordField passwordInput;
    @FXML private Text warnBadInputs;

    @FXML
    private void loginProcedure() throws IOException {
        System.out.println(("Hello you clicked the login button"));
        System.out.println(userNameInput.getText());
        System.out.println(passwordInput.getText());
        
        List<Object> inputs = Arrays.asList(
            userNameInput.getText(),
            passwordInput.getText()
        );
        Boolean emptyInputs = inputs.stream().map(s -> s.toString()).anyMatch(s -> s.isEmpty());
        if (emptyInputs) {
            warnBadInputs.setText("Por favor ingrese todos los campos");
            return;
        }

        String QUERY="select UserID,UserName,Password from Users where UserName=? and Password=?";
        List<Object> dataInDB =  dataIsInDB(inputs, QUERY, "LoginController");
        Boolean noDataInDB = dataInDB.isEmpty();
        if (noDataInDB) {
            //the user & pass combination doesnt exist
            warnBadInputs.setText("El usuario o la clave son incorrectas");
        }else {
            System.out.println(dataInDB.toString());
            //TabRegister.userID=(Integer)dataInDB.get(0);
            //PaneOrg a=new PaneOrg();
            //Scene temp =new Scene(a.getRoot(),820,600);
            //temp.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
            //App.window.setScene(temp);
        }
    }    
}
