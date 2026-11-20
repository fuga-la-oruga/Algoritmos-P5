module dev.reveles.main
{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;


    opens dev.reveles.main to javafx.fxml;
    exports dev.reveles.main;
}