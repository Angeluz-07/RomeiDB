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

public class UpdProductController {
    @FXML
    TextField productName;
    @FXML
    TextField price;
    @FXML
    TextField supplierName;

    @FXML
    Button deleteButton;
    @FXML
    Button saveButton;
    @FXML
    ComboBox<Product> productComboBox;

    @FXML
    Text actiontarget;

    ProductService productService;
    SupplierService supplierService;

    public void initialize() {
        productService = new ProductService();
        supplierService = new SupplierService();

        productComboBox.getItems().addAll(productService.getProducts());

        productComboBox.setOnHidden(e -> {
            Product chosenProduct = (Product) productComboBox.getValue();
            productName.setText(chosenProduct.getName());
            price.setText(Double.toString(chosenProduct.getPrice()));
            supplierName.setText(chosenProduct.getSupplier().getContactName());
        });
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

    }

    @FXML
    private void delete() throws IOException {
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

    }
}
