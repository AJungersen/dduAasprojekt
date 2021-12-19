module org.openjfx.dduaarsprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens org.openjfx.dduaarsprojekt to javafx.fxml;
    opens org.openjfx.dduaarsprojekt.random to javafx.base;
    opens AssistantClasses to javafx.base;
    exports org.openjfx.dduaarsprojekt;
}
