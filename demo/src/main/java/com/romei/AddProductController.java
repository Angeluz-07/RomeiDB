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

public class AddProductController {
    @FXML
    TextField productName;
    @FXML
    TextField price;
    @FXML
    TextField supplierName;
    @FXML
    Button saveButton;
    @FXML
    Text actiontarget;

    ProductService productService;
    SupplierService supplierService;

    public void initialize() {
        productService = new ProductService();
        supplierService = new SupplierService();

    }

    @FXML
    private void save() throws IOException {
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

        Boolean supplierExists = supplierService.suppliertExists(supplierName.getText());
        if (!supplierExists) {
            showErrorDialog("El Proveedor no esta registrado");
            return;
        }

        Boolean productExists = productService.productExists(productName.getText(), price.getText());
        if (productExists) {
            showErrorDialog("El producto ya existe.");
            return;
        }

        Boolean addedSuccessfully = productService.addProduct(
                supplierName.getText(),
                productName.getText(),
                price.getText());
        if (addedSuccessfully) {
            showInfoDialog("La operacion se realizo con exito!");
        } else {
            showErrorDialog("Un error ocurrio durante la transaccion");
        }

    }

}
