module com.daniilzyravlev.fishbone {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires javafx.swing;

    opens com.daniilzyravlev.fishbone to javafx.fxml;
    exports com.daniilzyravlev.fishbone;
}