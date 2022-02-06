module com.filechooser.filechooser {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.filechooser.filechooser to javafx.fxml;
    exports com.filechooser.filechooser;
    exports com.filechooser.filechooser.controlers;
    opens com.filechooser.filechooser.controlers to javafx.fxml;
}