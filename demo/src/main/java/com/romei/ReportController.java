package com.romei;

import static com.romei.Utils.showErrorDialog;
import static com.romei.Utils.setDateFormat;
import static com.romei.Utils.showInfoDialog;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;

public class ReportController {
    @FXML
    TableView<Register> reportTable;

    @FXML
    TableColumn<Register, String> dateCol;
    @FXML
    TableColumn<Register, Integer> quantitySoldCol;
    @FXML
    TableColumn<Register, Double> cashSaleCol;

    @FXML
    ComboBox<Product> productComboBox;
    @FXML
    DatePicker dateIni;
    @FXML
    DatePicker dateFin;

    @FXML
    Button genReportButton;

    RegisterService registerService;
    ProductService productService;

    public void initialize() {
        registerService = new RegisterService();
        productService = new ProductService();

        reportTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        reportTable.setMinWidth(620);

        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        quantitySoldCol.setCellValueFactory(new PropertyValueFactory<>("quantitySold"));
        cashSaleCol.setCellValueFactory(new PropertyValueFactory<>("cashSale"));

        reportTable.getItems().addAll(registerService.getRegisters());

        productComboBox.getItems().addAll(productService.getProducts());

        dateIni.setValue(LocalDate.now());
        dateFin.setValue(LocalDate.now());

    }

    @FXML
    private void genReport() throws IOException {
        String dateIniFormatted = setDateFormat(dateIni.getEditor().getText());
        String dateFinFormatted = setDateFormat(dateFin.getEditor().getText());

        ObservableList<Register> registersToTable = FXCollections.observableArrayList();
        Product p = (Product) productComboBox.getValue();

        List<Register> registersFromDB = registerService.getRegistersForReport(dateIniFormatted, dateFinFormatted,
                p.getProductPriceID());
        if (!registersFromDB.isEmpty()) {
            registersToTable.addAll(registersFromDB);
            reportTable.setItems(registersToTable);
            reportTable.refresh();
        }

    }

}
