module com.example.finalporject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires metadata.extractor;


    opens com.example.finalporject to javafx.fxml;
    exports com.example.finalporject;
    exports com.example.finalporject.C;
    opens com.example.finalporject.C to javafx.fxml;
}