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

public class AddSupplierController {
    @FXML
    TextField contactName;
    @FXML
    TextField phone;

    @FXML
    Button saveButton;
    @FXML
    Text actiontarget;

    SupplierService supplierService;

    public void initialize() {
        supplierService = new SupplierService();

    }

    @FXML
    private void save() throws IOException {
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

    }

}
