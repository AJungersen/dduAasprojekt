module org.openjfx.dduaarsprojekt {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.openjfx.dduaarsprojekt to javafx.fxml;
    exports org.openjfx.dduaarsprojekt;
}
