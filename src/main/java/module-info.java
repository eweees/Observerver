module com.example.timeserver {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.example.timeserver.ui to javafx.fxml;
    opens com.example.timeserver to javafx.fxml;
    opens com.example.timeserver.components to javafx.fxml;

    exports com.example.timeserver;
    exports com.example.timeserver.ui;
    exports com.example.timeserver.interfaces;
    exports com.example.timeserver.timeserver;
    exports com.example.timeserver.components;
}