module org.openjfx.dduaarsprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens org.openjfx.dduaarsprojekt to javafx.fxml;
    exports org.openjfx.dduaarsprojekt;
}
