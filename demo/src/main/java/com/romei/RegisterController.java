package com.romei;

import static com.romei.Utils.showErrorDialog;
import static com.romei.Utils.setDateFormat;
import static com.romei.Utils.showInfoDialog;

import java.io.IOException;
import java.time.LocalDate;

import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

public class RegisterController {
    @FXML
    TableView<Register> registerTable;

    @FXML
    TableColumn<Register, Integer> addedOrRemovedStockCol;
    @FXML
    TableColumn<Register, Product> productCol;
    @FXML
    TableColumn<Register, Integer> initialStockCol;
    @FXML
    TableColumn<Register, Integer> finalStockCol;
    @FXML
    TableColumn<Register, Integer> quantitySoldCol;
    @FXML
    TableColumn<Register, Double> cashSaleCol;

    @FXML
    DatePicker registerDate;
    @FXML
    Button refreshTableButton;
    @FXML
    Button saveTableButton;

    RegisterService registerService;

    public void initialize() {
        System.out.println("hello");
        this.registerService = new RegisterService();

        registerTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        registerTable.setMinWidth(620);
        registerTable.setEditable(true);

        // make editable
        addedOrRemovedStockCol.setEditable(true);
        finalStockCol.setEditable(true);

        // make editable with text field
        addedOrRemovedStockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        addedOrRemovedStockCol.setId("addedOrRemovedStockColID");
        // updateCell is an event of CellEditEvent type
        // addedOrRemovedStockCol.setOnEditCommit(updateCell->updateRegisterTable(updateCell));
        // todo: fix

        finalStockCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        finalStockCol.setId("finalStockColID");
        // updateCell is an event of CellEditEvent type
        // finalStockCol.setOnEditCommit(updateCell -> updateRegisterTable(updateCell));
        // todo: fix

        // linkage between Register object and Table columns
        // below the parameter is the name of the attribute in the class Register
        addedOrRemovedStockCol.setCellValueFactory(new PropertyValueFactory<>("addedOrRemovedStock"));
        productCol.setCellValueFactory(new PropertyValueFactory<>("product"));
        initialStockCol.setCellValueFactory(new PropertyValueFactory<>("initialStock"));
        finalStockCol.setCellValueFactory(new PropertyValueFactory<>("finalStock"));
        quantitySoldCol.setCellValueFactory(new PropertyValueFactory<>("quantitySold"));
        cashSaleCol.setCellValueFactory(new PropertyValueFactory<>("cashSale"));

        registerTable.getItems().addAll(registerService.getRegisters());

        registerDate.setValue(LocalDate.now());

    }

    @FXML
    private void refreshTable() throws IOException {
        registerTable.getItems().clear();
        registerTable.getItems().addAll(this.registerService.getRegisters());
    }

    @FXML
    private void saveTable() throws IOException {
        int userID = 1;// todo: fix
        boolean success = true;

        for (Register r : registerTable.getItems()) {
            success = registerService.addRegister(
                    userID,
                    setDateFormat(registerDate.getEditor().getText()),
                    r.getProduct().getProductPriceID(),
                    r.getInitialStock(),
                    r.getFinalStock(),
                    r.getQuantitySold(),
                    r.getCashSale());
            if (!success) {
                showErrorDialog("Un error ocurrio durante la transaccion de un item");
                break;
            }

            success = registerService.addAORStockItem(r.getAddedOrRemovedStock());
            if (!success) {
                showErrorDialog("Un error ocurrio durante la transaccion de un item");
                break;
            }

        }

        if (success)
            showInfoDialog("La operacion se realizo con exito!");
        else
            showErrorDialog("Un error ocurrio durante la transaccion");

    }
}
