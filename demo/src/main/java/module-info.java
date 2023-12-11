module com.romei {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.romei to javafx.fxml;
    exports com.romei;
}
