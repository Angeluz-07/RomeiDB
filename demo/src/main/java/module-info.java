module com.romei {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.romei to javafx.fxml;
    exports com.romei;
}
