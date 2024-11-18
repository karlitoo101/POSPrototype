module com.mycompany.systemprototype.pos {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.systemprototype.pos to javafx.fxml;
    exports com.mycompany.systemprototype.pos;
    requires com.jfoenix;
    requires fontawesomefx;
    
}
