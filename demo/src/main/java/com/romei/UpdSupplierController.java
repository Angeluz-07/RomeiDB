package com.romei;

import static com.romei.Utils.showErrorDialog;
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

public class UpdSupplierController {
    @FXML
    TextField contactName;
    @FXML
    TextField phone;
    @FXML
    ComboBox<Supplier> supplierComboBox;

    @FXML
    Button deleteButton;
    @FXML
    Button saveButton;
    @FXML
    Text actiontarget;

    SupplierService supplierService;

    public void initialize() {
        supplierService = new SupplierService();

        supplierComboBox.getItems().addAll(supplierService.getSuppliers());
        supplierComboBox.setOnHidden(e -> {
            Supplier chosenSupplier = supplierComboBox.getValue();
            contactName.setText(chosenSupplier.getContactName());
            phone.setText(chosenSupplier.getPhone());
        });

    }

    @FXML
    private void delete() throws IOException {
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
    };

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
    }

}
