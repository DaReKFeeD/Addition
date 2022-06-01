module com.addition {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.addition to javafx.fxml;
    exports com.addition;
}